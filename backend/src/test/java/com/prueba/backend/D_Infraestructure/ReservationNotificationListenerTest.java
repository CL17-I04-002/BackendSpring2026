package com.prueba.backend.D_Infraestructure;

import com.prueba.backend.A_Domain.business.Reservation;
import com.prueba.backend.A_Domain.business.Space;
import com.prueba.backend.A_Domain.security.Users;
import com.prueba.backend.B_Use_Cases.Events.ReservationConfirmedEvent;
import com.prueba.backend.D_Infraestructure.Listener.ReservationNotificationListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ReservationNotificationListenerTest {
    private ReservationNotificationListener listener;

    @BeforeEach
    void setUp(){
        listener = new ReservationNotificationListener();
    }

    @Test
    void shouldHandleReservationConfirmedWithoutException(){
        Users users = Mockito.mock(Users.class);
        Mockito.when(users.getUsername()).thenReturn("admin");

        Space space = Mockito.mock(Space.class);
        Mockito.when(space.getName()).thenReturn("Meeting Room A");

        Reservation reservation = Mockito.mock(Reservation.class);
        Mockito.when(reservation.getId()).thenReturn(1L);
        Mockito.when(reservation.getUser()).thenReturn(users);
        Mockito.when(reservation.getSpace()).thenReturn(space);

        ReservationConfirmedEvent event = Mockito.mock(ReservationConfirmedEvent.class);

        Mockito.when(event.getReservation()).thenReturn(reservation);

        Assertions.assertDoesNotThrow(() -> listener.handleReservationConfirmed(event));
    }
}
