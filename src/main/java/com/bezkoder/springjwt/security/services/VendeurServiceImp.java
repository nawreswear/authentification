package com.bezkoder.springjwt.security.services;

import com.bezkoder.springjwt.models.Vendeur;
import com.bezkoder.springjwt.repository.VendeurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class VendeurServiceImp implements VendeurService {
	@Autowired
	private VendeurRepository vr;

	@Override
	public Vendeur save(Vendeur v) {
		if (vr != null) {
			return vr.save(v);
		}
		return null;
	}
	 @Override
	    public Vendeur update(Vendeur updatedVendeur) {
	        if (vr != null) {
	            Vendeur existingVendeur = vr.findById(updatedVendeur.getId())
	                                                       .orElseThrow(() -> new IllegalArgumentException("Vendeur not found with id: " + updatedVendeur.getId()));
	            existingVendeur.setType(updatedVendeur.getType());
	            existingVendeur.setNom(updatedVendeur.getNom());
	            existingVendeur.setPrenom(updatedVendeur.getPrenom());
	            existingVendeur.setTel(updatedVendeur.getTel());
	            existingVendeur.setEmail(updatedVendeur.getEmail());
	            existingVendeur.setPassword(updatedVendeur.getPassword());
	            existingVendeur.setCodePostal(updatedVendeur.getCodePostal());
	            existingVendeur.setPays(updatedVendeur.getPays());
	            existingVendeur.setVille(updatedVendeur.getVille());
	            existingVendeur.setCin(updatedVendeur.getCin());
	            existingVendeur.setLongitude(updatedVendeur.getLongitude());
	            existingVendeur.setLatitude(updatedVendeur.getLatitude());
	            return vr.save(existingVendeur);
	        }
	        return null;
	    }

	@Override
	public void deletevendeur(Long id) {
		if (vr != null) {
			vr.deleteById(id);
		}
	}
	@Override
	public List<Vendeur> getAll() {
		return vr.findAll();
	}
	@Override
	public Vendeur getById(Long id) {
		Optional<Vendeur> vendeurOptional = vr.findById(id);
		return vendeurOptional.orElse(null);
	}

}
