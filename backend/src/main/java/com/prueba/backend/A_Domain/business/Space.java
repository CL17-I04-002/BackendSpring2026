package com.prueba.backend.A_Domain.business;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "spaces")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Space {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min = 5, max = 20)
    private String name;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Type type;
    @NotNull
    @Positive
    private Integer capacity;
    @NotBlank
    @Size(min = 20, max = 150)
    private String location;
    @NotNull
    @Positive
    private BigDecimal hourlyRate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
