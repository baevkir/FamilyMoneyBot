package com.familymoney.telegrambot.business.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Account {
    private Long id;
    private Long chatId;
    private String name;
}
