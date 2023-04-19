package dev.joseluisgs.tenistasprofesores.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Clase Raqueta POJO
 */

// Data: Genera los getters y setters, toString, equals, hashCode y el constructor con todos los parámetros necesarios (finals)
@Data
// AllArgsConstructor: Genera el constructor con todos los parámetros
@AllArgsConstructor
public class Raqueta {
    private final Long id;
    private final UUID uuid;
    private final String marca;
    private String modelo;
    private Double precio;
    private String imagen;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean deleted;
}
