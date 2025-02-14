package com.veterok.sensorapi.service;

import com.veterok.sensorapi.feign.GrafanaLokiService;
import com.veterok.sensorapi.feign.dto.StreamsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class SensorLogService {
    private final static Pattern PATTERN = Pattern.compile("(\\d{10,12})\\s+(I|W|E|D) \\((\\d+)\\) (.+)");

    private final GrafanaLokiService grafanaLokiService;

    public Mono<Void> pushLogs(UUID id, String logBuffer) {
        log.info("Parsing logs for device {}", id);
        StreamsDto streamsDto = new StreamsDto();
        streamsDto.setDeviceId(id.toString());

        for (String logLine : logBuffer.split("\n")) {
            List<String> logRow = parseLogLine(logLine);
            if (logRow.isEmpty()) {
                continue;
            }
            streamsDto.addLog(logRow);
        }
        log.info("Parsed {} log rows for device {}. Pushing to Grafana Loki...", streamsDto.getStreams().get(0).getValues().size(), id);

        return grafanaLokiService.pushLogs(streamsDto).then();
    }

    private List<String> parseLogLine(String logLine) {
        Matcher matcher = PATTERN.matcher(logLine);

        if (matcher.find()) {
            String timestampStr = matcher.group(1);  // Дата та час;
            long timestamp = Long.parseLong(timestampStr);
            long nanoseconds = Long.parseLong(matcher.group(3));  // Значення в дужках

            String level = matcher.group(2);
            String logLevel = switch (level) {
                case "I" -> "INFO";
                case "W" -> "WARN";
                case "E" -> "ERROR";
                case "D" -> "DEBUG";
                default -> "WHAT";
            };

            long unixTimestampWithNanos = timestamp * 1_000_000_000 + nanoseconds;

            return List.of(String.valueOf(unixTimestampWithNanos), "[%s] %s".formatted(logLevel, matcher.group(4)));
        } else {
            if (logLine.isBlank()) {
                return List.of();
            }

            long epochTimestamp = Instant.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() * 1_000_000;
            return List.of(String.valueOf(epochTimestamp), "ERROR: %s".formatted(logLine));
        }
    }
}
