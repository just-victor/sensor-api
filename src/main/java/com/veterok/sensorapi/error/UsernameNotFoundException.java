package com.veterok.sensorapi.error;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UsernameNotFoundException extends AuthenticationException {
    public UsernameNotFoundException(String msg) {
        super("User '" + msg + "' not found");
    }
}
