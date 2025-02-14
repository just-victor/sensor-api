package com.veterok.sensorapi.feign.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StreamsDto {
    private List<StreamDto> streams = List.of(new StreamDto());

    @JsonIgnore
    public void setDeviceId(String deviceId) {
        this.streams.get(0).getStream().setDeviceId(deviceId);
    }
    @JsonIgnore
    public void addLog(String timestamp, String message) {
        this.streams.get(0).addLog(timestamp, message);
    }

    public void addLog(List<String> logRow) {
        this.streams.get(0).values.add(logRow);
    }

    @Data
    public static class StreamDto {
        private LabelDto stream = new LabelDto();
        private List<List<String>> values = new ArrayList<>();

        @JsonIgnore
        public void addLog(String timestamp, String message) {
            this.values.add(List.of(timestamp, message));
        }
    }

    @Data
    public static class LabelDto {
        private String deviceId;
    }
}
