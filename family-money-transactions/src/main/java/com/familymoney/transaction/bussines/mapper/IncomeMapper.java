package com.familymoney.transaction.bussines.mapper;

import com.familymoney.model.Account;
import com.familymoney.model.BotUser;
import com.familymoney.model.Income;
import com.familymoney.transaction.persistence.entity.IncomeEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IncomeMapper {


    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "accountId", source = "account.id")
    IncomeEntity toEntity(Income type);

    @InheritInverseConfiguration(name = "toEntity")
    Income fromEntity(IncomeEntity entity);
}
