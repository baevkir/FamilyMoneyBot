package com.familymoney.categories.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("payment_categories")
public class CategoryEntity {
    @Id
    private Long id;
    private String name;
}
