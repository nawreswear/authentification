package com.springjwt.repository;
import com.springjwt.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    boolean existsByType(String type);

   /* default boolean existsAdmin() {
        return existsByType("admin");
    }*/
}
