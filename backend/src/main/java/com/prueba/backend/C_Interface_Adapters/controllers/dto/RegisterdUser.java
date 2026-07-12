package com.prueba.backend.C_Interface_Adapters.controllers.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Autor: Daniel Larín
 * This class sends basic information in each request, it doesn't necessary sends Users object
 */
@Getter
@Setter
public class RegisterdUser implements Serializable {
    private Long id;
    private String username;
    private String name;
    private String role;
    private String jwt;
}
