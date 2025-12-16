package com.bsm.ecommerce_api.ecommerce_api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

import jakarta.validation.Valid;

import com.bsm.ecommerce_api.ecommerce_api.dtos.AddItemToCartRequest;
import com.bsm.ecommerce_api.ecommerce_api.dtos.CartDto;
import com.bsm.ecommerce_api.ecommerce_api.dtos.UpdateProductQuantityInCartRequest;
import com.bsm.ecommerce_api.ecommerce_api.exceptions.CartNotFoundException;
import com.bsm.ecommerce_api.ecommerce_api.exceptions.ProductNotFoundException;
import com.bsm.ecommerce_api.ecommerce_api.services.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
@Tag(name = "Cart", description = "Cart operations")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(
            UriComponentsBuilder uriComponentsBuilder) {
        var cart = cartService.createCart();
        var location = uriComponentsBuilder.path("/carts/{id}").buildAndExpand(cart.getId()).toUri();
        return ResponseEntity.created(location).body(cart);
    }

    @PostMapping("/{cartId}/items")
    @Operation(summary = "Add item to cart", description = "Add item to cart")
    public ResponseEntity<CartDto> addItemToCart(
            @Parameter(description = "Cart ID") @PathVariable UUID cartId,
            @RequestBody AddItemToCartRequest addToCartDto) {
        var cartDto = cartService.addToCart(cartId, addToCartDto.getProductId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartDto);
    }

    @GetMapping("/{id}")
    public CartDto getCart(@PathVariable UUID id) {
        return cartService.getCart(id);
    }

    @PutMapping("/{cartId}/items/{productId}")
    public CartDto updateProductQuantityInCart(@PathVariable UUID cartId,
            @PathVariable Long productId,
            @RequestBody @Valid UpdateProductQuantityInCartRequest updateProductQuantityInCartDto) {
        return cartService.updateProductQuantityInCart(cartId, productId,
                updateProductQuantityInCartDto.getQuantity());
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public CartDto removeItemFromCart(@PathVariable UUID cartId, @PathVariable Long productId) {
        return cartService.removeProductFromCart(cartId, productId);
    }

    @DeleteMapping("/{cartId}/items")
    public CartDto clearCart(@PathVariable UUID cartId) {
        return cartService.clearCart(cartId);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<?> handleCartNotFoundException(CartNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFoundException(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }
}
