package com.codeit.backend.service.impl;

import com.codeit.backend.dto.EarthquakeDto;
import com.codeit.backend.dto.apiDto.EarthquakeFeatureDto;
import com.codeit.backend.exception.DatabaseException;
import com.codeit.backend.mapper.EarthquakeMapper;
import com.codeit.backend.mapper.JsonToDtoMapper;
import com.codeit.backend.repository.EarthquakeRepository;
import com.codeit.backend.service.EarthquakeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class EarthquakeServiceImpl implements EarthquakeService {

    private final EarthquakeRepository earthquakeRepository;

    private final String url = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";
    private final WebClient webClient = WebClient.create(url);

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final JsonToDtoMapper jsonToDtoMapper;
    private final EarthquakeMapper earthquakeMapper;

    public EarthquakeServiceImpl(EarthquakeRepository earthquakeRepository, JsonToDtoMapper jsonToDtoMapper, EarthquakeMapper earthquakeMapper) {
        this.earthquakeRepository = earthquakeRepository;
        this.earthquakeMapper = earthquakeMapper;
        this.jsonToDtoMapper = jsonToDtoMapper;
    }

    @Scheduled(fixedRate = 10000)
    @Override
    public void fetchAll() {
        webClient.get()
                .retrieve()
                .bodyToMono(String.class)
                .map(json -> {
                    try {
                        return jsonToDtoMapper.mapToDto(objectMapper.readTree(json));
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to parse response", e);
                    }
                })
                .doOnError(e ->
                        System.err.println("API Unavailability Error: " + e.getMessage()))
                .subscribe(
                        dto -> {
                            if (dto.getMetadata().getStatus() == 200) {
                                deleteAll();
                                for (EarthquakeFeatureDto feature : dto.getFeatures()) {
                                    try {
                                        EarthquakeDto earthquakeDto = EarthquakeDto.builder()
                                                .id(null)
                                                .earthquakeId(feature.getId())
                                                .magnitude(BigDecimal.valueOf(feature.getProperties().getMag()))
                                                .magType(feature.getProperties().getMagType())
                                                .title(feature.getProperties().getTitle())
                                                .place(feature.getProperties().getPlace())
                                                .timestamp(feature.getProperties().getTime())
                                                .longitude(BigDecimal.valueOf(feature.getGeometry().getCoordinates().get(0)))
                                                .latitude(BigDecimal.valueOf(feature.getGeometry().getCoordinates().get(1)))
                                                .depth(BigDecimal.valueOf(feature.getGeometry().getCoordinates().get(2)))
                                                .build();

                                        save(earthquakeDto);

                                    } catch (Exception e) {
                                        System.err.println("Error saving earthquake: " + e.getMessage());

                                    }
                                }

                            }
                        },
                        err -> System.err.println("Error during API call: " + err.getMessage())

                );
    }

    @Override
    public List<EarthquakeDto> getAllEarthquakes() {
        return earthquakeRepository.findAll().stream().map(earthquakeMapper::toDto).collect(Collectors.toList());
    }

    public void deleteAll() {
        try {
            earthquakeRepository.deleteAll();
        } catch (Exception e) {
            throw new DatabaseException("Failed to delete all earthquakes from the database", e);
        }
    }

    public void save(EarthquakeDto earthquakeDto) {
        try {
            if (!(earthquakeDto.getMagnitude().compareTo(new BigDecimal("0.0")) >= 0)) {
                throw new IllegalArgumentException("Magnitude has to be a positive value.");
            }
            earthquakeRepository.save(earthquakeMapper.toEntity(earthquakeDto));
        } catch (Exception e) {
            throw new DatabaseException("Failed to save earthquake to the database", e);
        }

    }

    @Override
    public List<EarthquakeDto> getEarthquakesWithMagnitudeGreaterThanEqual(BigDecimal magnitude) {
        return earthquakeRepository.getEarthquakesByMagnitudeGreaterThanEqual(magnitude).stream()
                .map(earthquakeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EarthquakeDto> getEarthquakesAfterDefinedTime(Long timestamp) {
        return earthquakeRepository.getEarthquakesByTimestampGreaterThan(timestamp).stream()
                .map(earthquakeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        earthquakeRepository.deleteById(id);
    }
}
