package com.springjwt.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
public class Admin extends User {
    @Size(max = 20)
    private String type ;

}
