package com.bezkoder.springjwt.security.services;

import com.bezkoder.springjwt.repository.CommantaireRepository;

import com.bezkoder.springjwt.models.Commantaire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommantaireServiceImpl implements CommantaireService {

    @Autowired
    private CommantaireRepository temoignageRepository;

    @Override
    public List<Commantaire> findAll() {
        return temoignageRepository.findAll();
    }

    @Override
    public Commantaire findById(Long id) {
        return temoignageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Témoignage introuvable"));
    }

    @Override
    public Commantaire save(Commantaire temoignage) {
        return temoignageRepository.save(temoignage);
    }

    @Override
    public void delete(Long id) {
        temoignageRepository.deleteById(id);
    }

   /* @Override
    public List<Commantaire> findByNomUtilisateur(String nomUtilisateur) {
        if(temoignageRepository.findByUserUsername(nomUtilisateur)!=null)
            return temoignageRepository.findByUserUsername(nomUtilisateur);
            else
            return null;
    }*/

    @Override
    public List<Commantaire> findByTexteContaining(String keywords) {
        return temoignageRepository.findByTexteContainingIgnoreCase(keywords);
    }

    @Override
    public Commantaire updateCommantaire(Commantaire temoignage, Long id) {
        Commantaire comExist = temoignageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Témoignage introuvable"));

        comExist.setTexte(temoignage.getTexte());
        comExist.setDate(temoignage.getDate());
        comExist.setUser(temoignage.getUser());
        return temoignageRepository.save(comExist);
    }

}
