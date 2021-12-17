package com.veterok.sensorapi.model.light;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StateLightDto {
    @JsonProperty("t")
    private Instant timestamp;
    @JsonProperty("prt")
    private Instant previousRequestTime;
    @JsonProperty("ws")
    private double windSpeed;
    @JsonProperty("wmxs")
    private double windMaxSpeed;
    @JsonProperty("wmns")
    private double windMinSpeed;
    @JsonProperty("wd")
    private int windDirection;
    @JsonProperty("pp")
    private double powerPercentages;
    @JsonProperty("tmp")
    private double temperature;
}
