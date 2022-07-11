package com.veterok.sensorapi.service;

import com.veterok.sensorapi.model.LogItem;
import com.veterok.sensorapi.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogService {
    private static final ThreadLocal<DateTimeFormatter> FORMATTER;
    static {
        FORMATTER = ThreadLocal.withInitial(() -> DateTimeFormatter
                        .ofPattern("dd/MM/yyyy HH:mm:ss")
                        .withZone(ZoneId.systemDefault())
                );
    }
    private final LogRepository repository;

    public void logBulkString(UUID id, String logString) {
        log.info("Start preparing internal logs for sensor: {}", id);
        List<LogItem> logItems = Arrays.stream(logString.split("\n"))
                .map(this::parseStringToLogItem)
                .peek(it -> it.setSensorId(id))
                .collect(Collectors.toList());

        log.info("Start saving {} log items for sensor: {}", logItems.size(), id);
        repository.saveAll(logItems)
                .subscribe();
    }

    private LogItem parseStringToLogItem(String logItemString) {
        String dateTime = logItemString.substring(0, 19);
        DateTimeFormatter dateTimeFormatter = FORMATTER.get();

        Instant parsedInstant = null;
        String logText;

        try {
            parsedInstant = Instant.from(dateTimeFormatter.parse(dateTime));
            logText = logItemString.substring(21);
        } catch (DateTimeParseException e) {
            log.warn("Error while parsing {}", dateTime);
            logText = logItemString;
        }

        LogItem logItem = new LogItem();
        logItem.setLog(logText);
        logItem.setTime(parsedInstant);

        return logItem;
    }
}
