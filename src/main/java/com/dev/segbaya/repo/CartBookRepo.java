package com.dev.segbaya.repo;

import com.dev.segbaya.domain.CartBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartBookRepo extends JpaRepository<CartBook, Long> {

    @Query(value = "DELETE FROM cart_book c WHERE c.id_book =:idBook AND c.id_cart =:idCart", nativeQuery = true)
    Optional<CartBook> removeBook(@Param("idBook") Long idBook, @Param("idCart")Long idCart);

    @Query(value = "SELECT * FROM cart_book c WHERE c.id_cart = ?1", nativeQuery = true)
    Optional<List<CartBook>> findAllCartBooksByCart(Long id_cart);
}
