package br.com.healthemed.healthhuman.framework;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Health-Human",
                description = "Api que gerencia agenda e cadastro de doutores",
                version = "v2.0"
                ))
@Configuration
public class OpenApiConfig {

}
