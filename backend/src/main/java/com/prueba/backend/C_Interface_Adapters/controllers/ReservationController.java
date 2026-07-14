package com.prueba.backend.C_Interface_Adapters.controllers;

import com.prueba.backend.B_Use_Cases.Services.Interface.IReservationService;
import com.prueba.backend.B_Use_Cases.Services.dto.ReservationRequest;
import com.prueba.backend.B_Use_Cases.Services.dto.ReservationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@AllArgsConstructor
@Tag(
        name = "Reservations",
        description = "Operations related to coworking space reservations"
)
public class ReservationController {
    private final IReservationService reservationService;

    @Operation(
            summary = "Create a new reservation",
            description = "Creates a reservation for a coworking space. The reservation is created using the authenticated user's information."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Reservation successfully created"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid reservation data"
    )
    @PostMapping
    public ResponseEntity<ReservationResponse> create(
            @Valid @RequestBody ReservationRequest request) {

        ReservationResponse response = reservationService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }


    @Operation(
            summary = "Find reservation by id",
            description = "Returns a reservation using its unique identifier."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Reservation found successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Reservation not found"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                reservationService.findById(id)
        );
    }


    @Operation(
            summary = "Get all reservations",
            description = "Returns all registered reservations."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Reservations retrieved successfully"
    )
    @GetMapping
    public ResponseEntity<List<ReservationResponse>> findAll() {

        return ResponseEntity.ok(
                reservationService.findAll()
        );
    }


    @Operation(
            summary = "Get my reservations",
            description = "Returns all reservations created by the authenticated user."
    )
    @ApiResponse(
            responseCode = "200",
            description = "User reservations retrieved successfully"
    )
    @GetMapping("/my")
    public ResponseEntity<List<ReservationResponse>> findMyReservations() {

        return ResponseEntity.ok(
                reservationService.findMyReservations()
        );
    }


    @Operation(
            summary = "Cancel reservation",
            description = "Cancels an existing reservation using its identifier."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Reservation cancelled successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Reservation not found"
    )
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ReservationResponse> cancel(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                reservationService.cancel(id)
        );
    }

}
