package com.bezkoder.springjwt.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.springjwt.models.Vendeur;
import org.springframework.stereotype.Repository;

@Repository
public interface VendeurRepository extends JpaRepository<Vendeur, Long> {


}
