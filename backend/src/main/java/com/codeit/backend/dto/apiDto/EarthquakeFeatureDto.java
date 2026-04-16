package com.codeit.backend.dto.apiDto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EarthquakeFeatureDto{
    private String id;
    private EarthquakePropertiesDto properties;
    private EarthquakeGeometryDto geometry;
}
