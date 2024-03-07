package com.springjwt.security.services;

import com.springjwt.models.Admin;

import java.util.List;

public interface AdminService {
    Admin save(Admin a);
    void deleteAdmin(Long userId);
    Admin updateAdmin(Long userId, Admin updatedAdmin);
    List<Admin> getAll();
    Admin getById(Long adminId);
}