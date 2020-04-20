package com.familymoney.telegrambot.business.model;

import lombok.Data;

@Data
public class PaymentType {
    private Long id;
    private Long chatId;
    private String name;
}
