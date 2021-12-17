package com.veterok.sensorapi.service;

import com.veterok.sensorapi.model.light.SensorLightDto;

import java.time.LocalTime;

public interface Constants {
    SensorLightDto DEFAULT_SETTINGS = new SensorLightDto(5, 30, LocalTime.of(22, 0), LocalTime.of(6, 0));
}
