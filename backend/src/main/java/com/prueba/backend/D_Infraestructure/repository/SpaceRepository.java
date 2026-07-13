package com.prueba.backend.D_Infraestructure.repository;

import com.prueba.backend.A_Domain.business.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long>  {
}
