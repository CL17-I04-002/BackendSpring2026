package com.prueba.backend.C_Interface_Adapters.controllers;

import com.prueba.backend.B_Use_Cases.Interfaces.AuthenticationService;
import com.prueba.backend.C_Interface_Adapters.controllers.dto.RegisterdUser;
import com.prueba.backend.C_Interface_Adapters.controllers.dto.SaveUser;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final AuthenticationService authenticationService;

    /**
     * This endpoint will register one user
     * @param newUser
     * @return
     */

    @PostMapping
    public ResponseEntity<RegisterdUser> registerOne(@RequestBody @Valid SaveUser newUser){
        RegisterdUser registerdUser = authenticationService.registerOneCustomer(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerdUser);
    }
}
