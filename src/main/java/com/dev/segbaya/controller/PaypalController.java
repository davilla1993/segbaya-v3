package com.dev.segbaya.controller;

import com.dev.segbaya.domain.Order;
import com.dev.segbaya.service.OrderService;
import com.dev.segbaya.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/payment/paypal")
@AllArgsConstructor
public class PaypalController {
    private final PaypalService paypalService;
    private final OrderService orderService;

    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    @PostMapping("/pay/{orderId}")
    public ResponseEntity<?> payment(@PathVariable ("orderId") Long orderId) {
        final Order order = orderService.getOrderById(orderId);

        try {
            Payment payment = paypalService.createPayment(order.getTotalPrice(), order.getCurrency(), order.getMethod(), order.getIntent(),
                     order.getDescription(), "http://localhost:8083/api/" + CANCEL_URL,
                    "http://localhost:8083/api/" + SUCCESS_URL);
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return ResponseEntity.ok().body("redirect:"+link.getHref());
                }
            }

        } catch (PayPalRESTException e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
        return ResponseEntity.ok().body("redirect:/");
    }

    @GetMapping(value = CANCEL_URL)
    public ResponseEntity<?> cancelPay() {
        return ResponseEntity.ok().body("cancel");
    }

    @GetMapping(value = SUCCESS_URL)
    public ResponseEntity<?> successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return ResponseEntity.ok().body("success");
            }
        } catch (PayPalRESTException e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
        return ResponseEntity.ok().body("redirect:/");
    }
}
