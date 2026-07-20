package com.prueba.backend.C_Interface_Adapters.controllers.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OccupancyReportResponse {
    private Long spaceId;

    private String spaceName;

    private Double occupancyPercentage;
}
