package com.mylinks.auth.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mylinks.repo.CartItemRepository;
import com.mylinks.repo.CartRepository;
import com.mylinks.users.entity.Cart;
import com.mylinks.users.entity.CartItem;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public void addToCart(UUID userId, UUID productId, int qty, BigDecimal price) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(new Cart(null, userId)));

        CartItem item = new CartItem();
        item.setCartId(cart.getId());
        item.setProductId(productId);
        item.setQuantity(qty);
        item.setPrice(price);

        cartItemRepository.save(item);
    }
}
