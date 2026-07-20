package com.prueba.backend.B_Use_Cases.Services.Interface;

import com.prueba.backend.C_Interface_Adapters.controllers.dto.OccupancyReportResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface IReportService {
    List<OccupancyReportResponse> occupancy(
            LocalDateTime start,
            LocalDateTime end);
}
