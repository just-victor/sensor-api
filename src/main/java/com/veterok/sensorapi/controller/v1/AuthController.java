package com.veterok.sensorapi.controller.v1;


import com.veterok.sensorapi.model.User;
import com.veterok.sensorapi.model.dto.AuthRequest;
import com.veterok.sensorapi.model.dto.AuthResponse;
import com.veterok.sensorapi.service.JwtProvider;
import com.veterok.sensorapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> registerUser(@RequestBody AuthRequest authRequest) {
        Mono<User> save = userService.save(authRequest.getLogin(), authRequest.getPassword());
        return save.map(user -> ResponseEntity.ok("OK"))
                .onErrorResume(error -> Mono.just(ResponseEntity.badRequest().body(error.getMessage())));
    }

    @PostMapping("/auth")
    public Mono<AuthResponse> auth(@RequestBody AuthRequest authRequest) {
        return userService.findByLoginAndPassword(authRequest.getLogin(), authRequest.getPassword())
                .map(jwtProvider::generateToken)
                .map(AuthResponse::new);
    }
}