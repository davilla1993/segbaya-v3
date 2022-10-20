package com.dev.segbaya.repo;

import com.dev.segbaya.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart, Long> {
}
