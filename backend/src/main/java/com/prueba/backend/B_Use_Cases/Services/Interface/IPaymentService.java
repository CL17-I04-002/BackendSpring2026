package com.prueba.backend.B_Use_Cases.Services.Interface;

import com.prueba.backend.A_Domain.business.Reservation;

public interface IPaymentService {
    boolean validatePayment(Reservation reservation);
}
