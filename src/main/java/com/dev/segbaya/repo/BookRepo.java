package com.dev.segbaya.repo;

import com.dev.segbaya.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book, Long> {

}
