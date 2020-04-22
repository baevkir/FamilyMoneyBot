package com.familymoney.telegrambot.business.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Income {
    private Long id;
    private Long chatId;
    private BotUser user;
    private Account account;
    private BigDecimal amount;
    private LocalDate date;
}
