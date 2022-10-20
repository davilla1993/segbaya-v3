package com.dev.segbaya.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idBook;
    private String title;
    private String fileNumericName;
    private String fileAudioName;
    private String urlNumeric;
    private String urlAudio;
    private String author;
    @Column(length = 500)
    private String description;
    private String size;
    private Double price;
    @ManyToOne
    @JoinColumn(name = "idCategory")
    private Category category;

    private LocalDateTime publishDate;
//    private LocalDateTime lastUpdate;

//    @OneToMany(mappedBy = "book")
//    private List<CartBook> cartBooks;


    public Book(String title) {
        this.title = title;
    }

    public Long getIdBook() {
        return idBook;
    }

    public void setIdBook(Long idBook) {
        this.idBook = idBook;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileNumericName() {
        return fileNumericName;
    }

    public void setFileNumericName(String fileNumericName) {
        this.fileNumericName = fileNumericName;
    }

    public String getFileAudioName() {
        return fileAudioName;
    }

    public void setFileAudioName(String fileAudioName) {
        this.fileAudioName = fileAudioName;
    }

    public String getUrlNumeric() {
        return urlNumeric;
    }

    public void setUrlNumeric(String urlNumeric) {
        this.urlNumeric = urlNumeric;
    }

    public String getUrlAudio() {
        return urlAudio;
    }

    public void setUrlAudio(String urlAudio) {
        this.urlAudio = urlAudio;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }
}
