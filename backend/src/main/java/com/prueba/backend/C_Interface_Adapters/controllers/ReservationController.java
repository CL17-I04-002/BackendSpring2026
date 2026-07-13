package com.prueba.backend.C_Interface_Adapters.controllers;

import com.prueba.backend.B_Use_Cases.Services.Interface.IReservationService;
import com.prueba.backend.B_Use_Cases.Services.dto.ReservationRequest;
import com.prueba.backend.B_Use_Cases.Services.dto.ReservationResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@AllArgsConstructor
public class ReservationController {
    private final IReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> create(
            @Valid @RequestBody ReservationRequest request) {

        ReservationResponse response = reservationService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(reservationService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> findAll() {

        return ResponseEntity.ok(reservationService.findAll());
    }

    @GetMapping("/my")
    public ResponseEntity<List<ReservationResponse>> findMyReservations() {

        return ResponseEntity.ok(reservationService.findMyReservations());
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ReservationResponse> cancel(
            @PathVariable Long id) {

        return ResponseEntity.ok(reservationService.cancel(id));
    }
}
