package com.bezkoder.springjwt.security.services;

import com.bezkoder.springjwt.models.Admin;
import com.bezkoder.springjwt.models.Vendeur;

import java.util.List;


public interface VendeurService {

	Vendeur save(Vendeur v);
	 List<Vendeur> getAll();

	Vendeur update(Vendeur updatedVendeur);

	void deletevendeur(Long id);
	Vendeur getById(Long id);
}
