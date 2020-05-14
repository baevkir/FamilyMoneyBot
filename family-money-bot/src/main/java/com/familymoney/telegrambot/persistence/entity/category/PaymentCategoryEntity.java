package com.familymoney.telegrambot.persistence.entity.category;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("payment_categories")
public class PaymentCategoryEntity {
    @Id
    private Long id;
    private String name;
}
