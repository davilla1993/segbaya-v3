package com.dev.segbaya.controller;

import com.dev.segbaya.domain.Book;
import com.dev.segbaya.domain.Cart;
import com.dev.segbaya.domain.Role;
import com.dev.segbaya.domain.User;
import com.dev.segbaya.service.CartService;
import com.dev.segbaya.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    @PostMapping("/cart/create")
    public ResponseEntity<Cart> saveCart(@RequestBody Cart cart){
        cartService.createCart(cart);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/book/add-to-cart")
    public ResponseEntity<?> addBookToCart(@RequestBody BookToCartForm form){
        cartService.addBookToCart(form.getIdBook(), form.getIdCart(), form.getQuantity());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cart/all")
    public ResponseEntity<List<Cart>> getCarts(){
        return ResponseEntity.ok().body(cartService.getCarts());
    }
}

@Data
class BookToCartForm{
    private Long idBook;
    private Long idCart;
    private int quantity;
}
