package com.veterok.sensorapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.security.Principal;

@Data
@AllArgsConstructor
public class UserIdPrincipal implements Principal {
    private String id;
    private String name;
}
