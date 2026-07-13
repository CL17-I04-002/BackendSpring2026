package com.prueba.backend.B_Use_Cases.Services.mapper;

import com.prueba.backend.A_Domain.business.Reservation;
import com.prueba.backend.B_Use_Cases.Services.dto.ReservationRequest;
import com.prueba.backend.B_Use_Cases.Services.dto.ReservationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    Reservation toEntity(ReservationRequest request);

    @Mapping(source = "user.username", target = "customer")
    @Mapping(source = "space.name", target = "space")
    ReservationResponse toResponse(Reservation reservation);
}
