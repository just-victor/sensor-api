package com.veterok.sensorapi.service;

import com.veterok.sensorapi.feign.GeonamesService;
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
    private final GeonamesService geonamesService;

    public Mono<SensorLightDto> getSensorSettings(UUID sensorId) {
        return sensorRepository.findById(sensorId)
                .map(mapper::makeItLight)
                .switchIfEmpty(Mono.just(DEFAULT_SETTINGS));
    }

    public void addState(UUID sensorId, StateLightDto stateDto) {
        log.info("Adding state for sensor {}", sensorId);
        log.info("State: {}", stateDto);
        State state = stateMapper.toEntity(stateDto);
        state.setSensorId(sensorId);
        stateRepository.save(state)
                .flatMap(it -> updateSensorData(state))
                .subscribe();
    }

    public void registerSensor(UUID sensorId) {
        sensorRepository.findById(sensorId)
                .switchIfEmpty(createNewSensor(sensorId))
                .map(sensor -> {
                    sensor.setRegisteredTime(Instant.now());
                    return sensor;
                })
                .flatMap(sensorRepository::save)
                .doOnSuccess(sensor -> log.info("Sensor {} registered", sensor.getId()))
                .subscribe();
    }

    private Mono<? extends Sensor> createNewSensor(UUID sensorId) {
        Sensor sensor = new Sensor();
        sensor.setId(sensorId);
        log.info("New sensor {} created", sensorId);
        return Mono.just(sensor);
    }

    private Mono<Sensor> updateSensorData(State sensorState) {
        return sensorRepository.findById(sensorState.getSensorId())
                .flatMap(sensor -> {
                    sensor.setLastPingTime(Instant.now());
                    if (sensor.getTimezoneId() == null && sensorState.getLatitude() != 0 && sensorState.getLongitude() != 0) {
                        return geonamesService.getTimezone(sensorState.getLatitude(), sensorState.getLongitude())
                                .map(geonames -> {
                                    sensor.setTimezoneId(geonames.getId());
                                    return sensor;
                                }).doOnError(e -> log.error("Error getting timezone", e));
                    }
                    return Mono.just(sensor);
                })
                .flatMap(sensorRepository::save);
    }

    public Flux<Sensor> getSensors() {
        return sensorRepository.findAll();
    }
}
