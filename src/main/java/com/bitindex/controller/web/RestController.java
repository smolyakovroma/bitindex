package com.bitindex.controller.web;

import com.bitindex.domain.Coin;
import com.bitindex.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@org.springframework.web.bind.annotation.RestController("/")
//@CrossOrigin
public class RestController {

    @Autowired
    private CoinService coinService;

    @RequestMapping(value = "/edit_coin", method = RequestMethod.POST)
    public void editCoinPost(@RequestParam(defaultValue = "0") int id, @RequestParam String name, @RequestParam String title, @RequestParam String description, @RequestParam int rank, @RequestParam int active) {
        Coin coin = coinService.findCoinById(id);
        if (coin == null) {
            coin = new Coin();
        }
        coin.setName(name);
        coin.setDescription(description);
        coin.setRank(rank);
        coin.setActive(active);
        coin.setTitle(title);
        coinService.saveCoin(coin);

    }

    @RequestMapping(value = "/getCoin/{id}", method = RequestMethod.GET)
    public Coin getCoinById(@PathVariable int id) {
        Coin coin = coinService.findCoinById(id);
        return coin;
    }

    @RequestMapping(value = "/getAllCoins", method = RequestMethod.GET)
    public List<Coin> getAllCoins() {
        List<Coin> coins = coinService.findAllByOrderByRankAsc();
        return coins;
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public void remove(@PathVariable int id) {
        coinService.removeCoin(id);
    }

    @RequestMapping(value = "/image/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("id") int id) throws IOException {
        byte[] imageContent = null;
        imageContent = coinService.findCoinById(id).getPic();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/upload_img", method = RequestMethod.POST)
    public void handleFileUploadItem(@RequestParam("file") MultipartFile file, @RequestParam("id") int id) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                Coin coin = coinService.findCoinById(id);
                coin.setPic(bytes);
                coinService.saveCoin(coin);
            } catch (Exception e) {

            }
        }

    }
}
