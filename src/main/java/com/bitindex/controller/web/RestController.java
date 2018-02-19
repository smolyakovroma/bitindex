package com.bitindex.controller.web;

import com.bitindex.BitindexApplication;
import com.bitindex.domain.Coin;
import com.bitindex.domain.RateCoin;
import com.bitindex.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController("/")
//@CrossOrigin
public class RestController {

    @Autowired
    private CoinService coinService;

    @RequestMapping(value = "/edit_coin", method = RequestMethod.POST)
    public void editCoinPost(@RequestParam(defaultValue = "0") int id, @RequestParam String name, @RequestParam String nameApi, @RequestParam String title, @RequestParam String description, @RequestParam int rank, @RequestParam int active) {
        Coin coin = coinService.findCoinById(id);
        if (coin == null) {
            coin = new Coin();
        }
        coin.setName(name);
        coin.setNameApi(nameApi);
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

    @RequestMapping(value = "/getRate/{currency}/{period}", method = RequestMethod.GET)
    public String getRate(@PathVariable String currency, @PathVariable String period) {
        int count = 7;
        if (period.equals("day")) {
            count = 24;
        } else if (period.equals("week")) {
            count = 7 * 24;
        } else if (period.equals("month")) {
            count = 7 * 24 * 30;
        }
        int i = BitindexApplication.rates.size();
        if (BitindexApplication.rates.size() < count) return "";
        ArrayList<RateCoin> rate = BitindexApplication.rates.get(i - 1);
        ArrayList<RateCoin> prevRate = BitindexApplication.rates.get(i - (count+1));
        double sum = 0d;
        for (RateCoin coin : rate) {
            if (currency.equals("rub")) {
                sum += coin.getPrice_rub();
            } else if (currency.equals("usd")) {
                sum += coin.getPrice_usd();
            }
        }

        double prevSum = 0d;
        for (RateCoin coin : prevRate) {
            if (currency.equals("rub")) {
                prevSum += coin.getPrice_rub();
            } else if (currency.equals("usd")) {
                prevSum += coin.getPrice_usd();
            }
        }

        double res = sum / rate.size();
        double prevRes = prevSum / prevRate.size();
        double proc = ((res-prevRes)/prevRes)*100;
        String str = "";

        if (currency.equals("rub")) {
             str = "<h2 style = \"color: red;\" > \u20BD " + String.format("%.2f", res) + " RUB (" + String.format("%.2f", proc) + " %) </h2 >";

        } else if (currency.equals("usd")) {
            str =  "<h2 style = \"color: red;\" > $ " + String.format("%.2f", res) + " USD (" + String.format("%.2f", proc) + " %) </h2 >";
        }

        if(res>=prevRes){
            str = str.replace("red", "green");
        }
        return str;
    }

    @RequestMapping(value = "/getGraph/{currency}/{period}", method = RequestMethod.GET)
    public double[] getGraph(@PathVariable String currency, @PathVariable String period) {

        int count = 7;
        if (period.equals("day")) {
            count = 24;
        } else if (period.equals("week")) {
            count = 7 * 24;
        } else if (period.equals("month")) {
            count = 7 * 24 * 30;
        }

        int i = BitindexApplication.rates.size();
        if (i < count) return new double[]{};
        double[] res = new double[count];
        double sum;
        for (int f = 0; f < count; f++) {
            ArrayList<RateCoin> rate = BitindexApplication.rates.get(--i);
            sum = 0d;
            for (RateCoin coin : rate) {
                if (currency.equals("rub")) {
                    sum += coin.getPrice_rub();
                } else if (currency.equals("usd")) {
                    sum += coin.getPrice_usd();
                }
            }
            res[f] = sum / rate.size();
        }

        return res;
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
