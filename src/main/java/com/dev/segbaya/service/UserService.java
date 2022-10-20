package com.dev.segbaya.service;

import com.dev.segbaya.domain.Role;
import com.dev.segbaya.domain.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String email, String roleName);
    User getUserById(Long id);
    User getUserByEmail(String email);
    List<User> getUsers();
    void updateUser(Long id, User user);
    void deleteUser(Long id);
}
