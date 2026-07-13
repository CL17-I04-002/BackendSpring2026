package com.prueba.backend.B_Use_Cases.Events;

import com.prueba.backend.A_Domain.business.Reservation;

public class ReservationConfirmedEvent {
    private final Reservation reservation;

    public ReservationConfirmedEvent(Reservation reservation) {
        this.reservation = reservation;
    }

    public Reservation getReservation() {
        return reservation;
    }

}
