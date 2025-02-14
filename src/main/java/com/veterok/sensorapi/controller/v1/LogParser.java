package com.veterok.sensorapi.controller.v1;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.veterok.sensorapi.utils.DateUtils.SENSOR_FORMATTER;

public class LogParser {
    // Коди спеціальних символів
    private static final byte STX = 0x02; // Початок тексту
    private static final byte ETX = 0x03; // Кінець тексту
    private static final byte LF = 0x0A;  // Розділювач параметрів

    public static List<LogEntry> parseLogs(String logBuffer) {
        List<LogEntry> logs = new ArrayList<>();



        return logs;
    }



    @RequiredArgsConstructor
    public static class LogEntry {
        public final Long timestamp;
        public final String level;
        public final String message;

        @Override
        public String toString() {
            return "%s [%s] %s".formatted(SENSOR_FORMATTER.format(LocalDateTime.ofEpochSecond(timestamp / 1_000_000_000, 0, ZoneOffset.UTC)), level, message);
        }
    }
}

