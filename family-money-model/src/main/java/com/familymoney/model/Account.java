package com.familymoney.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "Builder")
public class Account {
    private Long id;
    private String name;
    private Long userId;
}
