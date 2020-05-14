package com.familymoney.telegrambot.persistence.entity.category;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder(builderClassName = "Builder")
@Table("user_payment_categories")
public class UserPaymentCategoryEntity {
    @Id
    private Long id;
    private Long userId;
    private Long paymentCategoryId;
}
