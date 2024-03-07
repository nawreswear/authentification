package com.springjwt.security.services;

import com.springjwt.models.User;

import java.util.List;
import java.util.Optional;


public interface UserDetailsService {
    User save(User user);
    String getUserType(String email);
    List<User> getAllUsers();
    void deleteUser(Long userId);
    void update(User user);
    User getUserById (User userId);
   User getUserByEmail(String email);
    // boolean verifyEmail(String email, String code);
    // void markEmailAsVerified(String email);
    // void sendVerificationEmail(User user, String siteURL);
}
