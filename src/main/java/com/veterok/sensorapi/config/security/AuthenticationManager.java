package com.veterok.sensorapi.config.security;

import com.veterok.sensorapi.model.UserIdPrincipal;
import com.veterok.sensorapi.service.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtProvider jwtProvider;

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String id, username;
        try {
            username = jwtProvider.getUsernameFromToken(authToken);
            id = jwtProvider.getUserIdFromToken(authToken);
        } catch (ExpiredJwtException ex) {
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token expired"));
        }

        return Mono.just(jwtProvider.validateToken(authToken))
                .filter(valid -> valid)
                .switchIfEmpty(Mono.empty())
                .map(valid -> {
                    Claims claims = jwtProvider.getAllClaimsFromToken(authToken);
                    List<String> rolesMap = claims.get("role", List.class);
                    return new UsernamePasswordAuthenticationToken(new UserIdPrincipal(id, username), null,
                            rolesMap.stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(toList())
                    );
                });
    }
}
