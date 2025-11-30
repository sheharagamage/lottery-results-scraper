package com.example.lottery_results.service;

import com.example.lottery_results.entity.LotteryResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LotteryScraperService {

    private static final Logger log = LoggerFactory.getLogger(LotteryScraperService.class);
    private final LotteryService lotteryService;

    // Updated to history page
    private static final String GOVISETHA_HISTORY_URL = "https://www.lankayp.com/lottery/result/govisetha";

    public LotteryScraperService(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    /**
     * Scrape all historical Govisetha results
     */
    public List<LotteryResult> scrapeAllGovisethaResults() {
        return scrapeAndSave(GOVISETHA_HISTORY_URL);
    }

    public List<LotteryResult> scrapeAndSave(String url) {
        log.info("================================================");
        log.info("NLB Govisetha Lottery Results Scraper");
        log.info("================================================");
        log.info("Connecting to: {}", url);

        List<LotteryResult> results = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .timeout(15000)
                    .get();

            log.info("✓ Connected successfully!");
            log.info("Page Title: {}", doc.title());
            log.info("================================================");

            Elements tables = doc.select("table");
            if (tables.isEmpty()) {
                log.error("ERROR: No tables found on page.");
                return results;
            }

            Element table = tables.first();
            Elements rows = table.select("tbody tr");
            log.info("Total Results Found: {}", rows.size());
            log.info("================================================");

            int count = 0;

            for (Element row : rows) {
                Elements columns = row.select("td");
                if (columns.size() >= 3) {
                    try {
                        String dateStr = columns.get(0).text().trim();
                        String lotteryName = columns.get(1).text().trim();
                        String numbers = columns.get(2).text().trim();

                        LocalDate drawDate = parseDate(dateStr);

                        LotteryResult result = new LotteryResult(drawDate, lotteryName, numbers);
                        results.add(result);

                        log.info("Draw Date  : {}", drawDate);
                        log.info("Lottery    : {}", lotteryName);
                        log.info("Results    : {}", numbers);
                        log.info("------------------------------------------------");

                        count++;
                    } catch (Exception e) {
                        log.error("Error parsing row: {}", e.getMessage(), e);
                    }
                }
            }

            if (count > 0) {
                log.info("✓ Successfully scraped {} results!", count);
                log.info("Saving results to database...");
                lotteryService.saveLotteryResults(results);
                log.info("✓ Database save completed!");
            } else {
                log.warn("No valid results found.");
            }

            // ✅ Check for pagination
            Elements nextPageLinks = doc.select("ul.pagination a:contains(Next)"); // adjust selector if needed
            if (!nextPageLinks.isEmpty()) {
                String nextPageUrl = nextPageLinks.first().absUrl("href");
                if (!nextPageUrl.isEmpty()) {
                    log.info("Fetching next page: {}", nextPageUrl);
                    results.addAll(scrapeAndSave(nextPageUrl)); // recursive call
                }
            }

        } catch (IOException e) {
            log.error("Connection Failed: {}", e.getMessage(), e);
        }

        return results;
    }

    // ✅ Proper date parser
    private LocalDate parseDate(String dateStr) {
        dateStr = dateStr.trim().split(",")[0]; // remove day-of-week like "Thursday"
        DateTimeFormatter[] formatters = {
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                DateTimeFormatter.ofPattern("yyyy/MM/dd"),
                DateTimeFormatter.ofPattern("MM/dd/yyyy"),
                DateTimeFormatter.ofPattern("dd.MM.yyyy"),
                DateTimeFormatter.ofPattern("yyyy.MM.dd"),
                DateTimeFormatter.ofPattern("dd MMM yyyy") // e.g., 27 Nov 2025
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException e) {
                // skip
            }
        }

        log.warn("Could not parse date: '{}', using current date.", dateStr);
        return LocalDate.now();
    }

}
