package com.codeit.backend;

import com.codeit.backend.dto.EarthquakeDto;
import com.codeit.backend.entity.Earthquake;
import com.codeit.backend.exception.DatabaseException;
import com.codeit.backend.mapper.EarthquakeMapper;
import com.codeit.backend.mapper.JsonToDtoMapper;
import com.codeit.backend.repository.EarthquakeRepository;
import com.codeit.backend.service.impl.EarthquakeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTests {
    @Mock
    private EarthquakeRepository repository;

    @Mock
    private EarthquakeMapper earthquakeMapper;

    @InjectMocks
    private EarthquakeServiceImpl service;



    @Test
    void shouldNotSaveWhenMagnitudeIsNullOrInvalid() {
        EarthquakeDto dto = EarthquakeDto.builder()
                .magnitude(new BigDecimal("-1"))
                .build();

        assertThrows(DatabaseException.class, () -> service.save(dto));

    }


    @Test
    void shouldSaveValidEarthquake() {
        EarthquakeDto dto = EarthquakeDto.builder()
                .magnitude(BigDecimal.valueOf(5.0))
                .build();

        Earthquake entity = new Earthquake();

        when(earthquakeMapper.toEntity(dto)).thenReturn(entity);

        service.save(dto);

        verify(repository, times(1)).save(entity);
    }
}
