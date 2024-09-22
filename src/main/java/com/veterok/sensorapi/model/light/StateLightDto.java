package com.veterok.sensorapi.model.light;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StateLightDto {
//    @JsonProperty("t")
//    private Instant timestamp;
//    @JsonProperty("prt")
//    private Instant previousRequestTime;
    @JsonProperty("ws")
    private float windSpeed;
    @JsonProperty("wxs")
    private float windMaxSpeed;
    @JsonProperty("wns")
    private float windMinSpeed;
    @JsonProperty("wd")
    private int windDirection;
    @JsonProperty("pp")
    private int powerPercentages;
    @JsonProperty("tmp")
    private float temperature;
    @JsonProperty("lt")
    private float latitude;
    @JsonProperty("ln")
    private float longitude;
    @JsonProperty("s")
    private int satellites;
}
