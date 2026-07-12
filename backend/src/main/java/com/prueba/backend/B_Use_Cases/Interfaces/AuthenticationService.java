package com.prueba.backend.B_Use_Cases.Interfaces;

import com.prueba.backend.A_Domain.security.Users;
import com.prueba.backend.C_Interface_Adapters.controllers.dto.RegisterdUser;
import com.prueba.backend.C_Interface_Adapters.controllers.dto.SaveUser;
import com.prueba.backend.C_Interface_Adapters.controllers.dto.auth.AuthenticationRequest;
import com.prueba.backend.C_Interface_Adapters.controllers.dto.auth.AuthenticationResponse;
import jakarta.validation.Valid;

public interface AuthenticationService {
    RegisterdUser registerOneCustomer(SaveUser newUser);

    AuthenticationResponse login(@Valid AuthenticationRequest authenticationRequest);

    boolean validateToken(String jwt);
    Users findLoggedInUser();
}
