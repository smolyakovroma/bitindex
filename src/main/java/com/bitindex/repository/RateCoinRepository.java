package com.bitindex.repository;

import com.bitindex.domain.RateCoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateCoinRepository extends JpaRepository<RateCoin, Integer> {
}
