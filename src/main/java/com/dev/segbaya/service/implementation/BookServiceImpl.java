package com.dev.segbaya.service.implementation;

import com.dev.segbaya.controller.BookController;
import com.dev.segbaya.domain.Book;
import com.dev.segbaya.domain.Category;
import com.dev.segbaya.repo.BookRepo;
import com.dev.segbaya.repo.CategoryRepo;
import com.dev.segbaya.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.apache.commons.io.FilenameUtils;
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
import java.util.UUID;
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
    public Book saveBook(String title, String author, String description, Double price, Long idCategory,
                         MultipartFile fileNumeric, MultipartFile fileAudio, MultipartFile fileVideo,
                         MultipartFile fileImage1, MultipartFile fileImage2, MultipartFile fileImage3, MultipartFile fileImage4) {
        Book book = new Book();
        try {

            String fileNumericName = null;
            String fileAudioName = null;
            String fileVideoName = null;
            String fileImage1Name = null;
            String fileImage2Name = null;
            String fileImage3Name = null;
            String fileImage4Name = null;

            if (!fileNumeric.isEmpty()) {
                fileNumericName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(fileNumeric.getOriginalFilename());
                book.setUrlNumeric(MvcUriComponentsBuilder
                        .fromMethodName(BookController.class, "getFile", fileNumericName.toString()).build().toString());
                Files.copy(fileNumeric.getInputStream(), this.root.resolve(fileNumericName));
            }


            if (!fileAudio.isEmpty()) {
                fileAudioName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(fileAudio.getOriginalFilename());
                book.setUrlAudio(MvcUriComponentsBuilder
                        .fromMethodName(BookController.class, "getFile", fileAudioName.toString()).build().toString());
                Files.copy(fileAudio.getInputStream(), this.root.resolve(fileAudioName));
            }

            if (!fileVideo.isEmpty()) {
                fileVideoName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(fileVideo.getOriginalFilename());
                book.setUrlVideo(MvcUriComponentsBuilder
                        .fromMethodName(BookController.class, "getFile", fileVideoName.toString()).build().toString());
                Files.copy(fileVideo.getInputStream(), this.root.resolve(fileVideoName));
            }

            if (!fileImage1.isEmpty()) {
                fileImage1Name = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(fileImage1.getOriginalFilename());
                book.setUrlImage1(MvcUriComponentsBuilder
                        .fromMethodName(BookController.class, "getFile", fileImage1Name.toString()).build().toString());
                Files.copy(fileImage1.getInputStream(), this.root.resolve(fileImage1Name));
            }

            if (!fileImage2.isEmpty()) {
                fileImage2Name = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(fileImage2.getOriginalFilename());
                book.setUrlImage2(MvcUriComponentsBuilder
                        .fromMethodName(BookController.class, "getFile", fileImage2Name.toString()).build().toString());
                Files.copy(fileImage2.getInputStream(), this.root.resolve(fileImage2Name));
            }

            if (!fileImage3.isEmpty()) {
                fileImage3Name = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(fileImage3.getOriginalFilename());
                book.setUrlImage3(MvcUriComponentsBuilder
                        .fromMethodName(BookController.class, "getFile", fileImage3Name.toString()).build().toString());
                Files.copy(fileImage3.getInputStream(), this.root.resolve(fileImage3Name));
            }

            if (!fileImage4.isEmpty()) {
                fileImage4Name = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(fileImage4.getOriginalFilename());
                book.setUrlImage4(MvcUriComponentsBuilder
                        .fromMethodName(BookController.class, "getFile", fileImage4Name.toString()).build().toString());
                Files.copy(fileImage4.getInputStream(), this.root.resolve(fileImage4Name));
            }

            Category category = categoryRepo.findById(idCategory).orElseThrow((() -> new IllegalStateException(
                    "Category with id " + idCategory + "does not exist")
            ));
            book.setCategory(category);

            book.setFileNumericName(fileNumericName);
            book.setFileAudioName(fileAudioName);
            book.setFileVideoName(fileVideoName);
            book.setFileImage1(fileImage1Name);
            book.setFileImage2(fileImage2Name);
            book.setFileImage3(fileImage3Name);
            book.setFileImage4(fileImage4Name);

            book.setTitle(title);
            book.setAuthor(author);
            book.setDescription(description);
            book.setPrice(price);
            book.setSize((fileNumeric.getSize() / 1000) + " Ko");
            book.setPublishDate(LocalDateTime.now());

            return bookRepo.save(book);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Book updateBook(Long idBook, String title, String author, String description, Double price, Long idCategory, MultipartFile fileNumeric, MultipartFile fileAudio, MultipartFile fileVideo) {
        Book book = bookRepo.findById(idBook).orElseThrow((() -> new IllegalStateException(
                "Book with id " + idBook + "does not exist")
        ));

        try {
            String extNumeric = FilenameUtils.getExtension(fileNumeric.getOriginalFilename());
            String extAudio = FilenameUtils.getExtension(fileAudio.getOriginalFilename());
            String extVideo = FilenameUtils.getExtension(fileVideo.getOriginalFilename());
            String fileNumericName = UUID.randomUUID().toString() + "." + extNumeric;
            String fileAudioName = UUID.randomUUID().toString() + "." + extAudio;
            String fileVideoName = UUID.randomUUID().toString() + "." + extVideo;

            book.setTitle(title);
            book.setFileNumericName(fileNumericName);
            book.setFileAudioName(fileAudioName);
            book.setFileVideoName(fileVideoName);

            Category category = categoryRepo.findById(idCategory).orElseThrow((() -> new IllegalStateException(
                    "Category with id " + idCategory + "does not exist")
            ));
            book.setCategory(category);

            book.setUrlNumeric(MvcUriComponentsBuilder
                    .fromMethodName(BookController.class, "getFile", fileNumericName.toString()).build().toString());
            book.setUrlAudio(MvcUriComponentsBuilder
                    .fromMethodName(BookController.class, "getFile", fileAudioName.toString()).build().toString());
            book.setUrlVideo(MvcUriComponentsBuilder
                    .fromMethodName(BookController.class, "getFile", fileVideoName.toString()).build().toString());
            book.setAuthor(author);
            book.setDescription(description);
            book.setPrice(price);
            book.setSize((fileNumeric.getSize() /1000) + " Ko");
            book.setPublishDate(LocalDateTime.now());
            Files.copy(fileNumeric.getInputStream(), this.root.resolve(fileNumericName));
            Files.copy(fileAudio.getInputStream(), this.root.resolve(fileAudioName));
            Files.copy(fileVideo.getInputStream(), this.root.resolve(fileVideoName));
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

    @Transactional
    @Override
    public Category updateCategory(Long idCategory, Category category) {
        Category categoryOptional = categoryRepo.findById(idCategory).orElseThrow((() -> new IllegalStateException(
                "Category with id " + idCategory + "does not exist")
        ));

        if (category.getName() != null &&
                category.getName().length() > 0 ) {
            categoryOptional.setName(category.getName());
        }
        return null;
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

    @Transactional
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
        bookRepo.deleteById(book.getIdBook());
    }

    @Override
    public void deleteCategory(Long idCategory) {
        Category category = categoryRepo.findById(idCategory).orElseThrow(
                (() -> new IllegalStateException(
                        "Book with id "+ idCategory + " does not exist")
                ));
        categoryRepo.deleteById(category.getIdCategory());
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
