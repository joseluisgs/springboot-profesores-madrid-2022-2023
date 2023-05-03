package dev.joseluisgs.tenistasprofesores.dto.tenistas;

import dev.joseluisgs.tenistasprofesores.dto.raquetas.RaquetaResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true) // Para que no me de error en el constructor de jackson
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
