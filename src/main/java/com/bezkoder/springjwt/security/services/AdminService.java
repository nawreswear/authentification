package com.bezkoder.springjwt.security.services;

import com.bezkoder.springjwt.models.Admin;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.models.Vendeur;

import java.util.List;

public interface AdminService {
    Admin save(Admin a);
    void deleteAdmin(Long userId);
    Admin updateAdmin(Long userId, Admin updatedAdmin);
    List<Admin> getAll();
    Admin getById(Long adminId);
}