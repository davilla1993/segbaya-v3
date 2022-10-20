package com.dev.segbaya.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartBooky {
    @Id
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "idCart")
//    private Cart cart;
//
//    @ManyToOne
//    @JoinColumn(name = "idBook")
//    private Book book;
//
//    private int quantity;
//    private Double sellingPrice;

}
