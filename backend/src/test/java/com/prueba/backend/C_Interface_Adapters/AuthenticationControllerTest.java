package com.prueba.backend.C_Interface_Adapters;

import com.prueba.backend.B_Use_Cases.Interfaces.AuthenticationService;
import com.prueba.backend.C_Interface_Adapters.controllers.AuthenticationController;
import com.prueba.backend.C_Interface_Adapters.controllers.dto.auth.AuthenticationRequest;
import com.prueba.backend.C_Interface_Adapters.controllers.dto.auth.AuthenticationResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class AuthenticationControllerTest {
    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController controller;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAuthenticateUser() {

        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("daniel");
        request.setPassword("123456");

        AuthenticationResponse response = new AuthenticationResponse();

        Mockito.when(authenticationService.login(request)).thenReturn(response);

        ResponseEntity<AuthenticationResponse> result =
                controller.authenticate(request);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(response, result.getBody());

        Mockito.verify(authenticationService).login(request);
        Mockito.verifyNoMoreInteractions(authenticationService);
    }
}
