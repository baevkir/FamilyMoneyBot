package com.familymoney.telegrambot.business.mapper;
import com.familymoney.telegrambot.business.model.Account;
import com.familymoney.telegrambot.persistence.entity.AccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account fromEntity(AccountEntity entity);
    AccountEntity toEntity(Account type);
}
