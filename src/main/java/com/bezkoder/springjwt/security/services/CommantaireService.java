package com.bezkoder.springjwt.security.services;

import com.bezkoder.springjwt.models.Commantaire;

import java.util.List;



public interface CommantaireService {
    List<Commantaire> findAll();

    Commantaire findById(Long id);

    Commantaire save(Commantaire temoignage);

    void delete(Long id);


    // Recherche par utilisateur
    //List<Commantaire> findByNomUtilisateur(String nomUtilisateur);

    //List<Commantaire> findByNomVendeur(String nomVendeur);

    // Recherche par mots clés dans le texte
    List<Commantaire> findByTexteContaining(String keywords);


    Commantaire updateCommantaire(Commantaire temoignage, Long idTemoignage);
}
