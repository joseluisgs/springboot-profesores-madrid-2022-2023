package dev.joseluisgs.tenistasprofesores.dto.Tenistas;

import dev.joseluisgs.tenistasprofesores.dto.Raquetas.RaquetaResponseDto;
import lombok.Data;

import java.util.UUID;

@Data
public class TenistaResponseDto {
    private final Long id;
    private final UUID uuid;
    private final String nombre;
    private final Integer ranking;
    private final String pais;
    private final String imagen;
    private final RaquetaResponseDto raqueta; // Referencia a la raquetaDto, puede ser null
    // Si queremos podemos borrar los metadatos para no sacarlos
    // private final LocalDateTime createdAt;
    // private final LocalDateTime updatedAt;
    // private final Boolean deleted;
}
