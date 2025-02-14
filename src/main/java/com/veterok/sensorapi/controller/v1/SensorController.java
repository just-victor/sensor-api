package com.veterok.sensorapi.controller.v1;


import com.veterok.sensorapi.model.Sensor;
import com.veterok.sensorapi.model.light.SensorLightDto;
import com.veterok.sensorapi.model.light.StateLightDto;
import com.veterok.sensorapi.response.ResponseCommand;
import com.veterok.sensorapi.service.LogService;
import com.veterok.sensorapi.service.SensorCommandService;
import com.veterok.sensorapi.service.SensorLightService;
import com.veterok.sensorapi.service.SensorLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.veterok.sensorapi.controller.v1.LogParser.parseLogs;
import static com.veterok.sensorapi.utils.DateUtils.SENSOR_FORMATTER;

@Slf4j
@RestController
@RequestMapping("/sensors")
@RequiredArgsConstructor
public class SensorController {
    private final SensorLightService sensorService;
    private final LogService logService;
    private final SensorLogService sensorLogService;
    private final SensorCommandService sensorCommandService;


    @GetMapping("/{id}")
    public Mono<SensorLightDto> getSensor(@PathVariable UUID id) {
        return sensorService.getSensorSettings(id);
    }

    @GetMapping("")
    public Flux<Sensor> getSensors() {
        return sensorService.getSensors();
    }

    @PostMapping("/{id}/register")
    public ResponseEntity<String> registerSensor(@PathVariable UUID id) {
        log.info("Registering sensor {}", id);
        sensorService.registerSensor(id);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(SENSOR_FORMATTER.format(Instant.now().atZone(ZoneId.systemDefault())));
    }

    @PostMapping("/{id}/state")
    public ResponseEntity<ResponseCommand> addState(@PathVariable UUID id, @RequestBody StateLightDto stateDto) {
        log.info("Adding state for sensor {}", id);
        sensorService.addState(id, stateDto);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(sensorCommandService.getCommand(id));
    }

    @PostMapping(value = "/{id}/logs")
    @ResponseBody
    public ResponseEntity<ResponseCommand> sensorLog(@PathVariable UUID id, @RequestBody String logBuffer) {
        log.info("Adding log for sensor {}, buffer length: {}", id, logBuffer.length());
        sensorLogService.pushLogs(id, logBuffer)
                .subscribe();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(sensorCommandService.getCommand(id));
    }
}
