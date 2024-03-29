package com.springjwt.security.services;
import com.springjwt.models.User;
import com.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé avec l'adresse email : " + username);
        }
        return UserDetailsImpl.build(user.getType(), user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'ID : " + userId));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public void update(User updatedUser) {
        userRepository.findById(updatedUser.getId()).ifPresent(existingUser -> {
            // Update other fields
            existingUser.setType(updatedUser.getType());
            existingUser.setNom(updatedUser.getNom());
            existingUser.setPrenom(updatedUser.getPrenom());
            existingUser.setTel(updatedUser.getTel());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setCodePostal(updatedUser.getCodePostal());
            existingUser.setPays(updatedUser.getPays());
            existingUser.setVille(updatedUser.getVille());
            existingUser.setCin(updatedUser.getCin());
            existingUser.setLongitude(updatedUser.getLongitude());
            existingUser.setLatitude(updatedUser.getLatitude());

            // Update the photo only if a new photo is provided
            if (updatedUser.getPhoto() != null && updatedUser.getPhoto().length() > 0) {
                existingUser.setPhoto(updatedUser.getPhoto());
            }

            // Save the updated user
            userRepository.save(existingUser);
        });
    }
    public Long findUserIdByNom(String nom) {
        User user = userRepository.findByNom(nom);
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé avec le nom : " + nom);
        }
        return user.getId();
    }
    public boolean isEmailUnique(String email) {
        // Vérifiez si l'e-mail existe déjà dans la base de données
        User user = userRepository.findByEmail(email);

        return user == null; // Si l'utilisateur est null, l'e-mail est unique
    }
    public Long getUserIdByName(String nom) {
        User user = userRepository.findByNom(nom);
        return user.getId();
    }
    // @Transactional
    public User updateUserType(Long userId) {
        // Recherchez l'utilisateur par son ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        // Mettez à jour le type de l'utilisateur
        user.setType("vendeur");
        // Enregistrez les modifications dans la base de données
        return userRepository.save(user);
    }
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}