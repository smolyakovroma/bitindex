package com.bitindex;

import com.bitindex.domain.Coin;
import com.bitindex.domain.RateCoin;
import com.bitindex.domain.dto.CoinDTO;
import com.bitindex.service.CoinService;
import com.bitindex.service.RestLookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableAsync
@EnableAutoConfiguration
@ComponentScan
public class BitindexApplication implements CommandLineRunner{

	@Autowired
	private RestLookupService restLookupService;
	@Autowired
	private CoinService coinService;

	public static ArrayList<ArrayList<RateCoin>> rates = new ArrayList<>();

	public static void main(String[] args) {
		SpringApplication.run(BitindexApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {


		Date now;
		Future<List<CoinDTO>> result;
		List<Coin> coins;

		while (true) {

			coins = coinService.findAllByOrderByRankAsc();

			// Start the clock
			long start = System.currentTimeMillis();

			// Kick of multiple, asynchronous lookups
			result = restLookupService.getRate(coins);

			// Wait until they are all done
			while (!result.isDone()) {
				Thread.sleep(10); //millisecond pause between each check
			}

			// Print results, including elapsed time
			System.out.println("Elapsed time: " + (System.currentTimeMillis() - start));
			now = new Date();
			ArrayList<RateCoin> list = new ArrayList<>();

			for (CoinDTO rate : result.get()) {
					RateCoin rateCoin = new RateCoin();
					rateCoin.setCoin(rate.getCoin());
					rateCoin.setPrice_rub(rate.getPrice_rub());
					rateCoin.setPrice_usd(rate.getPrice_usd());
					rateCoin.setUpdateTime(now);
					list.add(rateCoin);
					coinService.saveRateCoin(rateCoin);
			}

			rates.add(list);

			TimeUnit.SECONDS.sleep(600);
		}

	}
}
