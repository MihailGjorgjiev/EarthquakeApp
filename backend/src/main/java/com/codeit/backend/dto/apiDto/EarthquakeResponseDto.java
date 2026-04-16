package com.codeit.backend.dto.apiDto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EarthquakeResponseDto {
    private EarthquakeMetadataDto metadata;
    private List<EarthquakeFeatureDto> features;

}
