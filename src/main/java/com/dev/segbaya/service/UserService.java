package com.dev.segbaya.service;

import com.dev.segbaya.domain.Role;
import com.dev.segbaya.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    //    User saveUser(User user, MultipartFile filePhoto);
    User saveUser(
            String firstName ,
            String lastName ,
            String email ,
            String password ,
            MultipartFile filePhoto);

    Role saveRole(Role role);

    void addRoleToUser(String email , String roleName);

    User getUserById(Long id);

    User getUserByEmail(String email);

    List<User> getUsers();

    void updateUser(Long id , User user);

    void deleteUser(Long id);
}
