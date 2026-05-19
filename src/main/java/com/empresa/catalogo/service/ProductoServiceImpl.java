package com.empresa.catalogo.service;

import com.empresa.catalogo.dto.ProductoRequestDTO;
import com.empresa.catalogo.dto.ProductoResponseDTO;
import com.empresa.catalogo.entity.Producto;
import com.empresa.catalogo.exception.RecursoNoEncontradoException;
import com.empresa.catalogo.factory.ProductoFactory;
import com.empresa.catalogo.repository.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación de la lógica de negocio para productos.
 *
 * Principio SRP: esta clase tiene una única responsabilidad —
 * coordinar la lógica de negocio. No accede directamente a la BD
 * (delega en ProductoRepository) ni construye objetos manualmente
 * (delega en ProductoFactory).
 *
 * Logging con SLF4J:
 * - Logger ESTÁTICO y FINAL: se crea una sola vez por clase (patrón correcto).
 * - Placeholders {}: evitan construir el String si el nivel está desactivado.
 *   MAL:  log.info("Creando: " + dto.getNombre())   <- siempre construye el String
 *   BIEN: log.info("Creando: {}", dto.getNombre())   <- solo si INFO está activo
 *
 * Niveles usados:
 * - DEBUG: consultas/búsquedas (detalle para el desarrollador)
 * - INFO:  operaciones exitosas del flujo principal (crear, eliminar)
 * - WARN:  situaciones inusuales pero recuperables (recurso no encontrado)
 */
@Service
public class ProductoServiceImpl implements ProductoService {

    // Logger estático: una instancia por clase, identificada por su nombre completo
    private static final Logger log =
            LoggerFactory.getLogger(ProductoServiceImpl.class);

    private final ProductoRepository repo;
    private final ProductoFactory factory;

    public ProductoServiceImpl(ProductoRepository repo, ProductoFactory factory) {
        this.repo = repo;
        this.factory = factory;
    }

    @Override
    public ProductoResponseDTO crear(ProductoRequestDTO dto) {
        // INFO: inicio de operación importante del flujo principal
        log.info("Creando producto: nombre={}, categoria={}",
                dto.getNombre(), dto.getCategoria());

        Producto producto = factory.toEntity(dto);
        ProductoResponseDTO respuesta = factory.toResponseDTO(repo.save(producto));

        // INFO: confirmación de éxito con el ID generado por la BD
        log.info("Producto creado exitosamente con id={}", respuesta.getId());
        return respuesta;
    }

    @Override
    public ProductoResponseDTO buscarPorId(Long id) {
        // DEBUG: consulta de detalle — útil para el desarrollador, no para producción
        log.debug("Buscando producto con id={}", id);

        Producto producto = repo.findById(id).orElseThrow(() -> {
            // WARN: situación inusual pero esperada — no es un error del sistema
            log.warn("Producto con id={} no encontrado en la base de datos", id);
            return new RecursoNoEncontradoException("Producto", id);
        });

        log.debug("Producto encontrado: id={}, nombre={}", id, producto.getNombre());
        return factory.toResponseDTO(producto);
    }

    @Override
    public List<ProductoResponseDTO> listarActivos() {
        log.debug("Listando todos los productos activos");

        List<ProductoResponseDTO> productos = repo.findByActivoTrue()
                .stream()
                .map(factory::toResponseDTO)
                .toList();

        log.info("Listado de productos activos retornado: {} registros", productos.size());
        return productos;
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando producto con id={}", id);

        // buscarPorId ya registra WARN si no existe antes de lanzar la excepción
        buscarPorId(id);
        repo.deleteById(id);

        log.info("Producto con id={} eliminado correctamente", id);
    }
}
