package com.familymoney.telegrambot.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table("payments")
@Data
public class PaymentEntity {
    @Id
    private Long id;
    private Long userId;
    private Long accountId;
    private Long paymentCategoryId;
    private BigDecimal amount;
    private LocalDate date;
}
