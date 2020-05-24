package com.familymoney.model;

import lombok.Data;

@Data
public class BotUser {
    private Long id;
    private Long telegramId;
    private String userName;
    private String firstName;
    private String lastName;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
