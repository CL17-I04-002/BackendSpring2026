package com.prueba.backend.B_Use_Cases.Services.Implementation;

import com.prueba.backend.A_Domain.business.Reservation;
import com.prueba.backend.B_Use_Cases.Services.Interface.IPaymentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@AllArgsConstructor
public class MockPaymentService implements IPaymentService {

    private final WebClient webClient;

    @Override
    @CircuitBreaker(
            name = "paymentService",
            fallbackMethod = "paymentFallback"
    )

    public boolean validatePayment(Reservation reservation) {
        return webClient.post()
                .uri("/mock-payment/validate")
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }

    public boolean paymentFallback(
            Reservation reservation,
            Throwable exception) {

        log.warn(
                "Payment service unavailable. Reservation {} remains PENDING_PAYMENT",
                reservation.getId());
        log.error("Payment service failed", exception);

        return false;
    }
}
