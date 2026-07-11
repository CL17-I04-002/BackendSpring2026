package com.prueba.backend.A_Domain.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "operation")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String path;
    private String httpMethod;
    private boolean permitAll;
    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;
}
