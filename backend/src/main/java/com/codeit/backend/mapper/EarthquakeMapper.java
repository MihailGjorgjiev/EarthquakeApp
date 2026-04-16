package com.codeit.backend.mapper;

import com.codeit.backend.dto.EarthquakeDto;
import com.codeit.backend.entity.Earthquake;
import org.springframework.stereotype.Component;

@Component
public class EarthquakeMapper {
    public EarthquakeDto toDto(Earthquake earthquake) {
        if(earthquake == null) throw new IllegalArgumentException("Earthquake entity is null.");
        return EarthquakeDto.builder()
                .id(earthquake.getId())
                .earthquakeId(earthquake.getEarthquakeId())
                .magnitude(earthquake.getMagnitude())
                .magType(earthquake.getMagType())
                .title(earthquake.getTitle())
                .place(earthquake.getPlace())
                .timestamp(earthquake.getTimestamp())
                .longitude(earthquake.getLongitude())
                .latitude(earthquake.getLatitude())
                .depth(earthquake.getDepth())
                .build();
    }

    public Earthquake toEntity(EarthquakeDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Earthquake DTO is null.");
        }
        Earthquake entity = new Earthquake();
        entity.setId(dto.getId());
        entity.setEarthquakeId(dto.getEarthquakeId());
        entity.setMagnitude(dto.getMagnitude());
        entity.setMagType(dto.getMagType());
        entity.setTitle(dto.getTitle());
        entity.setPlace(dto.getPlace());
        entity.setTimestamp(dto.getTimestamp());
        entity.setLongitude(dto.getLongitude());
        entity.setLatitude(dto.getLatitude());
        entity.setDepth(dto.getDepth());

        return entity;
    }
}
