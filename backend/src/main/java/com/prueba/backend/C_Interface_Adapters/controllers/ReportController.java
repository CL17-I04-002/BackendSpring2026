package com.prueba.backend.C_Interface_Adapters.controllers;

import com.prueba.backend.B_Use_Cases.Services.Interface.IReportService;
import com.prueba.backend.C_Interface_Adapters.controllers.dto.OccupancyReportResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reports")
@AllArgsConstructor
public class ReportController {

    private final IReportService reportService;

    @GetMapping("/occupancy")
    public ResponseEntity<List<OccupancyReportResponse>> occupancy(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end){
        return ResponseEntity.ok(reportService.occupancy(start, end));
    }
}