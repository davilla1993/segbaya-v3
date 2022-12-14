package com.dev.segbaya.service;

import com.dev.segbaya.domain.PublishHouse;
import com.dev.segbaya.filter.CustomUserDetails;
import com.dev.segbaya.repo.PublishHouseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private PublishHouseRepo publishHouseRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PublishHouse publishHouse = publishHouseRepo.findByEmail(username).orElseThrow(
                (() -> new IllegalStateException(
                        "Publish house with email "+ username + " does not exist")
                )
        );

        return new CustomUserDetails(publishHouse);
    }
}
