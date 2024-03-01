package com.bezkoder.springjwt.payload.request;

import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String nom;
    
    @NotBlank
    @Size(min = 3, max = 20)
    private String prenom;
    
    @NotNull(message = "Tel cannot be null")
    private Integer tel;
    
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;


    @NotBlank
    @Size(max = 20)
    private String type ;

    @Size(max = 20)
    private String marque ;

    @Size(max = 20)
    private String nomsociete ;
}
