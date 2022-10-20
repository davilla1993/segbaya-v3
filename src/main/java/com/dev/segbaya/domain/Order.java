package com.dev.segbaya.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idOrder;
    private LocalDateTime dateOrder;
    private Double amountOrder;
    private Boolean status;
    private String addressOrder;
    private Double shippingCosts;
    private Double totalPrice;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idCart", referencedColumnName = "idCart")
    private Cart cart;

    @ManyToOne
    private User user;

}
