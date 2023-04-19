package dev.joseluisgs.tenistasprofesores.dto.Tenistas;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TenistaResponseDto {
    private final Long id;
    private final UUID uuid;
    private final String nombre;
    private final Integer ranking;
    private final String pais;
    private final String imagen;
    private final Long raquetaId; // Id de la raqueta, puede ser null
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Boolean deleted;
}
