package dev.joseluisgs.tenistasprofesores.dto.Raquetas;

import lombok.Data;

import java.util.UUID;

/**
 * DTO para devolver una raqueta
 */

@Data
public class RaquetaResponseDto {
    private final Long id;
    private final UUID uuid;
    private final String marca;
    private final String modelo;
    private final Double precio;
    private final String imagen;
    // Si queremos podemos borrar los metadatos para no sacarlos
    // private final LocalDateTime createdAt;
    // private final LocalDateTime updatedAt;
    // private final Boolean deleted;
}
