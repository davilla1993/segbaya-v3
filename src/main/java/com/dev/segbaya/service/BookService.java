package com.dev.segbaya.service;

import com.dev.segbaya.domain.Book;
import com.dev.segbaya.domain.Category;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface BookService {
    void init();
    Book saveBook(String title,
                  String author,
                  String description,
                  Double price,
                  Long idCategory,
                  MultipartFile fileNumeric,
                  MultipartFile fileAudio,
                  MultipartFile fileVideo,
                  MultipartFile fileImage1,
                  MultipartFile fileImage2,
                  MultipartFile fileImage3,
                  MultipartFile fileImage4);

    Book updateBook(Long idBook,
                    String title,
                  String author,
                  String description,
                  Double price,
                  Long idCategory,
                  MultipartFile fileNumeric,
                  MultipartFile fileAudio,
                  MultipartFile fileVideo);



    Category saveCategory(Category category);

    Category updateCategory(Long id, Category category);

    Resource load(String fileName);

    void addCategoryToBook(Long idBook, Long idCategory);

    List<Book> getBooks();

    Book getBook(Long id);

    public Stream<Path> loadAll();

    List<Category> getCategories();

    void deleteBook(Long id);

    void deleteCategory(Long idCategory);
}
