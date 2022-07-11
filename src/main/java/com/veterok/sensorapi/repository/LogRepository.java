package com.veterok.sensorapi.repository;

import com.veterok.sensorapi.model.LogItem;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface LogRepository extends ReactiveCrudRepository<LogItem, UUID> {
}
