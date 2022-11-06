package com.dev.segbaya.service.implementation;

import com.dev.segbaya.domain.Role;
import com.dev.segbaya.domain.User;
import com.dev.segbaya.repo.RoleRepo;
import com.dev.segbaya.repo.UserRepo;
import com.dev.segbaya.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email).orElseThrow(
                (() -> new IllegalStateException(
                        "User with email "+ email + " does not exist")
                ));
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }else {
            log.info("User found in the database: {}", email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(
                (() -> new IllegalStateException(
                        "User with id "+ id + " does not exist")
                ));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(
                (() -> new IllegalStateException(
                        "User with id "+ email + " does not exist")
                ));
    }

    @Override
    public List<User> getUsers() {

        return userRepo.findAll();
    }


    @Override
    public void saveUser(User user) {
        Optional<User> userOptional = userRepo.
                findByEmail(user.getEmail());

        if (userOptional.isPresent()){
            throw new IllegalStateException("email "+ user.getEmail() + " taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        addRoleToUser(user.getEmail(), "ROLE_USER");
    }

    @Transactional
    @Override
    public void updateUser(Long id, User user) {
        User userOptional = userRepo.findById(id).orElseThrow(
                (() -> new IllegalStateException(
                        "User with id "+ id + " does not exist")
                ));
        if (user.getFirstName() != null &&
                user.getFirstName().length() > 0 ) {
            userOptional.setFirstName(user.getFirstName());
        }

        if (user.getLastName() != null &&
                user.getLastName().length() > 0) {
            userOptional.setLastName(user.getLastName());
        }

        if (user.getPassword() != null &&
                user.getPassword().length() > 0 ) {
            userOptional.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        if (userOptional.getImage() != null &&
                userOptional.getImage().length() > 0 ) {
            userOptional.setImage(user.getImage());
        }

    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepo.findById(id).orElseThrow(
                (() -> new IllegalStateException(
                        "User with id "+ id + " does not exist")
                ));
        userRepo.deleteById(user.getIdUser());
    }

    @Override
    public Role saveRole(Role role) {
        Optional<Role> roleOptional = roleRepo.findByName(role.getName());

        if (roleOptional.isPresent()) {
            throw new IllegalStateException("Role "+ role.getName() + " already add");
        }
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        User user = userRepo.findByEmail(email).orElseThrow(
                (() -> new IllegalStateException(
                        "User with email "+ email + " does not exist")
                ));
        Role role = roleRepo.findByName(roleName).orElseThrow(
                (() -> new IllegalStateException(
                        "Role "+ roleName + " does not exist")
                ));

        if (!user.getRoles().contains(role)) {
            user.getRoles().add(role);
        }

    }

}