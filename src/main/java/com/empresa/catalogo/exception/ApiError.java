package com.empresa.catalogo.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Objeto de respuesta de error estandarizado para toda la API.
 * Todos los errores retornan este mismo formato JSON, lo que facilita
 * el trabajo del frontend y las herramientas de monitoreo.
 *
 * Ejemplo de respuesta 404:
 * {
 *   "status": 404,
 *   "error": "Not Found",
 *   "mensaje": "Producto con id 999 no encontrado.",
 *   "timestamp": "2026-05-18T10:30:00",
 *   "path": "/api/productos/999"
 * }
 */
public class ApiError {

    private int status;
    private String error;
    private String mensaje;
    private String timestamp;
    private String path;

    public ApiError(int status, String error, String mensaje, String path) {
        this.status = status;
        this.error = error;
        this.mensaje = mensaje;
        this.timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        this.path = path;
    }

    // ── Getters ──────────────────────────────────────────────────

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }
}
