package com.veterok.sensorapi.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommandStatus {
    PENDING(0), DONE(1);

    private final int statusId;
}
