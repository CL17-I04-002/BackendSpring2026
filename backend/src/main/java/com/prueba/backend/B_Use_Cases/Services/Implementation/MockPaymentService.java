package com.prueba.backend.B_Use_Cases.Services.Implementation;

import com.prueba.backend.A_Domain.business.Reservation;
import com.prueba.backend.B_Use_Cases.Services.Interface.IPaymentService;
import org.springframework.stereotype.Service;

@Service
public class MockPaymentService implements IPaymentService {
    @Override
    public boolean validatePayment(Reservation reservation) {
        return true;
    }
}
