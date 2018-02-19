package com.bitindex.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name = "coin_rate")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class RateCoin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @ManyToOne
    private Coin coin;
    private Date updateTime;
    private double price_usd;
    private double price_rub;
}
