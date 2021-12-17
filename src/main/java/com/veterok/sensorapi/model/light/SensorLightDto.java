package com.veterok.sensorapi.model.light;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class SensorLightDto {
    @JsonProperty("pp")
    private int pingPeriodInMin;
    @JsonProperty("npp")
    private int nightPingPeriodInMin;
    @JsonProperty("ns")
    @JsonFormat(pattern="HH:mm")
    private LocalTime nightStart;
    @JsonProperty("ne")
    @JsonFormat(pattern="HH:mm")
    private LocalTime nightEnd;
    @JsonProperty("lng")
    private double longitude;
    @JsonProperty("lat")
    private double latitude;

    public SensorLightDto(int pingPeriodInMin, int nightPingPeriodInMin, LocalTime nightStart, LocalTime nightEnd) {
        this.pingPeriodInMin = pingPeriodInMin;
        this.nightPingPeriodInMin = nightPingPeriodInMin;
        this.nightStart = nightStart;
        this.nightEnd = nightEnd;
    }
}
