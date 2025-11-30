package com.example.lottery_results;

import com.example.lottery_results.entity.LotteryResult;
import com.example.lottery_results.service.LotteryScraperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class LotteryResultsApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(LotteryResultsApplication.class);
	private final LotteryScraperService lotteryScraperService;

	public LotteryResultsApplication(LotteryScraperService lotteryScraperService) {
		this.lotteryScraperService = lotteryScraperService;
	}

	public static void main(String[] args) {
		SpringApplication.run(LotteryResultsApplication.class, args);
	}

	@Override
	public void run(String... args) {
		log.info("================================================");
		log.info("Starting Lottery Results Scraper - Last Month");
		log.info("================================================");

		// Scrape only last month's results
		List<LotteryResult> lastMonthResults = lotteryScraperService.scrapeAllGovisethaResults();

		if (lastMonthResults.isEmpty()) {
			log.warn("No results found for last month.");
		} else {
			log.info("Total results retrieved: {}", lastMonthResults.size());
			for (LotteryResult result : lastMonthResults) {
				log.info("ID: {} | Date: {} | Lottery: {} | Numbers: {}",
						result.getId(),
						result.getDrawDate(),
						result.getLotteryName(),
						result.getWinningNumbers());
			}
		}

		log.info("================================================");
		log.info("Scraping completed successfully!");
		log.info("================================================");
	}
}
