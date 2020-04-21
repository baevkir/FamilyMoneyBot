package com.familymoney.telegrambot.business.mapper;
import com.familymoney.telegrambot.business.model.PaymentType;
import com.familymoney.telegrambot.persistence.entity.PaymentTypeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentTypeMapper {
    PaymentType fromEntity(PaymentTypeEntity entity);
    PaymentTypeEntity toEntity(PaymentType type);
}
