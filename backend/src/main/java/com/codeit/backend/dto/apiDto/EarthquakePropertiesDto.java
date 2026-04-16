package com.codeit.backend.dto.apiDto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EarthquakePropertiesDto{
        private Double mag;
        private String magType;
        private String place;
        private Long time;
        private String title;

}
