package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Commantaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CommantaireRepository extends JpaRepository<Commantaire, Long> {



    List<Commantaire> findByTexteContainingIgnoreCase(String keywords);


   // List<Commantaire> findByUserUsername(@Param("username") String username);
    List<Commantaire> findByDateBetween(Date dateDebut, Date dateFin);

    //List<Commantaire> findByVendeurNom(String nomVendeur);


}
