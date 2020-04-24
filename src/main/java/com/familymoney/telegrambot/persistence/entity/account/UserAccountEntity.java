package com.familymoney.telegrambot.persistence.entity.account;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder(builderClassName = "Builder")
@Table("user_accounts")
public class UserAccountEntity {
    @Id
    private Long id;
    private Long userId;
    private Long accountId;

}
