package com.prueba.backend.A_Domain.business;

import com.prueba.backend.A_Domain.security.Users;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id", nullable = false)
    private Space space;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Integer totalHours;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}