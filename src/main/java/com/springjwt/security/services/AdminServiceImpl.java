package com.springjwt.security.services;

import com.springjwt.models.Admin;
import com.springjwt.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin save(Admin a) {
        return adminRepository.save(a);
    }

    @Override
    public Admin updateAdmin(Long userId, Admin updatedAdmin) {
        Admin existingAdmin = adminRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found with id: " + userId));
        existingAdmin.setType(updatedAdmin.getType());
        existingAdmin.setNom(updatedAdmin.getNom());
        existingAdmin.setPrenom(updatedAdmin.getPrenom());
        existingAdmin.setTel(updatedAdmin.getTel());
        existingAdmin.setEmail(updatedAdmin.getEmail());
        existingAdmin.setPassword(updatedAdmin.getPassword());
        existingAdmin.setCodePostal(updatedAdmin.getCodePostal());
        existingAdmin.setPays(updatedAdmin.getPays());
        existingAdmin.setVille(updatedAdmin.getVille());
        existingAdmin.setCin(updatedAdmin.getCin());
        existingAdmin.setLongitude(updatedAdmin.getLongitude());
        existingAdmin.setLatitude(updatedAdmin.getLatitude());
        return adminRepository.save(existingAdmin);
    }
    @Override
    public void deleteAdmin(Long userId) {
        adminRepository.deleteById(userId);
    }
    @Override
    public List<Admin> getAll() {
        return adminRepository.findAll();
    }

    @Override
    public Admin getById(Long adminId) {
        Optional<Admin> adminOptional = adminRepository.findById(adminId);
        return adminOptional.orElse(null);
    }

}
