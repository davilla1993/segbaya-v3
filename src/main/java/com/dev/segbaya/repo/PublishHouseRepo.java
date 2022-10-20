package com.dev.segbaya.repo;

import com.dev.segbaya.domain.PublishHouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublishHouseRepo extends JpaRepository<PublishHouse, Long> {
    Optional<PublishHouse> findByEmail(String Email);
}
