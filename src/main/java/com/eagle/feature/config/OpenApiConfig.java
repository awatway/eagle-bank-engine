package com.eagle.feature.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI eagleBankOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Eagle Bank API")
                        .description("REST API for managing users, accounts, and transactions")
                        .version("v1.0")
                        .license(new License().name("Apache 2.0")));
    }
}

