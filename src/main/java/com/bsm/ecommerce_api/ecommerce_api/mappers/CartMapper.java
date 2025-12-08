package com.bsm.ecommerce_api.ecommerce_api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.bsm.ecommerce_api.ecommerce_api.dtos.CartItemDto;
import com.bsm.ecommerce_api.ecommerce_api.entities.CartItem;
import com.bsm.ecommerce_api.ecommerce_api.dtos.CartItemProductDto;
import com.bsm.ecommerce_api.ecommerce_api.entities.Product;
import com.bsm.ecommerce_api.ecommerce_api.dtos.CartDto;
import com.bsm.ecommerce_api.ecommerce_api.entities.Cart;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "totalPrice", expression = "java(cart.calculateTotalPrice())")
    CartDto toDto(Cart cart);

    @Mapping(target = "product", source = "product")
    CartItemDto toDto(CartItem cartItem);

    CartItemProductDto toDto(Product product);
}