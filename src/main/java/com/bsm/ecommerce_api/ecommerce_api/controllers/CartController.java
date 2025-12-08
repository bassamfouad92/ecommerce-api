package com.bsm.ecommerce_api.ecommerce_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import com.bsm.ecommerce_api.ecommerce_api.repositories.CartRepository;
import com.bsm.ecommerce_api.ecommerce_api.repositories.ProductRepository;

import jakarta.validation.Valid;

import com.bsm.ecommerce_api.ecommerce_api.dtos.AddItemToCartRequest;
import com.bsm.ecommerce_api.ecommerce_api.dtos.CartDto;
import com.bsm.ecommerce_api.ecommerce_api.dtos.CartItemDto;
import com.bsm.ecommerce_api.ecommerce_api.dtos.UpdateProductQuantityInCartRequest;
import com.bsm.ecommerce_api.ecommerce_api.entities.Cart;
import com.bsm.ecommerce_api.ecommerce_api.entities.CartItem;
import com.bsm.ecommerce_api.ecommerce_api.mappers.CartMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    @PostMapping
    public ResponseEntity<CartDto> createCart(
            UriComponentsBuilder uriComponentsBuilder) {
        var cart = new Cart();
        cartRepository.save(cart);
        var cartDto = cartMapper.toDto(cart);
        var location = uriComponentsBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(location).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartDto> addToCart(@PathVariable UUID cartId,
            @RequestBody AddItemToCartRequest addToCartDto) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            return ResponseEntity.badRequest().build();
        }
        var product = productRepository.findById(addToCartDto.getProductId()).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        cart.addItem(product);
        cartRepository.save(cart);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartMapper.toDto(cart));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID id) {
        var cart = cartRepository.getCartWithItems(id).orElse(null);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartMapper.toDto(cart));
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> updateProductQuantityInCart(@PathVariable UUID cartId,
            @PathVariable Long productId,
            @RequestBody @Valid UpdateProductQuantityInCartRequest updateProductQuantityInCartDto) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("message", "Cart not found"));
        }
        var product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("message", "Product not found"));
        }
        var cartItem = cart.getItem(productId);
        if (cartItem == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("message", "Product not found in cart"));
        }
        cartItem.setQuantity(updateProductQuantityInCartDto.getQuantity());
        cartRepository.save(cart);
        return ResponseEntity.ok(cartMapper.toDto(cart));
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable UUID cartId, @PathVariable Long productId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("message", "Cart not found"));
        }
        cart.removeItem(productId);
        cartRepository.save(cart);
        return ResponseEntity.noContent().build();
    }
}
