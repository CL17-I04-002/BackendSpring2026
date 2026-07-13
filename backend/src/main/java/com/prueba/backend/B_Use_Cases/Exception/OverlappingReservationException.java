package com.prueba.backend.B_Use_Cases.Exception;

public class OverlappingReservationException extends RuntimeException{
    public OverlappingReservationException(){

    }
    public OverlappingReservationException(String message) {
        super(message);
    }
    public OverlappingReservationException(String message, Throwable cause) {
        super(message, cause);
    }
}
