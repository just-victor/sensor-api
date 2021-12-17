package com.veterok.sensorapi.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Commands {
    ALL_IS_OK(0),
    SEND_COORDINATES_TO_SERVER(1),
    UPDATE_SETTINGS_FROM_SERVER(2);

    private final int commandId;
}
