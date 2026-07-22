package com.prueba.backend.D_Infraestructure;

import com.prueba.backend.A_Domain.security.Users;
import com.prueba.backend.B_Use_Cases.Exception.ObjectNotFoundException;
import com.prueba.backend.B_Use_Cases.Interfaces.JwtService;
import com.prueba.backend.B_Use_Cases.Interfaces.UserService;
import com.prueba.backend.D_Infraestructure.security.filter.JwtAuthenticationFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

class JwtAuthenticationFilterTest {
    @Mock
    private JwtService jwtService;

    @Mock
    private UserService userService;

    @Mock
    private FilterChain filterChain;

    private JwtAuthenticationFilter filter;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        filter = new JwtAuthenticationFilter(jwtService, userService, "User not found");
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() throws Exception {
        SecurityContextHolder.clearContext();
        closeable.close();
    }

    @Test
    void shouldContinueFilterWhenAuthorizationHeaderIsNull() throws ServletException, IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilter(request, response, filterChain);

        Mockito.verify(filterChain).doFilter(request, response);
        Mockito.verifyNoInteractions(jwtService);
        Mockito.verifyNoInteractions(userService);

        Assertions.assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void shouldContinueFilterWhenAuthorizationHeaderIsNotBearer() throws ServletException, IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Basic abc123");

        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilter(request, response, filterChain);

        Mockito.verify(filterChain).doFilter(request, response);
        Mockito.verifyNoInteractions(jwtService);
        Mockito.verifyNoInteractions(userService);

        Assertions.assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void shouldAuthenticateUserWhenTokenIsValid() throws ServletException, IOException {

        String token = "jwt-token";
        String username = "daniel";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token);

        MockHttpServletResponse response = new MockHttpServletResponse();

        Users user = Mockito.mock(Users.class);

        Mockito.when(jwtService.extractUsername(token)).thenReturn(username);
        Mockito.when(userService.findOneByUsername(username)).thenReturn(Optional.of(user));
        Mockito.when(user.getAuthorities()).thenReturn(Collections.emptyList());

        filter.doFilter(request, response, filterChain);

        Assertions.assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        Assertions.assertEquals(username,
                SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        Mockito.verify(jwtService).extractUsername(token);
        Mockito.verify(userService).findOneByUsername(username);
        Mockito.verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() throws ServletException, IOException {

        String token = "jwt-token";
        String username = "daniel";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token);

        MockHttpServletResponse response = new MockHttpServletResponse();

        Mockito.when(jwtService.extractUsername(token)).thenReturn(username);
        Mockito.when(userService.findOneByUsername(username)).thenReturn(Optional.empty());

        ObjectNotFoundException exception = Assertions.assertThrows(
                ObjectNotFoundException.class,
                () -> filter.doFilter(request, response, filterChain)
        );

        Assertions.assertEquals("User not found: daniel", exception.getMessage());

        Mockito.verify(jwtService).extractUsername(token);
        Mockito.verify(userService).findOneByUsername(username);
        Mockito.verify(filterChain, Mockito.never()).doFilter(Mockito.any(), Mockito.any());
    }
}
