package br.com.healthemed.healthhuman.framework;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;

@OpenAPIDefinition(
        info = @Info(
                title = "Health-Human",
                description = "Api que gerencia agenda e cadastro de doutores",
                version = "v2.0"
                ),
        servers = @Server(
                url = "host/api",
                description = "Descrição do Server",
                variables = {
                        @ServerVariable(name = "serverUrl", defaultValue = "localhost"),
                        @ServerVariable(name = "serverHttpPort", defaultValue = "8080")
                }))
@Configuration
public class OpenApiConfig {

}
