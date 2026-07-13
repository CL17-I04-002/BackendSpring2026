package com.prueba.backend.B_Use_Cases.Services.Implementation;

import com.prueba.backend.A_Domain.business.Reservation;
import com.prueba.backend.B_Use_Cases.Exception.ObjectNotFoundException;
import com.prueba.backend.B_Use_Cases.Services.Interface.IReservationService;
import com.prueba.backend.B_Use_Cases.Services.dto.ReservationRequest;
import com.prueba.backend.B_Use_Cases.Services.dto.ReservationResponse;
import com.prueba.backend.B_Use_Cases.Services.mapper.ReservationMapper;
import com.prueba.backend.D_Infraestructure.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ReservationServiceImpl implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    @Override
    @Transactional
    public ReservationResponse create(ReservationRequest request) {
        Reservation reservation = reservationMapper.toEntity(request);

        reservation = reservationRepository.save(reservation);

        return reservationMapper.toResponse(reservation);
    }

    @Override
    public ReservationResponse findById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Reservation not found"));

        return reservationMapper.toResponse(reservation);
    }

    @Override
    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll()
                .stream()
                .map(reservationMapper::toResponse)
                .toList();
    }

    @Override
    public List<ReservationResponse> findMyReservations() {
        throw new UnsupportedOperationException("Pending implementation");
    }

    @Override
    @Transactional
    public ReservationResponse cancel(Long reservationId) {
        throw new UnsupportedOperationException("Pending implementation");
    }
}
