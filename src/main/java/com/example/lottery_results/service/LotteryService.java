package com.example.lottery_results.service;

import com.example.lottery_results.entity.LotteryResult;
import com.example.lottery_results.repository.LotteryResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class LotteryService {

    private static final Logger log = LoggerFactory.getLogger(LotteryService.class);

    private final LotteryResultRepository repository;

    // Constructor injection (replace Lombok's @RequiredArgsConstructor)
    public LotteryService(LotteryResultRepository repository) {
        this.repository = repository;
    }

    /**
     * Save a list of lottery results
     */
    public void saveLotteryResults(List<LotteryResult> results) {
        for (LotteryResult result : results) {
            // avoid duplicates
            if (!repository.existsByDrawDateAndLotteryName(result.getDrawDate(), result.getLotteryName())) {
                repository.save(result);
            } else {
                log.warn("Skipping duplicate result → Date: {}, Lottery: {}",
                        result.getDrawDate(), result.getLotteryName());
            }
        }
    }

    /**
     * Get total count
     */
    public long getResultCount() {
        return repository.count();
    }

    /**
     * Get all results
     */
    public List<LotteryResult> getAllResults() {
        return repository.findAll();
    }

    /**
     * Check if result exists already
     */
    public boolean resultExists(LocalDate date, String lotteryName) {
        return repository.existsByDrawDateAndLotteryName(date, lotteryName);
    }
    public List<LotteryResult> getLastMonthResults() {
        YearMonth lastMonth = YearMonth.now().minusMonths(1);
        LocalDate startDate = lastMonth.atDay(1);
        LocalDate endDate = lastMonth.atEndOfMonth();
        return repository.findByDrawDateBetween(startDate, endDate);
    }
}
