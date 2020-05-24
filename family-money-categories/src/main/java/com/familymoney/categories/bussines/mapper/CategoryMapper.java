package com.familymoney.categories.bussines.mapper;

import com.familymoney.model.PaymentCategory;
import org.mapstruct.Mapper;

import com.familymoney.categories.persistence.entity.PaymentCategoryEntity;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    PaymentCategory fromEntity(PaymentCategoryEntity entity);
    PaymentCategory fromEntity(PaymentCategoryEntity entity, Long userId);
    PaymentCategoryEntity toEntity(PaymentCategory type);
}
