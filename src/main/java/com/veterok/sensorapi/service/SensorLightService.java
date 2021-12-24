package com.veterok.sensorapi.service;

import com.veterok.sensorapi.mapper.SensorLightMapper;
import com.veterok.sensorapi.mapper.StateMapper;
import com.veterok.sensorapi.model.Sensor;
import com.veterok.sensorapi.model.State;
import com.veterok.sensorapi.model.light.SensorLightDto;
import com.veterok.sensorapi.model.light.StateLightDto;
import com.veterok.sensorapi.repository.SensorRepository;
import com.veterok.sensorapi.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

import static com.veterok.sensorapi.service.Constants.DEFAULT_SETTINGS;

@Slf4j
@Service
@RequiredArgsConstructor
public class SensorLightService {
    private final SensorRepository sensorRepository;
    private final StateRepository stateRepository;
    private final SensorLightMapper mapper;
    private final StateMapper stateMapper;

    public Mono<SensorLightDto> getSensorSettings(UUID sensorId) {
        return sensorRepository.findById(sensorId)
                .map(mapper::makeItLight)
                .switchIfEmpty(Mono.just(DEFAULT_SETTINGS));
    }

    public void updateCoordinates(UUID id, SensorLightDto coordinates) {
        sensorRepository.findById(id)
                .map(sensor -> mapper.updateCoordinates(sensor, coordinates))
                .flatMap(sensorRepository::save)
                .subscribe();
    }

    public void addState(UUID sensorId, StateLightDto stateDto) {
        State state = stateMapper.toEntity(stateDto);
        state.setSensorId(sensorId);
        stateRepository.save(state)
                .flatMap(it -> updateSensorTime(state.getSensorId()))
                .subscribe();
    }

    private Mono<Sensor> updateSensorTime(UUID sensorId) {
        return sensorRepository.findById(sensorId)
                .map(sensor -> {
                    sensor.setLastPingTime(Instant.now());
                    return sensor;
                })
                .flatMap(sensorRepository::save);
    }

    public Flux<Sensor> getSensors() {
        return sensorRepository.findAll();
    }
}
