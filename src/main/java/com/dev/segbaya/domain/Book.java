package com.dev.segbaya.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idBook;
    private String title;
    private String fileNumericName;
    private String fileAudioName;
    private String fileVideoName;
    private String fileImage1;
    private String fileImage2;
    private String fileImage3;
    private String fileImage4;
    private String urlNumeric;
    private String urlAudio;
    private String urlVideo;
    private String urlImage1;
    private String urlImage2;
    private String urlImage3;
    private String urlImage4;
    private String author;
    @Column(length = 500)
    private String description;
    private String size;
    private Double price;
    private Boolean isPublished;
    @ManyToOne
    @JoinColumn(name = "idCategory")
    private Category category;

    private LocalDateTime publishDate;
//    private LocalDateTime lastUpdate;

    @ManyToOne
    @JoinColumn(name = "idPublishHouse")
    private PublishHouse publishHouse;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private Collection<CartBook> cartBooks;

    @CreatedDate
    private Date createdAt;

    @LastModifiedBy
    private Date updatedAt;

    public Book(String title) {
        this.title = title;
    }

}
