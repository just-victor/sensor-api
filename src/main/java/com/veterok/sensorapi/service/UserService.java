package com.veterok.sensorapi.service;

import com.veterok.sensorapi.error.BadCredentialsException;
import com.veterok.sensorapi.error.UsernameNotFoundException;
import com.veterok.sensorapi.model.User;
import com.veterok.sensorapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<User> save(String login, String password) {
        return userRepository.findByLogin(login)
                .flatMap((it) -> Mono.error(new RuntimeException("User already exists")))
                .switchIfEmpty(Mono.just(new User(login, passwordEncoder.encode(password))))
                .flatMap(it -> userRepository.save((User) it));
    }

    public Flux<User> getAll() {
        return userRepository.findAll();
    }
    public Mono<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public Mono<User> findByLoginAndPassword(String login, String password) {
        return findByLogin(login)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException(login)))
                .filter(u -> passwordEncoder.matches(password, u.getPassword()))
                .switchIfEmpty(Mono.error(new BadCredentialsException()));
    }
}
