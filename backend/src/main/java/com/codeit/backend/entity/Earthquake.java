package com.codeit.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Table(name = "earthquakes",
        indexes = {
                @Index(name = "idx_earthquake_timestamp", columnList = "timestamp"),
                @Index(name = "idx_earthquake_mag", columnList = "magnitude")
        })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Earthquake {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "earthquake_id", length = 50)
    @Size(max = 50)
    private String earthquakeId;

    @NotNull
    @Column(name = "magnitude", nullable = false)
    private BigDecimal magnitude;

    @NotNull
    @Column(name = "mag_type", nullable = false, length = 50)
    @Size(max = 50)
    private String magType;

    @NotNull
    @Column(name = "title", nullable = false, length = 255)
    @Size(max = 255)
    private String title;

    @NotNull
    @Column(name = "place", nullable = false, length = 255)
    @Size(max = 255)
    private String place;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private Long timestamp;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private BigDecimal longitude;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private BigDecimal latitude;

    @NotNull
    @Column(name = "depth", nullable = false)
    private BigDecimal depth;

}