package com.springjwt.controllers;

import com.springjwt.models.Admin;
//import com.springjwt.models.ConfirmationToken;
import com.springjwt.models.User;
import com.springjwt.models.Vendeur;
import com.springjwt.payload.request.LoginRequest;
import com.springjwt.payload.request.SignupRequest;
import com.springjwt.payload.response.JwtResponse;
import com.springjwt.payload.response.MessageResponse;
import com.springjwt.repository.AdminRepository;
//import com.springjwt.repository.ConfirmationTokenRepository;
import com.springjwt.repository.UserRepository;
import com.springjwt.repository.VendeurRepository;
import com.springjwt.security.jwt.JwtUtils;
import com.springjwt.security.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/")
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

    @Autowired
    private EmailService emailService;
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            // Votre logique d'authentification
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            // Réponse contenant le jeton JWT et les détails de l'utilisateur
            return ResponseEntity.ok(new JwtResponse(
                    jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    userDetails.getType()));
        } catch (AuthenticationException e) {
            // Gestion des erreurs d'authentification
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
        }
    }
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @PostMapping("/addUser")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User createdUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("getUserById/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }
    @GetMapping("/vendeurs")
    public ResponseEntity<?> getAllVendeurs() {
        List<Vendeur> vendeurs = vendeurService.getAll();
        return ResponseEntity.ok(vendeurs);
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Email is already in use!"));
            }

            User newUser;
            String type = signUpRequest.getType().toLowerCase();
            User user = null;
            if (type.startsWith("admin")) {
                Admin admin = new Admin();
                admin.setNom(signUpRequest.getNom());
                admin.setPrenom(signUpRequest.getPrenom());
                admin.setEmail(signUpRequest.getEmail());
                admin.setTel(signUpRequest.getTel());
                admin.setType(signUpRequest.getType());
                admin.setCin(signUpRequest.getCin());
                admin.getPhoto(signUpRequest.getPhoto());
                admin.getPhoto(signUpRequest.getPhoto());
                admin.setPassword(encoder.encode(signUpRequest.getPassword()));
                newUser = admin;
                adminService.save(admin);

            } else if (type.startsWith("vendeur")) {
                Vendeur vendeur = new Vendeur();
                vendeur.setNom(signUpRequest.getNom());
                vendeur.setPrenom(signUpRequest.getPrenom());
                vendeur.setEmail(signUpRequest.getEmail());
                vendeur.setTel(signUpRequest.getTel());
                vendeur.setType(signUpRequest.getType());
                vendeur.setCin(signUpRequest.getCin());
                vendeur.getPhoto(signUpRequest.getPhoto());
                vendeur.setPassword(encoder.encode(signUpRequest.getPassword()));

                newUser = vendeur;
                vendeurService.save(vendeur);
            } else {
                user = new User();
                user.setNom(signUpRequest.getNom());
                user.setPrenom(signUpRequest.getPrenom());
                user.setEmail(signUpRequest.getEmail());
                user.setTel(signUpRequest.getTel());
                user.setCin(signUpRequest.getCin());
                user.setType("user");
                user.setPassword(encoder.encode(signUpRequest.getPassword()));
                user.getPhoto(signUpRequest.getPhoto());
                newUser = user;
                userService.save(user);
            }
            //return ResponseEntity.ok().build();
// Générez le token JWT pour cet utilisateur nouvellement enregistré
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signUpRequest.getEmail(), signUpRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
// Créez une réponse contenant le token JWT et retournez-la
// Utilisez les informations appropriées pour construire JwtResponse
            return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), userDetails.getType()));
        } catch (DataIntegrityViolationException e) {
            // Gérer les erreurs de violation d'intégrité des données (par exemple, e-mail déjà utilisé)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Error: Data integrity violation."));
        }
    }
    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
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
            admin.setCin(signUpRequest.getCin());
            admin.setLongitude(signUpRequest.getLongitude());
            admin.setLatitude(signUpRequest.getLatitude());
            admin.setVille(signUpRequest.getVille());
            admin.setPays(signUpRequest.getPays());
            admin.setCodePostal(signUpRequest.getCodePostal());
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
            vendeur.setCin(signUpRequest.getCin());
            vendeur.setLongitude(signUpRequest.getLongitude());
            vendeur.setLatitude(signUpRequest.getLatitude());
            vendeur.setVille(signUpRequest.getVille());
            vendeur.setPays(signUpRequest.getPays());
            vendeur.setCodePostal(signUpRequest.getCodePostal());
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
            user.setCin(signUpRequest.getCin());
            user.setLongitude(signUpRequest.getLongitude());
            user.setLatitude(signUpRequest.getLatitude());
            user.setVille(signUpRequest.getVille());
            user.setPays(signUpRequest.getPays());
            user.setCodePostal(signUpRequest.getCodePostal());
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
    //  @Autowired
    // private ConfirmationTokenRepository confirmationTokenRepository;

   /* @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            User user = userRepository.findByEmailIgnoreCase(token.getUserEntity().getEmail());
            user.setEnabled(true);
            userRepository.save(user);
            modelAndView.setViewName("accountVerified");
        }
        else
        {
            modelAndView.addObject("message","The link is invalid or broken!");
            modelAndView.setViewName("error");
        }

        return modelAndView;
    }*/
   /* private void sendActivationEmail(String email) throws MessagingException, UnsupportedEncodingException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Activation de compte");
        mailMessage.setFrom("nawreselou2382@gmail.com");
        mailMessage.setText("Veuillez cliquer sur le lien suivant pour activer votre compte : http://localhost:3003/confirm-account?token=token_value");
  emailService.sendVerificationEmail(signUpRequest.getEmail(), "743185");

        return ResponseEntity.ok().build();(mailMessage);
    }*/
}