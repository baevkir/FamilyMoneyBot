package com.familymoney.telegrambot.business.mapper;

import com.familymoney.model.PaymentCategory;
import com.familymoney.telegrambot.persistence.entity.category.PaymentCategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentCategoryMapper {
    PaymentCategory fromEntity(PaymentCategoryEntity entity);
    PaymentCategory fromEntity(PaymentCategoryEntity entity, Long userId);
    PaymentCategoryEntity toEntity(PaymentCategory type);
}
