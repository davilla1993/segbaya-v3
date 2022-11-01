package com.dev.segbaya.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCart;
    private LocalDateTime dateCreated;
    @ManyToOne
    @JoinColumn
    private User user;

    @OneToOne(mappedBy = "cart")
    private Order order;


//    @LazyCollection(LazyCollectionOption.FALSE)
//    @ManyToMany
//    private Collection<Book> books = new ArrayList<>();


    @OneToMany(mappedBy = "cart")
    @JsonIgnore
    private Collection<CartBook> cartBooks;

}
