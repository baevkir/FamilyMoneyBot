package com.familymoney.categories.bussines.mapper;

import com.familymoney.categories.persistence.entity.CategoryEntity;
import com.familymoney.model.PaymentCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    PaymentCategory fromEntity(CategoryEntity entity);
    PaymentCategory fromEntity(CategoryEntity entity, Long userId);
    CategoryEntity toEntity(PaymentCategory type);
}
