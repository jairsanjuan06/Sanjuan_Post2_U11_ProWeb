package com.empresa.catalogo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @OpenAPIDefinition define los metadatos globales de la API.
 * springdoc-openapi los usa para generar el encabezado de Swagger UI.
 */
@OpenAPIDefinition(
        info = @Info(
                title = "API Catálogo de Productos",
                version = "1.0",
                description = "API REST para la gestión del catálogo de productos. " +
                        "Implementa principios SOLID, patrones DAO/DTO/Factory y manejo " +
                        "centralizado de excepciones con @RestControllerAdvice."
        )
)
@SpringBootApplication
public class CatalogoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogoApiApplication.class, args);
    }
}
