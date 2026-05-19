package com.empresa.catalogo.exception;

/**
 * Excepción de negocio lanzada cuando un recurso solicitado no existe en la BD.
 * Extiende RuntimeException para no obligar a quien la lanza a capturarla
 * (unchecked exception), pero sí será interceptada por GlobalExceptionHandler.
 *
 * El mensaje generado es uniforme: "Producto con id 5 no encontrado."
 */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String recurso, Long id) {
        super(recurso + " con id " + id + " no encontrado.");
    }
}
