package com.veterok.sensorapi.repository;

import com.veterok.sensorapi.model.Sensor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SensorRepository extends ReactiveCrudRepository<Sensor, UUID> {
}
