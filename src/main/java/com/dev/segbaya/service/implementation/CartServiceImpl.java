package com.dev.segbaya.service.implementation;

import com.dev.segbaya.domain.Book;
import com.dev.segbaya.domain.Cart;
import com.dev.segbaya.domain.CartBook;
import com.dev.segbaya.repo.BookRepo;
import com.dev.segbaya.repo.CartBookRepo;
import com.dev.segbaya.repo.CartRepo;
import com.dev.segbaya.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;
    private final BookRepo bookRepo;
    private final CartBookRepo cartBookRepo;

    @Override
    public void createCart(Cart cart) {
//        cart.setUser(userRepo.findById(idUser).orElseThrow(
//                (() -> new IllegalStateException("User with id " + idUser + "does not exist"))
//        ));
//
        cart.setDateCreated(LocalDateTime.now());
        cartRepo.save(cart);
    }

    @Transactional
    @Override
    public void addBookToCart(Long idBook, Long idCart, int quantity) {
        Book book = bookRepo.findById(idBook).orElseThrow(
                (() -> new IllegalStateException(
                        "Book with id "+ idBook + " does not exist")
                )
        );

        Cart cart = cartRepo.findById(idCart).orElseThrow(
                (() -> new IllegalStateException(
                        "Cart with id "+ idCart + " does not exist")
                )
        );

        CartBook cartBook = new CartBook();
        cartBook.setBook(book);
        cartBook.setCart(cart);
        cartBook.setQuantity(quantity);
        cartBook.setSellingPrice(book.getPrice()*quantity);
//        cart.getBooks().add(book);
        cartBookRepo.save(cartBook);

    }

    @Override
    public void removeBookFromCart(Long idBook, Long idCart) {
        Book book = bookRepo.findById(idBook).orElseThrow(
                (() -> new IllegalStateException(
                        "Book with id "+ idBook + " does not exist")
                )
        );

        Cart cart = cartRepo.findById(idCart).orElseThrow(
                (() -> new IllegalStateException(
                        "Cart with id "+ idCart + " does not exist")
                )
        );
        cartBookRepo.removeBook(idBook, idCart);
    }

    @Override
    public List<Cart> getCarts() {
        return cartRepo.findAll();
    }

    @Override
    public List<CartBook> getCartBooks() {
        return cartBookRepo.findAll();
    }
}
