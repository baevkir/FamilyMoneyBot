package com.familymoney.telegrambot.business.mapper;

import com.familymoney.telegrambot.business.model.PaymentCategory;
import com.familymoney.telegrambot.persistence.entity.PaymentCategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentCategoryMapper {
    PaymentCategory fromEntity(PaymentCategoryEntity entity);
    PaymentCategoryEntity toEntity(PaymentCategory type);
}
