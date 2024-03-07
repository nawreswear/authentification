package com.springjwt.security.services;

import com.springjwt.models.User;
import com.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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
            userRepository.save(existingUser);
        });
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}