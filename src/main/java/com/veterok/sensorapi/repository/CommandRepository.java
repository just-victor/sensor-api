package com.veterok.sensorapi.repository;

import com.veterok.sensorapi.model.Command;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface CommandRepository extends ReactiveCrudRepository<Command, UUID> {
}
