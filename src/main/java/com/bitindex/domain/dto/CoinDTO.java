package com.bitindex.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class CoinDTO {

    @JsonProperty("id")
    private String id;
    private String name;
    private String symbol;
    private int rank;
    private double price_usd;
    private double price_btc;
    @JsonProperty("24h_volume_usd")
    private BigDecimal volume_usd;
    private BigDecimal market_cap_usd;
    private BigDecimal available_supply;
    private BigDecimal total_supply;
    private BigDecimal max_supply;
    private double percent_change_1h;
    private double percent_change_24h;
    private double percent_change_7d;
    private String last_updated;
}
