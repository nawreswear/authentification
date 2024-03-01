package com.bezkoder.springjwt.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.springjwt.models.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    // Vérifie s'il existe déjà un utilisateur avec le type spécifié
    boolean existsByType(String type);

    // Vérifie s'il existe déjà un administrateur dans la base de données
    default boolean existsAdmin() {
        return existsByType("admin");
    }
}
