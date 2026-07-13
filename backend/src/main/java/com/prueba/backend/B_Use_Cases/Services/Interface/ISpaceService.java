package com.prueba.backend.B_Use_Cases.Services.Interface;

import com.prueba.backend.A_Domain.business.Space;

import java.util.List;
import java.util.Optional;

public interface ISpaceService {
    Space saveSpace(Space space);
    List<Space> findAllSpaces();
    Optional<Space> findSpaceById(Long id);
    Space updateSpace(Space space, Long id);
    void deleteSpace(Long id);
}
