package com.familymoney.accounts.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("user_accounts")
public class UserAccountEntity {
    @Id
    private Long id;
    private Long accountId;
    private Long userId;
}
