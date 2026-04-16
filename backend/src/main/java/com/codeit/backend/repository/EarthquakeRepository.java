package com.codeit.backend.repository;

import com.codeit.backend.entity.Earthquake;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface EarthquakeRepository extends JpaRepository<Earthquake, UUID> {


    List<Earthquake> getEarthquakesByMagnitudeGreaterThanEqual(BigDecimal magnitude);

    List<Earthquake> getEarthquakesByTimestampGreaterThan(Long timestamp);


}
