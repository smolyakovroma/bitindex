package com.bitindex.controller.web;

import com.bitindex.domain.Coin;
import com.bitindex.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController("/")
//@CrossOrigin
public class RestController {

    @Autowired
    private CoinService coinService;

    @RequestMapping(value = "/edit_coin", method = RequestMethod.POST)
    public void editCoinPost(@RequestParam(defaultValue = "0") int id, @RequestParam String name,@RequestParam String title, @RequestParam String description, @RequestParam int rank, @RequestParam int active) {
        Coin coin = coinService.findCoinById(id);
        if(coin==null) {
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
}
