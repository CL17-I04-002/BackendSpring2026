package com.prueba.backend.D_Infraestructure.repository;

import com.prueba.backend.A_Domain.business.Reservation;
import com.prueba.backend.A_Domain.business.ReservationStatus;
import com.prueba.backend.A_Domain.security.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("""
    SELECT COUNT(r) > 0
    FROM Reservation r
    WHERE r.space.id = :spaceId
    AND r.status <> :status
    AND r.startDate < :endDate
    AND r.endDate > :startDate
    """)
    boolean existsOverlappingReservation(
            @Param("spaceId") Long spaceId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("status") ReservationStatus status);


    List<Reservation> findByUser(Users user);


}
