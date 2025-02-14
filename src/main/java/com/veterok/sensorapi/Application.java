package com.veterok.sensorapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactivefeign.spring.config.EnableReactiveFeignClients;


@Slf4j
@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableReactiveMongoAuditing
@EnableReactiveFeignClients
@OpenAPIDefinition(info = @Info(title = "Sensor Api", version = "1.0", description = "Documentation APIs v1.0"))
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
