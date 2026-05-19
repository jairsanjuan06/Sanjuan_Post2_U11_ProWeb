package com.empresa.catalogo.controller;

import com.empresa.catalogo.dto.ProductoRequestDTO;
import com.empresa.catalogo.dto.ProductoResponseDTO;
import com.empresa.catalogo.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para el recurso /api/productos.
 *
 * @Tag agrupa todos los endpoints de este controlador bajo "Productos" en Swagger UI.
 *
 * Principio SRP: solo maneja HTTP — recibe requests, delega al servicio, retorna respuesta.
 * Principio DIP: depende de la interfaz ProductoService, no de la implementación concreta.
 */
@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Operaciones CRUD del catálogo de productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * GET /api/productos
     */
    @Operation(summary = "Listar todos los productos activos",
               description = "Retorna la lista completa de productos con activo=true")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public List<ProductoResponseDTO> listar() {
        return productoService.listarActivos();
    }

    /**
     * GET /api/productos/{id}
     */
    @Operation(summary = "Obtener producto por ID",
               description = "Busca un producto por su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ProductoResponseDTO obtener(
            @Parameter(description = "Identificador único del producto", example = "1")
            @PathVariable Long id) {
        return productoService.buscarPorId(id);
    }

    /**
     * POST /api/productos
     */
    @Operation(summary = "Crear un nuevo producto",
               description = "Registra un producto en el catálogo. El campo 'activo' se establece en true por defecto.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (nombre vacío o precio negativo)")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductoResponseDTO crear(@Valid @RequestBody ProductoRequestDTO dto) {
        return productoService.crear(dto);
    }

    /**
     * DELETE /api/productos/{id}
     */
    @Operation(summary = "Eliminar producto por ID",
               description = "Elimina permanentemente un producto del catálogo")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(
            @Parameter(description = "Identificador único del producto a eliminar", example = "1")
            @PathVariable Long id) {
        productoService.eliminar(id);
    }
}
