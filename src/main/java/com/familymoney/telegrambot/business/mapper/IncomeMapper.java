package com.familymoney.telegrambot.business.mapper;

import com.familymoney.telegrambot.business.model.Account;
import com.familymoney.telegrambot.business.model.BotUser;
import com.familymoney.telegrambot.business.model.Income;
import com.familymoney.telegrambot.business.model.Payment;
import com.familymoney.telegrambot.business.model.PaymentCategory;
import com.familymoney.telegrambot.persistence.entity.IncomeEntity;
import com.familymoney.telegrambot.persistence.entity.PaymentEntity;
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


    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "chatId", source = "entity.chatId")
    Income fromEntity(IncomeEntity entity, BotUser user, Account account);
}
