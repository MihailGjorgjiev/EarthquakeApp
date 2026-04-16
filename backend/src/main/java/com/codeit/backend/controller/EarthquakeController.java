package com.codeit.backend.controller;

import com.codeit.backend.dto.EarthquakeDto;
import com.codeit.backend.service.EarthquakeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/earthquakes")
public class EarthquakeController{

    private final EarthquakeService earthquakeService;

    public EarthquakeController(EarthquakeService earthquakeService) {
        this.earthquakeService = earthquakeService;
    }

    @GetMapping
    public ResponseEntity<List<EarthquakeDto>> getAllEarthquakes(){
        List<EarthquakeDto> earthquakes=earthquakeService.getAllEarthquakes();
        return new ResponseEntity<>(earthquakes, HttpStatus.OK);
    }


    @GetMapping("/magnitude")
    public ResponseEntity<List<EarthquakeDto>> getEarthquakesWithMagnitudeGreaterThan(@RequestParam(value = "magnitude",required = false)BigDecimal magnitude){
        List<EarthquakeDto> earthquakes=earthquakeService.getEarthquakesWithMagnitudeGreaterThanEqual(magnitude);
        return new ResponseEntity<>(earthquakes, HttpStatus.OK);
    }

    @GetMapping("/timestamp")
    public ResponseEntity<List<EarthquakeDto>> getEarthquakesWithTimeAfter(@RequestParam(value = "timestamp",required = false)Long timestamp){
        List<EarthquakeDto> earthquakes=earthquakeService.getEarthquakesAfterDefinedTime(timestamp);
        return new ResponseEntity<>(earthquakes, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEarthquake(@PathVariable UUID id){
        earthquakeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
