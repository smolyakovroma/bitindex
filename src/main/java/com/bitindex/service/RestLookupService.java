package com.bitindex.service;

import com.bitindex.domain.Coin;
import com.bitindex.domain.dto.CoinDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class RestLookupService {

    RestTemplate restTemplate = new RestTemplate();

    @Async
    public Future<List<CoinDTO>> getRate(List<Coin> coins) throws InterruptedException {
        List<CoinDTO> list = new ArrayList<>();
        for (Coin coin : coins) {
            if (coin.getActive() == 1) {
                CoinDTO[] coinDTO = restTemplate.getForObject("https://api.coinmarketcap.com/v1/ticker/{coin}/?convert=RUB", CoinDTO[].class, coin.getNameApi());
                coinDTO[0].setCoin(coin);
                list.add(coinDTO[0]);
            }
        }

        Thread.sleep(1000L);
        return new AsyncResult<List<CoinDTO>>(list);
    }
}
