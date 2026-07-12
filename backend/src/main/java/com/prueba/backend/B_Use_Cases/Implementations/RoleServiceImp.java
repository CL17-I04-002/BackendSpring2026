package com.prueba.backend.B_Use_Cases.Implementations;

import com.prueba.backend.A_Domain.security.Role;
import com.prueba.backend.B_Use_Cases.Interfaces.RoleService;
import com.prueba.backend.D_Infraestructure.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImp implements RoleService {
    private final String defaultRole;
    private final RoleRepository roleRepository;

    public RoleServiceImp(@Value("${security.default.role}") String defaultRole,
                          RoleRepository roleRepository) {
        this.defaultRole = defaultRole;
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> findDefaultRole() {
        return roleRepository.findByName(defaultRole);
    }
}
