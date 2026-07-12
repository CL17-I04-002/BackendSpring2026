package com.prueba.backend.B_Use_Cases.Interfaces;

import com.prueba.backend.A_Domain.security.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findDefaultRole();
}
