package com.dev.segbaya.repo;

import com.dev.segbaya.domain.Book;
import com.dev.segbaya.domain.PublishHouse;
import com.dev.segbaya.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepo extends JpaRepository<Book, Long> {

    Optional<List<Book>> findByPublishHouse(PublishHouse publishHouse);

//    @Query("SELECT b FROM book b WHERE b.publishHouse = ?1 AND book = ?2")
//    Optional<Book> findByPublishHouseAndBook(PublishHouse publishHouse, Book book);

    Optional<List<Book>> findByUser(User user);

}
