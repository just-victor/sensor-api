package com.veterok.sensorapi.response;

import com.veterok.sensorapi.enums.Commands;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseCommand {
    private int cmd;

    public static ResponseCommand of(Commands command) {
        return new ResponseCommand(command.getCommandId());
    }
}
