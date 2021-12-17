package com.veterok.sensorapi.repository;

import com.veterok.sensorapi.model.State;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface StateRepository extends ReactiveCrudRepository<State, UUID> {
}
