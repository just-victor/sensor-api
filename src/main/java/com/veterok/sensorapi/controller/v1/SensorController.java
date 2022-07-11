package com.veterok.sensorapi.controller.v1;


import com.veterok.sensorapi.model.Sensor;
import com.veterok.sensorapi.model.light.SensorLightDto;
import com.veterok.sensorapi.model.light.StateLightDto;
import com.veterok.sensorapi.response.ResponseCommand;
import com.veterok.sensorapi.service.LogService;
import com.veterok.sensorapi.service.SensorCommandService;
import com.veterok.sensorapi.service.SensorLightService;
import lombok.RequiredArgsConstructor;
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

import java.util.UUID;

@RestController
@RequestMapping("/sensors")
@RequiredArgsConstructor
public class SensorController {
    private final SensorLightService sensorService;
    private final LogService logService;
    private final SensorCommandService sensorCommandService;


    @GetMapping("/{id}")
    public Mono<SensorLightDto> getSensor(@PathVariable UUID id) {
        return sensorService.getSensorSettings(id);
    }

    @GetMapping("")
    public Flux<Sensor> getSensors() {
        return sensorService.getSensors();
    }

    @PostMapping("/{id}/coordinates")
    public ResponseEntity<ResponseCommand> updateCoordinates(@PathVariable UUID id, @RequestBody SensorLightDto sensorLightDto) {
        sensorService.updateCoordinates(id, sensorLightDto);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(sensorCommandService.getCommand(id));
    }

    @PostMapping("/{id}/state")
    public ResponseEntity<ResponseCommand> addState(@PathVariable UUID id, @RequestBody StateLightDto stateDto) {
        sensorService.addState(id, stateDto);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(sensorCommandService.getCommand(id));
    }

    @PostMapping(value = "/{id}/log")
    @ResponseBody
    public ResponseEntity<ResponseCommand> sensorLog(@PathVariable UUID id, @RequestBody String logString) {
        logService.logBulkString(id, logString);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(sensorCommandService.getCommand(id));
    }
}
