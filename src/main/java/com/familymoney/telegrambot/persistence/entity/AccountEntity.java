package com.familymoney.telegrambot.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("accounts")
public class AccountEntity {
    @Id
    private Long id;
    private Long chatId;
    private String name;
}
