package com.codeit.backend.dto.apiDto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EarthquakeGeometryDto {
    private List<Double> coordinates;

}
