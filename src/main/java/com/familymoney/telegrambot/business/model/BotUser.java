package com.familymoney.telegrambot.business.model;

import lombok.Data;

@Data
public class BotUser {
    private Long id;
    private Integer telegramId;
    private String userName;
    private String firstName;
    private String lastName;
}
