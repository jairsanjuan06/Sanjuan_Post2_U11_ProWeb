package com.empresa.catalogo.factory;

import com.empresa.catalogo.dto.ProductoRequestDTO;
import com.empresa.catalogo.dto.ProductoResponseDTO;
import com.empresa.catalogo.entity.Producto;
import org.springframework.stereotype.Component;

/**
 * Patrón Factory — centraliza toda la lógica de construcción y conversión
 * de objetos Producto. Evita duplicar la lógica de mapeo en múltiples capas.
 *
 * Principio SRP: esta clase tiene una única razón para cambiar,
 * que es cuando cambia la estructura de Producto o sus DTOs.
 */
@Component
public class ProductoFactory {

    /**
     * Convierte un DTO de entrada en una entidad JPA lista para persistir.
     */
    public Producto toEntity(ProductoRequestDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setCategoria(dto.getCategoria());
        return producto;
    }

    /**
     * Convierte una entidad JPA en un DTO de salida para el cliente.
     * El campo 'activo' no se expone — el cliente no necesita ese detalle interno.
     */
    public ProductoResponseDTO toResponseDTO(Producto producto) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        dto.setCategoria(producto.getCategoria());
        return dto;
    }
}
