package com.prueba.backend.B_Use_Cases.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Error> invalidPassException(InvalidPasswordException exception){
        Error error = buildError("500", "Invalid Password", "Invalid Password, try again");
        log.error("Invalid Password, user must try again");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Error> invalidPassException(ObjectNotFoundException exception){
        Error error = buildError("500", "Something happened wrong", "unfortunately something happened wrong, try again");
        log.error("Error: {}", exception.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(OverlappingReservationException.class)
    public ResponseEntity<Error> overlappingReservationException(OverlappingReservationException exception){
        Error error = buildError("500", "Something happened wrong", "unfortunately something happened wrong, try again");
        log.error("Error: {}", exception.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    private Error buildError(String code, String title, String detail){
        Error error = new Error();
        error.setCode(code);
        error.setTitle(title);
        error.setDetail(detail);

        return error;
    }
}
