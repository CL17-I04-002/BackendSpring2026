package com.prueba.backend.C_Interface_Adapters.controllers.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SaveUser implements Serializable {
    @Size(min = 6)
    private String name;
    @Size(min = 6)
    private String username;
    @Size(min = 8)
    private String password;
    @Size(min = 8)
    private String repeatedPassword;
}
