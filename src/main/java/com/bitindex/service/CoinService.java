package com.bitindex.service;

import com.bitindex.domain.Coin;
import com.bitindex.domain.RateCoin;
import com.bitindex.repository.CoinRepository;
import com.bitindex.repository.RateCoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoinService {

    @Autowired
    private CoinRepository coinRepository;
    @Autowired
    private RateCoinRepository rateCoinRepository;

    public List<Coin> findAllByOrderByRankAsc(){
        return coinRepository.findAllByOrderByRankAsc();
    }

    public Coin findCoinById(int id){
        return coinRepository.findOne(id);
    }

    public Coin findCoinByNameApi(String nameApi){
        return coinRepository.findByNameApi(nameApi);
    }

    public Coin saveCoin(Coin coin){
        return coinRepository.save(coin);
    }

    public void removeCoin(int id){
        coinRepository.delete(id);
    }

    public RateCoin saveRateCoin(RateCoin rateCoin){
        return rateCoinRepository.save(rateCoin);
    }
}
