package com.familymoney.users.persistence;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("bot_users")
public class UserEntity {
    @Id
    private Long id;
    private Long telegramId;
    private String userName;
    private String firstName;
    private String lastName;
}
