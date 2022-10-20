package com.dev.segbaya.service;

import com.dev.segbaya.domain.Cart;

import java.util.List;

public interface CartService {
    void createCart(Cart cart);
    void addBookToCart(Long idBook, Long idCart, int quantity);

    List<Cart> getCarts();
}
