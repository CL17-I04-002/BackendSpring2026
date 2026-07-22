package com.prueba.backend.C_Interface_Adapters;

import com.prueba.backend.B_Use_Cases.Services.Interface.IReportService;
import com.prueba.backend.C_Interface_Adapters.controllers.ReportController;
import com.prueba.backend.C_Interface_Adapters.controllers.dto.OccupancyReportResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

class ReportControllerTest {
    @Mock
    private IReportService reportService;

    @InjectMocks
    private ReportController controller;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnOccupancyReport() {

        LocalDateTime start = LocalDateTime.of(2026, 1, 1, 8, 0);
        LocalDateTime end = LocalDateTime.of(2026, 1, 31, 18, 0);

        List<OccupancyReportResponse> report = List.of(
                Mockito.mock(OccupancyReportResponse.class),
                Mockito.mock(OccupancyReportResponse.class)
        );

        Mockito.when(reportService.occupancy(start, end)).thenReturn(report);

        ResponseEntity<List<OccupancyReportResponse>> response =
                controller.occupancy(start, end);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(report, response.getBody());

        Mockito.verify(reportService).occupancy(start, end);
        Mockito.verifyNoMoreInteractions(reportService);
    }
}