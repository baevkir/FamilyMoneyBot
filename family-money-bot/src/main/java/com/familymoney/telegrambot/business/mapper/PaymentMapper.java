package com.familymoney.telegrambot.business.mapper;

import com.familymoney.model.Account;
import com.familymoney.model.BotUser;
import com.familymoney.model.Payment;
import com.familymoney.model.PaymentCategory;
import com.familymoney.telegrambot.persistence.entity.PaymentEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {


    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "accountId", source = "account.id")
    @Mapping(target = "paymentCategoryId", source = "category.id")
    PaymentEntity toEntity(Payment payment);

    @InheritInverseConfiguration(name = "toEntity")
    Payment fromEntity(PaymentEntity entity);


    @Mapping(target = "id", source = "entity.id")
    Payment fromEntity(PaymentEntity entity, BotUser user, Account account, PaymentCategory category);
}
