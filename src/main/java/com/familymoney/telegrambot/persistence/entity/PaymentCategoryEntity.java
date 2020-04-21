package com.familymoney.telegrambot.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("payment_category")
public class PaymentCategoryEntity {
    @Id
    private Long id;
    private Long chatId;
    private String name;
}
