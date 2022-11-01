package com.dev.segbaya.repo;

import com.dev.segbaya.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {

}
