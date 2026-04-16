package com.codeit.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
public class EarthquakeDto {

    private UUID id;
    private String earthquakeId;
    private BigDecimal magnitude;
    private String magType;
    private String title;
    private String place;
    private Long timestamp;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private BigDecimal depth;
}
