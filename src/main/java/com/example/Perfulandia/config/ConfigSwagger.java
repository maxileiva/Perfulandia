package com.example.Perfulandia.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigSwagger {

    @Bean
    public OpenAPI deliveryOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Pedidos - perfulandia")
                        .description("API Web para gestionar pedidos de perfulandia")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Soporte perfulandia")
                                .url("https://www.perfulandia.com")
                                .email("soporte@perfulandia.com")));
    }
}