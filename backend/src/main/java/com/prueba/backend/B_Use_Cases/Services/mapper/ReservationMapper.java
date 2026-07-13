package com.prueba.backend.B_Use_Cases.Services.mapper;

import com.prueba.backend.A_Domain.business.Reservation;
import com.prueba.backend.B_Use_Cases.Services.dto.ReservationRequest;
import com.prueba.backend.B_Use_Cases.Services.dto.ReservationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    Reservation toEntity(ReservationRequest request);

    ReservationResponse toResponse(Reservation reservation);
}
