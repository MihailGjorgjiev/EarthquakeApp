package com.codeit.backend.mapper;

import com.codeit.backend.dto.apiDto.*;
import com.codeit.backend.exception.MissingJsonDataException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JsonToDtoMapper {
    public EarthquakeResponseDto mapToDto(JsonNode root) {
        try {

            JsonNode meta = root.get("metadata");
            if (meta == null){
                throw new MissingJsonDataException("Metadata missing in response.");
            }
            EarthquakeMetadataDto metadata = new EarthquakeMetadataDto(
                    meta.get("status").asInt(),
                    meta.get("count").asInt()
            );

            List<EarthquakeFeatureDto> features = new ArrayList<>();
            JsonNode featuresNode=root.get("features");
            if (featuresNode == null){
                throw new MissingJsonDataException("Features missing in response.");
            }

            if(!featuresNode.isArray()){
                throw new IllegalArgumentException("Features need to be in array format.");
            }
            for (JsonNode feature : featuresNode) {
                JsonNode props = feature.get("properties");

                if(props == null){
                    throw new MissingJsonDataException("Properties missing in response.");
                }

                EarthquakePropertiesDto properties = new EarthquakePropertiesDto(
                        props.get("mag").asDouble(),
                        props.get("magType").asText(),
                        props.get("place").asText(),
                        props.get("time").asLong(),
                        props.get("title").asText()
                );

                List<Double> coords = new ArrayList<>();
                JsonNode geometryNode = feature.get("geometry");
                if (geometryNode == null) {
                    throw new MissingJsonDataException("Geometry missing for feature.");
                }
                JsonNode coordsNode = geometryNode.get("coordinates");
                if (coordsNode == null) {
                    throw new MissingJsonDataException("Coordinates missing for feature.");
                }
                if(!coordsNode.isArray()){
                    throw new IllegalArgumentException("Coordinates missing in feature.");
                }
                for (JsonNode coord : coordsNode) {
                    coords.add(coord.asDouble());
                }

                features.add(new EarthquakeFeatureDto(
                        feature.get("id").asText(),
                        properties,
                        new EarthquakeGeometryDto(coords)
                ));
            }

            return new EarthquakeResponseDto(metadata, features);
        } catch (Exception e){
            throw new IllegalStateException("Error while mapping Json to Dto",e);
        }
    }
}
