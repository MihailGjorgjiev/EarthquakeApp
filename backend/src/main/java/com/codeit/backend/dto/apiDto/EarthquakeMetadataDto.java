package com.codeit.backend.dto.apiDto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EarthquakeMetadataDto {
    private int status;
    private int count;
}
