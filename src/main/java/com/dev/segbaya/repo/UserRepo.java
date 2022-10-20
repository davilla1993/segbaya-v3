package com.dev.segbaya.repo;

import com.dev.segbaya.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

//    @Query("DELETE FROM User u WHERE u.idUser=?1")
//    void deleteById(UUID idUser);
}