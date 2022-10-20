package com.dev.segbaya.service.implementation;

import com.dev.segbaya.controller.BookController;
import com.dev.segbaya.domain.Book;
import com.dev.segbaya.domain.Category;
import com.dev.segbaya.repo.BookRepo;
import com.dev.segbaya.repo.CategoryRepo;
import com.dev.segbaya.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final Path root = Paths.get("uploads");
    private final BookRepo bookRepo;
    private final CategoryRepo categoryRepo;

    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Transactional
    @Override
    public Book saveBook(String title, String author, String description, Double price, Long idCategory, MultipartFile fileNumeric, MultipartFile fileAudio) {
        Book book = new Book();
        try {
            book.setTitle(title);
            book.setFileNumericName(fileNumeric.getOriginalFilename());
            book.setFileAudioName(fileAudio.getOriginalFilename());

            Category category = categoryRepo.findById(idCategory).orElseThrow((() -> new IllegalStateException(
                    "Category with id " + idCategory + "does not exist")
            ));
            book.setCategory(category);

            book.setUrlNumeric(MvcUriComponentsBuilder
                    .fromMethodName(BookController.class, "getFile", fileNumeric.getOriginalFilename().toString()).build().toString());
            book.setUrlAudio(MvcUriComponentsBuilder
                    .fromMethodName(BookController.class, "getFile", fileAudio.getOriginalFilename().toString()).build().toString());
            book.setAuthor(author);
            book.setDescription(description);
            book.setPrice(price);
            book.setSize((fileNumeric.getSize() /1000) + " Ko");
            book.setPublishDate(LocalDateTime.now());
            Files.copy(fileNumeric.getInputStream(), this.root.resolve(fileNumeric.getOriginalFilename()));
            Files.copy(fileAudio.getInputStream(), this.root.resolve(fileAudio.getOriginalFilename()));
            return bookRepo.save(book);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Category saveCategory(Category category) {
        Optional<Category> categoryOptional = categoryRepo.findByName(category.getName());

        if (categoryOptional.isPresent()) {
            throw new IllegalStateException("Category "+ category.getName() + " already add");
        }

        return categoryRepo.save(category);
    }


    @Override
    public Resource load(String fileName) {
        try {
            Path file = root.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void addCategoryToBook(Long idBook, Long idCategory) {
        Book book = bookRepo.findById(idBook).orElseThrow((() -> new IllegalStateException(
                "Book with id " + idBook + "does not exist")
        ));

        Category category = categoryRepo.findById(idCategory).orElseThrow((() -> new IllegalStateException(
                "Category with id " + idCategory + "does not exist")
        ));

        book.setCategory(category);
    }

    @Override
    public List<Book> getBooks() {
        return bookRepo.findAll();
    }

    @Override
    public Book getBook(Long id) {
        return bookRepo.findById(id).orElseThrow(
                (() -> new IllegalStateException(
                        "Book with id "+ id + " does not exist")
                )
        );
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root,1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }


    @Override
    public List<Category> getCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepo.findById(id).orElseThrow(
                (() -> new IllegalStateException(
                        "Book with id "+ id + " does not exist")
                ));
        bookRepo.deleteById(id);
    }


//    @Override
//    public Stream<Path> loadAll() {
//        try {
//            List<Book> books = storageService.loadAll().map(path -> {
//                String bookName = path.getFileName().toString();
//                String url = MvcUriComponentsBuilder
//                        .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();
//
//                return new FileInfo(null, bookName, url);
//            }).collect(Collectors.toList());
//            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
//        } catch (IOException e) {
//            throw new RuntimeException("Could not load the files!");
//        }
//    }
}
