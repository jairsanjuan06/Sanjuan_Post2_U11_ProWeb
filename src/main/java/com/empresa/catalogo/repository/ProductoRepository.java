package com.empresa.catalogo.repository;

import com.empresa.catalogo.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Patrón DAO implementado con Spring Data JPA.
 * JpaRepository proporciona CRUD completo sin código extra.
 *
 * Principio DIP: las capas superiores (Service) dependen de esta
 * abstracción (interfaz), no de una implementación concreta de acceso a BD.
 */
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    /**
     * Spring Data genera automáticamente la consulta:
     * SELECT * FROM producto WHERE activo = true
     */
    List<Producto> findByActivoTrue();
}
