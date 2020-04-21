package com.familymoney.telegrambot.persistence.entity;

import org.springframework.data.relational.core.mapping.Table;

@Table("payment_type")
public class PaymentType {
    private Long id;
    private String name;
}
