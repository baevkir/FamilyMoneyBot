package com.familymoney.accounts.bussines.mapper;

import com.familymoney.accounts.persistence.entity.AccountEntity;
import com.familymoney.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account fromEntity(AccountEntity entity);
    Account fromEntity(AccountEntity entity, Long userId);

    AccountEntity toEntity(Account type);
}
