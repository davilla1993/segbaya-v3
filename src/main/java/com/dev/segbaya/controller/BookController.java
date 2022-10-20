package com.dev.segbaya.controller;

import com.dev.segbaya.domain.Book;
import com.dev.segbaya.domain.Category;
import com.dev.segbaya.message.ResponseMessage;
import com.dev.segbaya.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/book/upload")
    public ResponseEntity<ResponseMessage> uploadBook(@RequestParam MultipartFile fileNumeric,
                                                      @RequestParam MultipartFile fileAudio,
                                                      @RequestParam String title,
                                                      @RequestParam String author,
                                                      @RequestParam String description,
                                                      @RequestParam Double price,
                                                      @RequestParam Long idCategory) {
        String message = "";
        try {
            bookService.saveBook(title, author, description, price, idCategory, fileNumeric, fileAudio);
            message = "Files are uploaded successfully !";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the files !" ;
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/books")
    public ResponseEntity<Stream<Path>> getListBooks() {
        final Stream<Path> pathStreams = bookService.loadAll();
        pathStreams.map(path -> {
            String bookName = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(BookController.class, "getFile", path.getFileName().toString()).build().toString();

            return new Book(bookName);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(pathStreams);
    }

    @GetMapping("/books/{bookName:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String bookName) {
        Resource file = bookService.load(bookName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/book/find/{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable ("bookId") Long bookId){
        return ResponseEntity.ok().body(bookService.getBook(bookId));
    }

    @DeleteMapping("/book/delete/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable ("bookId") Long bookId){
        bookService.deleteBook(bookId);
        return ResponseEntity.ok().body("Deleted successfully !");
    }

    @GetMapping("/book/all")
    public ResponseEntity<List<Book>> getBooks(){
        return ResponseEntity.ok().body(bookService.getBooks());
    }

    @PostMapping("/category/save")
    public ResponseEntity<Category> saveCategory(@RequestBody Category category){
        URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().
                path("/api/category/save").toUriString());
        return ResponseEntity.created(uri).body(bookService.saveCategory(category));
    }

    @GetMapping("/category/all")
    public ResponseEntity<List<Category>> getCategories(){
        return ResponseEntity.ok().body(bookService.getCategories());
    }

//    @PostMapping("/add-category-book")
//    public ResponseEntity<?> addCategoryToBook(@RequestBody RoleToUserForm form){
//        userService.addRoleToUser(form.getEmail(), form.getRoleName());
//        return ResponseEntity.ok().build();
//    }

}
