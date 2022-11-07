package com.dev.segbaya.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublishHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPublishHouse;
    private String name;
    private String email;
    private String password;
    private String image;
    private String address;
    private String website;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "publishHouse")
    private Set<Book> books;

}
