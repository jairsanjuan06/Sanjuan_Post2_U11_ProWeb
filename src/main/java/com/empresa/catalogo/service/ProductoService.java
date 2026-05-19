package com.empresa.catalogo.service;

import com.empresa.catalogo.dto.ProductoRequestDTO;
import com.empresa.catalogo.dto.ProductoResponseDTO;

import java.util.List;

/**
 * Interfaz del servicio de productos.
 *
 * Principio DIP: el controlador depende de esta abstracción,
 * no de ProductoServiceImpl. Spring inyecta la implementación concreta
 * en tiempo de ejecución, lo que facilita pruebas y futuras variantes.
 *
 * Principio ISP: la interfaz expone solo los métodos que el controlador
 * realmente necesita, sin operaciones innecesarias.
 */
public interface ProductoService {

    ProductoResponseDTO crear(ProductoRequestDTO dto);

    ProductoResponseDTO buscarPorId(Long id);

    List<ProductoResponseDTO> listarActivos();

    void eliminar(Long id);
}
