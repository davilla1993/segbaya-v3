package com.dev.segbaya.service;

import com.dev.segbaya.domain.Order;

import java.util.List;

public interface OrderService {
    void createOrder(Long idUser, Long idCartBook, Order order);

    List<Order> getOrders();

    Order getOrderById(Long orderId);

    void deleteOrder(Long idOrder);
}
