package com.bezkoder.springjwt.security.services;

import com.bezkoder.springjwt.models.Admin;
import com.bezkoder.springjwt.models.User;
import org.springframework.stereotype.Service;


public interface UserDetailsService {
    User save(User user);
    String getUserType(String email);
    void deleteUser(Long userId);
    void update(User user);
}
