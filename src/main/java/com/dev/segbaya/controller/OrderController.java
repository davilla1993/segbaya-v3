package com.dev.segbaya.controller;

import com.dev.segbaya.domain.Cart;
import com.dev.segbaya.domain.Order;
import com.dev.segbaya.domain.User;
import com.dev.segbaya.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order/create/{idUser}/{idCart}")
    public ResponseEntity<Order> saveCart(@RequestBody Order order,
                                          @PathVariable Long idUser,
                                          @PathVariable Long idCart){
        orderService.createOrder(idUser, idCart, order);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/order/find/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable ("orderId") Long orderId){
        return ResponseEntity.ok().body(orderService.getOrderById(orderId));
    }

    @DeleteMapping("/order/delete/{orderId}")
    public ResponseEntity<?> deleteUser(@PathVariable ("orderId") Long orderId){
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok().body("Deleted successfully !");
    }

}





