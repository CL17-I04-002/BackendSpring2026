package com.prueba.backend.D_Infraestructure.Listener;

import com.prueba.backend.B_Use_Cases.Events.ReservationConfirmedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReservationNotificationListener {
    @Async
    @EventListener
    public void handleReservationConfirmed(ReservationConfirmedEvent event){

        log.info("""
                ===== SIMULATING EMAIL =====
                Reservation {} confirmed.
                Customer: {}
                Space: {}
                ===========================
                """,
                event.getReservation().getId(),
                event.getReservation().getUser().getUsername(),
                event.getReservation().getSpace().getName());

    }
}
