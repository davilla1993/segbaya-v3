package com.dev.segbaya.controller;

import com.dev.segbaya.domain.*;
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
        return ResponseEntity.ok().body("Added successfully");
    }

    @GetMapping("/cart/all")
    public ResponseEntity<List<Cart>> getCarts(){
        return ResponseEntity.ok().body(cartService.getCarts());
    }

    @GetMapping("/cart-books/all")
    public ResponseEntity<List<CartBook>> getCartBooks(){
        return ResponseEntity.ok().body(cartService.getCartBooks());
    }

    @DeleteMapping("/cart-books/delete/{bookId}/{cartId}")
    public ResponseEntity<?> removeBookFromCart(@PathVariable ("bookId") Long bookId,
                                                @PathVariable ("cartId") Long cartId){
        cartService.removeBookFromCart(bookId, cartId );
        return ResponseEntity.ok().body("Removed successfully");
    }
}

@Data
class BookToCartForm{
    private Long idBook;
    private Long idCart;
    private int quantity;
}
