package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.security.services.CommantaireService;
import com.bezkoder.springjwt.models.Commantaire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commentaire")
public class CommantaireController {

    @Autowired
    private CommantaireService temoignageService;

    @GetMapping
    public ResponseEntity<List<Commantaire>> getAllCommantaires() {
        List<Commantaire> temoignages = temoignageService.findAll();
        return ResponseEntity.ok(temoignages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commantaire> getCommantaireById(@PathVariable Long id) {
        Commantaire temoignage = temoignageService.findById(id);
        return ResponseEntity.ok(temoignage);
    }

    @PostMapping
    public ResponseEntity<Commantaire> createCommantaire(@RequestBody Commantaire temoignage) {
        Commantaire createdTemoignage = temoignageService.save(temoignage);
        return new ResponseEntity<>(createdTemoignage, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommantaire(@PathVariable Long id) {
        temoignageService.delete(id);
        return ResponseEntity.noContent().build();
    }

   /* @GetMapping("/utilisateur/{nomutulisateur}")
    public ResponseEntity<List<Commantaire>> getCommantairesByUtilisateur(@PathVariable String nomutulisateur) {
        List<Commantaire> temoignages = temoignageService.findByNomUtilisateur(nomutulisateur);
        return ResponseEntity.ok(temoignages);
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<Commantaire> updateCommantaire(@RequestBody Commantaire temoignage, @PathVariable Long id) {
        Commantaire updatedTemoignage = temoignageService.updateCommantaire(temoignage, id);
        return ResponseEntity.ok(updatedTemoignage);
    }
    @GetMapping("/keywords/{keywords}")
    public ResponseEntity<List<Commantaire>> getCommantairesByKeywords(@PathVariable String keywords) {
        List<Commantaire> temoignages = temoignageService.findByTexteContaining(keywords);
        return ResponseEntity.ok(temoignages);
    }



}
