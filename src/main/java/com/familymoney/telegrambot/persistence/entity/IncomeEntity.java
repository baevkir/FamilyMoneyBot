package com.familymoney.telegrambot.persistence.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Table("incomes")
public class IncomeEntity {
    private Long id;
    private Long userId;
    private Long accountId;
    private BigDecimal amount;
    private LocalDate date;
}
