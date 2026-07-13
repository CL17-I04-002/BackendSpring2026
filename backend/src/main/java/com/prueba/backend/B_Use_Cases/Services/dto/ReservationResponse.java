package com.prueba.backend.B_Use_Cases.Services.dto;

import com.prueba.backend.A_Domain.business.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationResponse {

    private Long id;

    private String customer;

    private String space;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Integer totalHours;

    private BigDecimal totalAmount;

    private ReservationStatus status;

}
