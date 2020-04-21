package com.familymoney.telegrambot.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("payment")
@Data
public class PaymentEntity {
    @Id
    private Long id;
    private Long chatId;
    private Long userId;
    private Long paymentTypeId;
    private Long paymentCategoryId;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
}
