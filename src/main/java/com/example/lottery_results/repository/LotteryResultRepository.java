package com.example.lottery_results.repository;

import com.example.lottery_results.entity.LotteryResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LotteryResultRepository extends JpaRepository<LotteryResult, Long> {

    boolean existsByDrawDateAndLotteryName(LocalDate drawDate, String lotteryName);

    // Fetch results between two dates
    List<LotteryResult> findByDrawDateBetween(LocalDate startDate, LocalDate endDate);
}
