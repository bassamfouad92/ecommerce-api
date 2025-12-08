package com.bsm.ecommerce_api.ecommerce_api.dtos;

import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private CartItemProductDto product;
    private Integer quantity;
}
