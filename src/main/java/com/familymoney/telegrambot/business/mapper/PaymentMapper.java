package com.familymoney.telegrambot.business.mapper;

import com.familymoney.telegrambot.business.model.Account;
import com.familymoney.telegrambot.business.model.BotUser;
import com.familymoney.telegrambot.business.model.Payment;
import com.familymoney.telegrambot.business.model.PaymentCategory;
import com.familymoney.telegrambot.persistence.entity.PaymentEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {


    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "paymentTypeId", source = "type.id")
    @Mapping(target = "paymentCategoryId", source = "category.id")
    PaymentEntity toEntity(Payment type);

    @InheritInverseConfiguration(name = "toEntity")
    Payment fromEntity(PaymentEntity entity);


    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "chatId", source = "entity.chatId")
    Payment fromEntity(PaymentEntity entity, BotUser user, Account type, PaymentCategory category);
}
