package com.prueba.backend.B_Use_Cases.Services.Implementation;

import com.prueba.backend.A_Domain.business.Reservation;
import com.prueba.backend.A_Domain.business.ReservationStatus;
import com.prueba.backend.A_Domain.business.Space;
import com.prueba.backend.A_Domain.security.Users;
import com.prueba.backend.B_Use_Cases.Exception.ObjectNotFoundException;
import com.prueba.backend.B_Use_Cases.Exception.OverlappingReservationException;
import com.prueba.backend.B_Use_Cases.Services.Interface.IReservationService;
import com.prueba.backend.B_Use_Cases.Services.dto.ReservationRequest;
import com.prueba.backend.B_Use_Cases.Services.dto.ReservationResponse;
import com.prueba.backend.B_Use_Cases.Services.mapper.ReservationMapper;
import com.prueba.backend.D_Infraestructure.repository.ReservationRepository;
import com.prueba.backend.D_Infraestructure.repository.SpaceRepository;
import com.prueba.backend.D_Infraestructure.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ReservationServiceImpl implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final SpaceRepository spaceRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ReservationResponse create(ReservationRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("User was not found"));

        Space space = spaceRepository.findById(request.getSpaceId())
                .orElseThrow(() -> new ObjectNotFoundException("Space not found"));

        if (request.getStartDate().isAfter(request.getEndDate())
                || request.getStartDate().isEqual(request.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        Reservation reservation = reservationMapper.toEntity(request);

        reservation.setUser(user);
        reservation.setSpace(space);

        reservation.setStartDate(request.getStartDate());
        reservation.setEndDate(request.getEndDate());

        reservation.setTotalHours(Math.toIntExact(calculateHours(reservation)));
        reservation.setTotalAmount(calculateAmount(reservation));

        reservation.setStatus(ReservationStatus.PENDING_PAYMENT);

        reservation.setCreatedAt(LocalDateTime.now());
        reservation.setUpdatedAt(null);

        if (reservationRepository.existsOverlappingReservation(
                space.getId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                ReservationStatus.CANCELLED)) {

            throw new OverlappingReservationException(
                    "The selected space is already reserved during the requested period.");
        }


        reservation = reservationRepository.save(reservation);

        return reservationMapper.toResponse(reservation);
    }

    @Override
    public ReservationResponse findById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Reservation not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if (!isAdmin &&
                !reservation.getUser().getUsername().equals(authentication.getName())) {

            throw new IllegalArgumentException("You are not allowed to view this reservation.");
        }

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("User was not found"));

        return reservationRepository.findByUser(user)
                .stream()
                .map(reservationMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public ReservationResponse cancel(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ObjectNotFoundException("Reservation not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        /*boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if (!isAdmin &&
                !reservation.getUser().getUsername().equals(authentication.getName())) {

            throw new IllegalArgumentException("You are not allowed to cancel this reservation.");
        }*/

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new IllegalArgumentException("Reservation is already cancelled.");
        }

        if (reservation.getStatus() == ReservationStatus.COMPLETED) {
            throw new IllegalArgumentException("Completed reservations cannot be cancelled.");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservation.setUpdatedAt(LocalDateTime.now());

        reservation = reservationRepository.save(reservation);

        return reservationMapper.toResponse(reservation);
    }

    private Long calculateHours(Reservation request){
        return Duration.between(
                        request.getStartDate(),
                        request.getEndDate())
                .toHours();

    }
    private BigDecimal calculateAmount(Reservation reservation) {

        return reservation.getSpace()
                .getHourlyRate()
                .multiply(BigDecimal.valueOf(reservation.getTotalHours()));

    }
}
