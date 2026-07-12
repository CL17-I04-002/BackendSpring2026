package com.prueba.backend.C_Interface_Adapters.controllers;

import com.prueba.backend.B_Use_Cases.Interfaces.AuthenticationService;
import com.prueba.backend.C_Interface_Adapters.controllers.dto.auth.AuthenticationRequest;
import com.prueba.backend.C_Interface_Adapters.controllers.dto.auth.AuthenticationResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    /**
     * Signs in a user and returning a jwt
     * @param authenticationRequest
     * @return ResponseEntity<AuthenticationResponse>
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest authenticationRequest){
        AuthenticationResponse rsp = authenticationService.login(authenticationRequest);
        return ResponseEntity.ok(rsp);
    }
}
