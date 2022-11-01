package com.dev.segbaya.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
//    private Boolean status;
    private String addressOrder;
    private Double shippingCosts;
    private Double totalPrice;
    private String currency;
    private String method;
    private String description;
    private String intent;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idCart", referencedColumnName = "idCart")
    @JsonIgnore
    private Cart cart;

    @ManyToOne
    @JoinColumn(name="idUser", nullable=false)
    private User user;

}
