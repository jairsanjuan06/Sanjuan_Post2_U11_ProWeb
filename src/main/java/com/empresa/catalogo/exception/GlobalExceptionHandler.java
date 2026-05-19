package com.empresa.catalogo.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * Manejador global de excepciones para toda la API.
 *
 * @RestControllerAdvice intercepta las excepciones lanzadas por cualquier
 * controlador y las transforma en respuestas HTTP estandarizadas (ApiError).
 *
 * Principio SRP: este componente tiene una única responsabilidad —
 * centralizar el manejo de errores. Los controladores no necesitan
 * bloques try/catch individuales.
 *
 * Beneficios clave:
 * - Formato de error uniforme en toda la API (JSON en lugar de HTML o texto)
 * - Códigos HTTP correctos: 404, 400, 500 según el tipo de error
 * - Sin stack traces expuestos al cliente
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura: recurso no encontrado en la BD.
     * Respuesta: 404 Not Found con el mensaje descriptivo del recurso.
     */
    @ExceptionHandler(RecursoNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(RecursoNoEncontradoException ex,
                                   HttpServletRequest req) {
        return new ApiError(404, "Not Found", ex.getMessage(), req.getRequestURI());
    }

    /**
     * Captura: validación fallida en el body del request (@Valid en el controlador).
     * Respuesta: 400 Bad Request con todos los errores de campo concatenados.
     * Ejemplo: "nombre: El nombre es obligatorio; precio: El precio debe ser mayor a cero"
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidation(MethodArgumentNotValidException ex,
                                     HttpServletRequest req) {
        String errores = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return new ApiError(400, "Bad Request", errores, req.getRequestURI());
    }

    /**
     * Captura: cualquier excepción no controlada explícitamente.
     * Respuesta: 500 Internal Server Error con mensaje genérico (sin detalles internos).
     * Nota: en producción, el detalle real debe ir al log, no al cliente.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleGeneral(Exception ex, HttpServletRequest req) {
        return new ApiError(
                500,
                "Internal Server Error",
                "Error inesperado. Contactar soporte.",
                req.getRequestURI()
        );
    }
}
