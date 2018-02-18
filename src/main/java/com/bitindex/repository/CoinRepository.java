package com.bitindex.repository;

import com.bitindex.domain.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Integer> {

    List<Coin> findAllByOrderByRankAsc();
}
