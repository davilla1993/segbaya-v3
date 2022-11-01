package com.dev.segbaya.service.implementation;

import com.dev.segbaya.domain.*;
import com.dev.segbaya.repo.CartBookRepo;
import com.dev.segbaya.repo.CartRepo;
import com.dev.segbaya.repo.OrderRepo;
import com.dev.segbaya.repo.UserRepo;
import com.dev.segbaya.service.OrderService;
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
public class OrderServiceImpl implements OrderService {
    private final CartBookRepo cartBookRepo;
    private final UserRepo userRepo;
    private final CartRepo cartRepo;
    private final OrderRepo orderRepo;
    @Override
    public void createOrder(Long idUser, Long idCart, Order order) {
        User user = userRepo.findById(idUser).orElseThrow(
                (() -> new IllegalStateException(
                        "User with id "+ idUser + " does not exist")
                )
        );

        List<CartBook> cartBooks = cartBookRepo.findAllCartBooksByCart(idCart).orElseThrow(
                (() -> new IllegalStateException(
                        "Cart with id "+ idCart + " does not exist")
                )
        );

        Cart cart = cartRepo.findById(idCart).orElseThrow(
                (() -> new IllegalStateException(
                        "Cart with id "+ idCart + " does not exist")
                )
        );

        Double amount = 0.0;

        for (CartBook c: cartBooks) {
            amount += c.getSellingPrice();
        }

        order.setAmountOrder(amount);
        order.setDateOrder(LocalDateTime.now());
        order.setTotalPrice(amount + order.getShippingCosts());
        order.setUser(user);

        order.setCart(cart);

//        System.out.println(cartBooks);
        orderRepo.save(order);

    }

    @Override
    public List<Order> getOrders() {
        return null;
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepo.findById(orderId).orElseThrow(
                (() -> new IllegalStateException(
                        "Order with id "+ orderId + " does not exist")
                ));
    }

    @Override
    public void deleteOrder(Long idOrder) {
        Order order = orderRepo.findById(idOrder).orElseThrow(
                (() -> new IllegalStateException(
                        "Order with id "+ idOrder + " does not exist")
                ));
        orderRepo.deleteById(order.getIdOrder());
    }
}
