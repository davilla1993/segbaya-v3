package com.dev.segbaya.domain;

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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCart;
    private LocalDateTime dateCreated;
//    @ManyToOne
//    @JoinColumn
//    private User user;

    @OneToOne(mappedBy = "cart")
    private Order order;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private Collection<Book> books = new ArrayList<>();

//    @OneToMany(mappedBy = "cart")
//    private Set<CartBook> cartBooks;
}
