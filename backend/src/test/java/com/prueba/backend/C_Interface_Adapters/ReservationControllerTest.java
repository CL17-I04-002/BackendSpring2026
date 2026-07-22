package com.prueba.backend.C_Interface_Adapters;

import com.prueba.backend.B_Use_Cases.Services.Interface.IReservationService;
import com.prueba.backend.B_Use_Cases.Services.dto.ReservationRequest;
import com.prueba.backend.B_Use_Cases.Services.dto.ReservationResponse;
import com.prueba.backend.C_Interface_Adapters.controllers.ReservationController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReservationControllerTest {
    @Mock
    private IReservationService reservationService;

    @InjectMocks
    private ReservationController controller;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateReservation() {

        ReservationRequest request = new ReservationRequest();
        ReservationResponse response = mock(ReservationResponse.class);

        when(reservationService.create(request)).thenReturn(response);

        ResponseEntity<ReservationResponse> result = controller.create(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());

        verify(reservationService).create(request);
        verifyNoMoreInteractions(reservationService);
    }

    @Test
    void shouldFindReservationById() {

        ReservationResponse response = mock(ReservationResponse.class);

        when(reservationService.findById(1L)).thenReturn(response);

        ResponseEntity<ReservationResponse> result = controller.findById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());

        verify(reservationService).findById(1L);
        verifyNoMoreInteractions(reservationService);
    }

    @Test
    void shouldReturnAllReservations() {

        List<ReservationResponse> responses = List.of(
                mock(ReservationResponse.class),
                mock(ReservationResponse.class)
        );

        when(reservationService.findAll()).thenReturn(responses);

        ResponseEntity<List<ReservationResponse>> result = controller.findAll();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responses, result.getBody());

        verify(reservationService).findAll();
        verifyNoMoreInteractions(reservationService);
    }

    @Test
    void shouldReturnMyReservations() {

        List<ReservationResponse> responses = List.of(
                mock(ReservationResponse.class),
                mock(ReservationResponse.class)
        );

        when(reservationService.findMyReservations()).thenReturn(responses);

        ResponseEntity<List<ReservationResponse>> result = controller.findMyReservations();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responses, result.getBody());

        verify(reservationService).findMyReservations();
        verifyNoMoreInteractions(reservationService);
    }

    @Test
    void shouldCancelReservation() {

        ReservationResponse response = mock(ReservationResponse.class);

        when(reservationService.cancel(1L)).thenReturn(response);

        ResponseEntity<ReservationResponse> result = controller.cancel(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());

        verify(reservationService).cancel(1L);
        verifyNoMoreInteractions(reservationService);
    }

}