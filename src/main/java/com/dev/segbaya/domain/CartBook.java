package com.dev.segbaya.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCartBook;

    @ManyToOne
    @JoinColumn(name = "idCart")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "idBook")
    private Book book;

    private int quantity;

    private Double sellingPrice;

}
