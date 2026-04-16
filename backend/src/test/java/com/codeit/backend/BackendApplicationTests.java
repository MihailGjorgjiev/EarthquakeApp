package com.codeit.backend;

import com.codeit.backend.dto.apiDto.EarthquakeResponseDto;
import com.codeit.backend.mapper.JsonToDtoMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@SpringBootTest
class BackendApplicationTests {
    private final JsonToDtoMapper mapper = new JsonToDtoMapper();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldMapValidGeoJson() throws Exception {
        String json = """
                {
                  "metadata": { "status": 200, "count": 1 },
                  "features": [
                    {
                      "id": "2026earthquakeid",
                      "properties": {
                        "mag": 5.2,
                        "magType": "ml",
                        "place": "2.3km from Sahara Desert",
                        "time": 123456,
                        "title": "Test Earthquake"
                      },
                      "geometry": {
                        "coordinates": [10.0, 20.0, 5.0]
                      }
                    }
                  ]
                }
                """;

        JsonNode node = objectMapper.readTree(json);

        EarthquakeResponseDto result = mapper.mapToDto(node);

        assertEquals(200, result.getMetadata().getStatus());
        assertEquals(1, result.getFeatures().size());
        assertEquals("2026earthquakeid", result.getFeatures().get(0).getId());
    }

    @Test
    void shouldThrowExceptionWhenMetadataMissing() throws JsonProcessingException {
        String json = """
    {
      "features": []
    }
    """;

        JsonNode node = objectMapper.readTree(json);

        assertThrows(IllegalStateException.class, () -> {
            mapper.mapToDto(node);
        });
    }

    @Test
    void shouldThrowWhenCoordinatesMissing() throws JsonProcessingException {
        String json = """
    {
      "metadata": { "status": 200, "count": 1 },
      "features": [
        {
          "id": "2026earthquakeid",
          "properties": {
            "mag": 5.2,
            "magType": "ml",
            "place": "no where",
            "time": 1,
            "title": "t"
          },
          "geometry": {}
        }
      ]
    }
    """;

        JsonNode node = objectMapper.readTree(json);

        assertThrows(IllegalStateException.class, () -> mapper.mapToDto(node));
    }

}
