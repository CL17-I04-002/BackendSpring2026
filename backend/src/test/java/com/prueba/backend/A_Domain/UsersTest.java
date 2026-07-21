package com.prueba.backend.A_Domain;

import com.prueba.backend.A_Domain.security.GrantedPermission;
import com.prueba.backend.A_Domain.security.Operation;
import com.prueba.backend.A_Domain.security.Role;
import com.prueba.backend.A_Domain.security.Users;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

class UsersTest {
    @Test
    void shouldReturnNullAuthoritiesWhenRoleIsNull() {
        Users user = new Users();
        user.setRole(null);

        Assertions.assertNull(user.getAuthorities());
    }

    @Test
    void shouldReturnNullAuthoritiesWhenPermissionsAreNull() {
        Role role = new Role();
        role.setPermissions(null);

        Users user = new Users();
        user.setRole(role);

        Assertions.assertNull(user.getAuthorities());
    }

    @Test
    void shouldReturnAuthoritiesSuccessfully() {
        Operation operation = new Operation();
        operation.setName("READ_USERS");

        GrantedPermission permission = new GrantedPermission();
        permission.setOperation(operation);

        Role role = new Role();
        role.setName("ADMIN");
        role.setPermissions(List.of(permission));

        Users user = new Users();
        user.setRole(role);

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        Assertions.assertNotNull(authorities);
        Assertions.assertEquals(2, authorities.size());

        Assertions.assertTrue(
                authorities.stream()
                        .anyMatch(a -> a.getAuthority().equals("READ_USERS"))
        );

        Assertions.assertTrue(
                authorities.stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
        );
    }

    @Test
    void shouldReturnTrueForUserDetailsMethods() {
        Users user = new Users();

        Assertions.assertTrue(user.isAccountNonExpired());
        Assertions.assertTrue(user.isAccountNonLocked());
        Assertions.assertTrue(user.isCredentialsNonExpired());
        Assertions.assertTrue(user.isEnabled());
    }

    @Test
    void shouldCreateUserUsingParameterizedConstructor() {

        Role role = new Role();

        Users user = new Users(
                1L,
                "Daniel",
                "dlarin",
                "123456",
                "daniel@test.com",
                true,
                role
        );

        Assertions.assertEquals(1L, user.getId());
        Assertions.assertEquals("Daniel", user.getName());
        Assertions.assertEquals("dlarin", user.getUsername());
        Assertions.assertEquals("123456", user.getPassword());
        Assertions.assertEquals("daniel@test.com", user.getEmail());
        Assertions.assertTrue(user.getEnabled());
        Assertions.assertEquals(role, user.getRole());
    }

    @Test
    void shouldTestAllGettersAndSetters() {

        Role role = new Role();

        Users user = new Users();

        user.setId(10L);
        user.setName("John Doe");
        user.setUsername("johndoe");
        user.setPassword("password");
        user.setEmail("john@test.com");
        user.setEnabled(true);
        user.setRole(role);

        Assertions.assertEquals(10L, user.getId());
        Assertions.assertEquals("John Doe", user.getName());
        Assertions.assertEquals("johndoe", user.getUsername());
        Assertions.assertEquals("password", user.getPassword());
        Assertions.assertEquals("john@test.com", user.getEmail());
        Assertions.assertTrue(user.getEnabled());
        Assertions.assertEquals(role, user.getRole());
    }

    @Test
    void shouldCreateUserUsingNoArgsConstructor() {

        Users user = new Users();

        Assertions.assertNotNull(user);
        Assertions.assertNull(user.getId());
        Assertions.assertNull(user.getName());
        Assertions.assertNull(user.getUsername());
        Assertions.assertNull(user.getPassword());
        Assertions.assertNull(user.getEmail());
        Assertions.assertNull(user.getEnabled());
        Assertions.assertNull(user.getRole());
    }
}
