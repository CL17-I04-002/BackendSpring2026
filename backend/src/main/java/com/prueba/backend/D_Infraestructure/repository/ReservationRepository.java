package com.prueba.backend.D_Infraestructure.repository;

import com.prueba.backend.A_Domain.business.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
