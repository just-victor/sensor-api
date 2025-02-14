package com.veterok.sensorapi.controller.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DataBufferWrapper;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class RequestLoggingFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.info("Request: {} {}", exchange.getRequest().getMethod(), exchange.getRequest().getURI());
        if (exchange.getRequest().getMethod() == HttpMethod.GET) {
            return chain.filter(exchange);
        }

        return DataBufferUtils.join(exchange.getRequest().getBody().switchIfEmpty(Mono.just(new DefaultDataBufferFactory().wrap("".getBytes()))))
                .flatMap(dataBuffer -> {
                    String body = dataBuffer.toString(StandardCharsets.UTF_8);
                    log.info("Request Body: {}", body);
                    DataBuffer cachedDataBuffer = dataBuffer.factory().wrap(body.getBytes(StandardCharsets.UTF_8));

                    ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                        @Override
                        public Flux<DataBuffer> getBody() {
                            // Повторно передаємо тіло
                            return Flux.just(cachedDataBuffer);
                        }
                    };

                    // Продовжуємо обробку з новим запитом
                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                });
    }
}
