package com.prueba.backend.C_Interface_Adapters;

import com.prueba.backend.B_Use_Cases.Interfaces.AuthenticationService;
import com.prueba.backend.C_Interface_Adapters.controllers.UserController;
import com.prueba.backend.C_Interface_Adapters.controllers.dto.RegisterdUser;
import com.prueba.backend.C_Interface_Adapters.controllers.dto.SaveUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

class UserControllerTest {
    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private UserController controller;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldRegisterUser() {

        SaveUser saveUser = new SaveUser();

        RegisterdUser registeredUser = mock(RegisterdUser.class);

        when(authenticationService.registerOneCustomer(saveUser))
                .thenReturn(registeredUser);

        ResponseEntity<RegisterdUser> response =
                controller.registerOne(saveUser);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(registeredUser, response.getBody());

        verify(authenticationService).registerOneCustomer(saveUser);
        verifyNoMoreInteractions(authenticationService);
    }
}
