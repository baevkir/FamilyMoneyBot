package com.familymoney.telegrambot.business.mapper;

import com.familymoney.telegrambot.business.model.Account;
import com.familymoney.telegrambot.persistence.entity.account.AccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account fromEntity(AccountEntity entity);

    Account fromEntity(AccountEntity entity, Long userId);

    AccountEntity toEntity(Account type);
}
