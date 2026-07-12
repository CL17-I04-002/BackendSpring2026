package com.prueba.backend.C_Interface_Adapters.controllers.dto.auth;

import lombok.Getter;
import lombok.Setter;


/**
 * @Autor: Daniel Larin
 * Returns JWT
 */
@Getter
@Setter
public class AuthenticationResponse {
    private String jwt;
}
