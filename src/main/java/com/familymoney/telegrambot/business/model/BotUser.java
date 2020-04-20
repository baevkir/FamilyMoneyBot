package com.familymoney.telegrambot.business.model;

import lombok.Data;

@Data
public class BotUser {
    private Integer id;
    private String userName;
    private String firstName;
    private String lastName;
    private String familyName;
}
