package com.veterok.sensorapi.model.dto;

import lombok.Data;

@Data
public class Geoname {
    private String countryCode;
    private String countryName;
    private String timezoneId;
}
