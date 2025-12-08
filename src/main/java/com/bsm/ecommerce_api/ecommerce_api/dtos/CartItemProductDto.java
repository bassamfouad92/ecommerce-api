package com.bsm.ecommerce_api.ecommerce_api.dtos;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CartItemProductDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
}
