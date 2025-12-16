package com.bsm.ecommerce_api.ecommerce_api.services;
import java.util.UUID;
import org.springframework.stereotype.Service;

import com.bsm.ecommerce_api.ecommerce_api.dtos.CartDto;
import com.bsm.ecommerce_api.ecommerce_api.entities.Cart;
import com.bsm.ecommerce_api.ecommerce_api.exceptions.CartNotFoundException;
import com.bsm.ecommerce_api.ecommerce_api.exceptions.ProductNotFoundException;
import com.bsm.ecommerce_api.ecommerce_api.mappers.CartMapper;
import com.bsm.ecommerce_api.ecommerce_api.repositories.CartRepository;
import com.bsm.ecommerce_api.ecommerce_api.repositories.ProductRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CartService {
    private final CartMapper cartMapper;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartDto createCart() {
        var cart = new Cart();
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartDto addToCart(UUID cartId, Long productId) {
        var cart = cartRepository.getCartWithItems(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        cart.addItem(product);
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartDto getCart(UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));
        return cartMapper.toDto(cart);
    }

    public CartDto updateProductQuantityInCart(UUID cartId, Long productId, Integer quantity) {
        var cart = cartRepository.getCartWithItems(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));
        var product = cart.getItem(productId);
        if (product == null) {
            throw new ProductNotFoundException("Product not found");
        }
        product.setQuantity(quantity);
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartDto removeProductFromCart(UUID cartId, Long productId) {
        var cart = cartRepository.getCartWithItems(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));
        var product = cart.getItem(productId);
        if (product == null) {
            throw new ProductNotFoundException("Product not found");
        }
        cart.removeItem(productId);
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartDto clearCart(UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));
        cart.clear();
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }
}
