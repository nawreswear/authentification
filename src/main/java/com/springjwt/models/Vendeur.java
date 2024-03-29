package com.springjwt.models;

import javax.persistence.*;
import javax.validation.constraints.Size;

import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vendeur extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
