package com.veterok.sensorapi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Slf4j
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        log.info("Starting application 0002");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .description("Documentation APIs v1.0")
                        .title("Sensor API")
                        .version("1.0")
                        .build())
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.veterok.sensorapi.controller.v1"))
                .paths(PathSelectors.any())
                .build();
    }
}
