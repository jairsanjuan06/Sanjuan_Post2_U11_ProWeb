package com.empresa.catalogo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * DTO de entrada: transporta los datos que el cliente envía a la API.
 *
 * @Schema documenta cada campo en Swagger UI con descripción y ejemplo concreto,
 * lo que permite al equipo frontend entender el contrato sin leer el código fuente.
 */
@Schema(description = "Datos requeridos para crear o actualizar un producto")
public class ProductoRequestDTO {

    @Schema(
            description = "Nombre del producto",
            example = "Laptop HP ProBook 450"
    )
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Schema(
            description = "Precio del producto en pesos colombianos",
            example = "3500000.00"
    )
    @Positive(message = "El precio debe ser mayor a cero")
    private Double precio;

    @Schema(
            description = "Categoría del producto",
            allowableValues = {"ELECTRONICA", "PAPELERIA", "HOGAR"},
            example = "ELECTRONICA"
    )
    private String categoria;

    // ── Constructores ────────────────────────────────────────────

    public ProductoRequestDTO() {}

    public ProductoRequestDTO(String nombre, Double precio, String categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    // ── Getters y Setters ────────────────────────────────────────

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
