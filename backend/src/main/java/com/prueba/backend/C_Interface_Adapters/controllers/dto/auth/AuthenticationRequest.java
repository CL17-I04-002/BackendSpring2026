package com.prueba.backend.C_Interface_Adapters.controllers.dto.auth;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Autor: Daniel Larin
 * It works to send username and pass and then validating them to return a JWT
 */
@Getter
@Setter
public class AuthenticationRequest implements Serializable {
    private String username;
    private String password;
}
