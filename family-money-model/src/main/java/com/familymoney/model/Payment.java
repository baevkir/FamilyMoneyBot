package com.familymoney.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Payment {
    private Long id;
    private BotUser user;
    private Account account;
    private PaymentCategory category;
    private BigDecimal amount;
    private LocalDate date;
}
