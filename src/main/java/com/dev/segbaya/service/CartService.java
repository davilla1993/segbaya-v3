package com.dev.segbaya.service;

import com.dev.segbaya.domain.Cart;
import com.dev.segbaya.domain.CartBook;

import java.util.List;

public interface CartService {
    void createCart(Cart cart);
    void addBookToCart(Long idBook, Long idCart, int quantity);
    void removeBookFromCart(Long idBook, Long idCart);

    List<Cart> getCarts();

    List<CartBook> getCartBooks();
}
