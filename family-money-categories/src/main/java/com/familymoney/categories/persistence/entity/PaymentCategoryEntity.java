package com.familymoney.categories.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class PaymentCategoryEntity {
    @Id
    private Long id;
    private String name;
    private List<Long> userIds;
}
