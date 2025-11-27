package com.rabbitmq.registration.user.config;

import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server()
                        .url("http://localhost:8080")
                        .description("Local Server"))
                .addServersItem(new Server()
                        .url("https://api.user.com")
                        .description("Production Server"))
                .info(new Info()
                        .title("User Register API")
                        .version("1.0")
                        .description("API for managing users register")
                        .contact(new Contact()
                                .name("Developer Team")
                                .email("dev@team.com")
                                .url("https://user.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}
