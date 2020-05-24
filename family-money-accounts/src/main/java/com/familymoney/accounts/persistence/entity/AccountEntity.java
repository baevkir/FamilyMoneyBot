package com.familymoney.accounts.persistence.entity;

import com.familymoney.model.BotUser;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class AccountEntity {
    @Id
    private Long id;
    private String name;
    private List<Long> userIds;
}
