package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.Admin;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.models.Vendeur;
import com.bezkoder.springjwt.payload.request.LoginRequest;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.payload.response.JwtResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.AdminRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.repository.VendeurRepository;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import com.bezkoder.springjwt.security.services.AdminService;
import com.bezkoder.springjwt.security.services.UserDetailsImpl;
import com.bezkoder.springjwt.security.services.UserDetailsServiceImpl;
import com.bezkoder.springjwt.security.services.VendeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    VendeurRepository vendeurRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private AdminService adminService;

    @Autowired
    private VendeurService vendeurService;

    @Autowired
    private UserDetailsServiceImpl userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getType())
        );
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User newUser;
        if (signUpRequest.getType().toLowerCase().startsWith("admin")) {
            Admin admin = new Admin();
            admin.setNom(signUpRequest.getNom());
            admin.setPrenom(signUpRequest.getPrenom());
            admin.setEmail(signUpRequest.getEmail());
            admin.setTel(signUpRequest.getTel());
            admin.setType(signUpRequest.getType());
            admin.setPassword(encoder.encode(signUpRequest.getPassword()));
            newUser = admin;
            adminService.save(admin);
        } else if (signUpRequest.getType().toLowerCase().startsWith("vendeur")) {
            Vendeur vendeur = new Vendeur();
            vendeur.setNom(signUpRequest.getNom());
            vendeur.setPrenom(signUpRequest.getPrenom());
            vendeur.setEmail(signUpRequest.getEmail());
            vendeur.setTel(signUpRequest.getTel());
            vendeur.setType(signUpRequest.getType());
            vendeur.setPassword(encoder.encode(signUpRequest.getPassword()));
            newUser = vendeur;
            vendeurService.save(vendeur);
        } else {
            User user = new User();
            user.setNom(signUpRequest.getNom());
            user.setPrenom(signUpRequest.getPrenom());
            user.setEmail(signUpRequest.getEmail());
            user.setTel(signUpRequest.getTel());
            user.setType(("user"));
            user.setPassword(encoder.encode(signUpRequest.getPassword()));
            newUser = user;
            userService.save(user);
        }
        return ResponseEntity.ok(new MessageResponse("Utilisateur enregistré avec succès !"));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @Valid @RequestBody SignupRequest signUpRequest) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User not found with id " + userId));
        }

        User updatedUser;
        if (signUpRequest.getType().toLowerCase().startsWith("admin")) {
            Admin admin = new Admin();
            admin.setId(userId);
            admin.setNom(signUpRequest.getNom());
            admin.setPrenom(signUpRequest.getPrenom());
            admin.setEmail(signUpRequest.getEmail());
            admin.setTel(signUpRequest.getTel());
            admin.setType(signUpRequest.getType());
            admin.setPassword(encoder.encode(signUpRequest.getPassword()));
            updatedUser = admin;
            adminService.updateAdmin(admin.getId(),admin);
        } else if (signUpRequest.getType().toLowerCase().startsWith("vendeur")) {
            Vendeur vendeur = new Vendeur();
            vendeur.setId(userId);
            vendeur.setNom(signUpRequest.getNom());
            vendeur.setPrenom(signUpRequest.getPrenom());
            vendeur.setEmail(signUpRequest.getEmail());
            vendeur.setTel(signUpRequest.getTel());
            vendeur.setType(signUpRequest.getType());
            vendeur.setPassword(encoder.encode(signUpRequest.getPassword()));
            updatedUser = vendeur;
            vendeurService.update(vendeur);
        } else {
            User user = new User();
            user.setId(userId);
            user.setNom(signUpRequest.getNom());
            user.setPrenom(signUpRequest.getPrenom());
            user.setEmail(signUpRequest.getEmail());
            user.setTel(signUpRequest.getTel());
            user.setType(("user"));
            user.setPassword(encoder.encode(signUpRequest.getPassword()));
            updatedUser = user;
            userService.update(user);
        }
        return ResponseEntity.ok(new MessageResponse("Utilisateur mis à jour avec succès !"));
    }
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User not found with id " + userId));
        }

        userService.deleteUser(userId);
        return ResponseEntity.ok(new MessageResponse("User deleted successfully."));
    }
    @DeleteMapping("/deleteAdmin/{adminId}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Long adminId) {
        if (!adminRepository.existsById(adminId)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Admin not found with id " + adminId));
        }

        adminService.deleteAdmin(adminId);
        return ResponseEntity.ok(new MessageResponse("Admin deleted successfully."));
    }

    @DeleteMapping("/deleteVendeur/{vendeurId}")
    public ResponseEntity<?> deleteVendeur(@PathVariable Long vendeurId) {
        if (!vendeurRepository.existsById(vendeurId)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Vendeur not found with id " + vendeurId));
        }

        vendeurService.deletevendeur(vendeurId);
        return ResponseEntity.ok(new MessageResponse("Vendeur deleted successfully."));
    }
    @GetMapping("/vendeurs")
    public ResponseEntity<?> getAllVendeurs() {
        List<Vendeur> vendeurs = vendeurService.getAll();
        return ResponseEntity.ok(vendeurs);
    }

    @GetMapping("/vendeurs/{vendeurId}")
    public ResponseEntity<?> getVendeurById(@PathVariable Long vendeurId) {
        Vendeur vendeur = vendeurService.getById(vendeurId);
        if (vendeur == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Vendeur not found with id " + vendeurId));
        }
        return ResponseEntity.ok(vendeur);
    }
    @GetMapping("/admins")
    public ResponseEntity<?> getAllAdmins() {
        List<Admin> admins = adminService.getAll();
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/admins/{adminId}")
    public ResponseEntity<?> getAdminById(@PathVariable Long adminId) {
        Admin admin = adminService.getById(adminId);
        if (admin == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Admin not found with id " + adminId));
        }
        return ResponseEntity.ok(admin);
    }
}
