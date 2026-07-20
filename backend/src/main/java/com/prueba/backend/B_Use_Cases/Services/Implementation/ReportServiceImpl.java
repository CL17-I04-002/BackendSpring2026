package com.prueba.backend.B_Use_Cases.Services.Implementation;

import com.prueba.backend.A_Domain.business.Reservation;
import com.prueba.backend.A_Domain.business.Space;
import com.prueba.backend.B_Use_Cases.Services.Interface.IReportService;
import com.prueba.backend.C_Interface_Adapters.controllers.dto.OccupancyReportResponse;
import com.prueba.backend.D_Infraestructure.repository.ReservationRepository;
import com.prueba.backend.D_Infraestructure.repository.SpaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements IReportService {
    private final SpaceRepository spaceRepository;

    private final ReservationRepository reservationRepository;

    @Override
    @Cacheable("occupancy-report")
    public List<OccupancyReportResponse> occupancy(LocalDateTime start, LocalDateTime end) {
        long totalHours =
                Duration.between(start, end).toHours();

        List<Space> spaces = spaceRepository.findAll();

        List<OccupancyReportResponse> report = new ArrayList<>();

        for (Space space : spaces) {

            List<Reservation> reservations =
                    reservationRepository.findConfirmedReservations(
                            space.getId(),
                            start,
                            end);

            long reservedHours = reservations.stream()
                    .mapToLong(r ->
                            Duration.between(
                                            r.getStartDate(),
                                            r.getEndDate())
                                    .toHours())
                    .sum();

            double percentage =
                    totalHours == 0
                            ? 0
                            : (reservedHours * 100.0) / totalHours;

            report.add(
                    OccupancyReportResponse.builder()
                            .spaceId(space.getId())
                            .spaceName(space.getName())
                            .occupancyPercentage(
                                    Math.round(percentage * 100.0) / 100.0)
                            .build()
            );
        }

        return report;
    }
}
