package com.prueba.backend.B_Use_Cases.Exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Error {
    private String code;
    private String title;
    private String detail;
}
