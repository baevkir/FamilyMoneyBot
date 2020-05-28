package com.familymoney.categories.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("user_payment_categories")
public class UserCategoryEntity {
    @Id
    private Long id;
    private Long categoryId;
    private Long userId;
}
