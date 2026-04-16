package com.codeit.backend.service;

import com.codeit.backend.dto.EarthquakeDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface EarthquakeService {

    void fetchAll();

    void deleteAll();
    void save(EarthquakeDto earthquakeDto);

    List<EarthquakeDto> getAllEarthquakes();

    List<EarthquakeDto> getEarthquakesWithMagnitudeGreaterThanEqual(BigDecimal magnitude);
    List<EarthquakeDto> getEarthquakesAfterDefinedTime(Long timestamp);

    void deleteById(UUID id);

}
