package com.dev.segbaya.service;

import com.dev.segbaya.domain.PublishHouse;
import com.dev.segbaya.repo.PublishHouseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private PublishHouseRepo publishHouseRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<PublishHouse> publishHouse = publishHouseRepo.findByEmail(username);
        return null;
    }
}
