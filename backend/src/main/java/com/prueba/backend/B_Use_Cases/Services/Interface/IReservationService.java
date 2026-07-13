package com.prueba.backend.B_Use_Cases.Services.Interface;

import com.prueba.backend.B_Use_Cases.Services.dto.ReservationRequest;
import com.prueba.backend.B_Use_Cases.Services.dto.ReservationResponse;

import java.util.List;

public interface IReservationService {
    ReservationResponse create(ReservationRequest request);

    ReservationResponse findById(Long id);

    List<ReservationResponse> findAll();

    List<ReservationResponse> findMyReservations();

    ReservationResponse cancel(Long reservationId);
}
