package com.bsm.ecommerce_api.ecommerce_api.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddItemToCartRequest {
    @NotNull(message = "Product ID is required")
    private Long productId;
}
