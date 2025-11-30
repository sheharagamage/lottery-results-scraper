package com.example.lottery_results.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "lottery_results_a1")
public class LotteryResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "draw_date", nullable = false)
    private LocalDate drawDate;


    @Column(name = "lottery_name", nullable = false, length = 100)
    private String lotteryName;

    @Column(name = "winning_numbers", nullable = false, length = 500)
    private String winningNumbers;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public LotteryResult() {}

    public LotteryResult(LocalDate drawDate, String lotteryName, String winningNumbers) {
        this.drawDate = drawDate;
        this.lotteryName = lotteryName;
        this.winningNumbers = winningNumbers;
    }

    public Long getId() { return id; }
    public LocalDate getDrawDate() { return drawDate; }
    public String getLotteryName() { return lotteryName; }
    public String getWinningNumbers() { return winningNumbers; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setDrawDate(LocalDate drawDate) { this.drawDate = drawDate; }
    public void setLotteryName(String lotteryName) { this.lotteryName = lotteryName; }
    public void setWinningNumbers(String winningNumbers) { this.winningNumbers = winningNumbers; }
}
