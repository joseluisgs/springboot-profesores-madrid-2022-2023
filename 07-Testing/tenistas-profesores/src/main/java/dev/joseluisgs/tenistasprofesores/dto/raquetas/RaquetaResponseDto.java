package dev.joseluisgs.tenistasprofesores.dto.raquetas;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

/**
 * DTO para devolver una raqueta
 */

@Data
@NoArgsConstructor(force = true) // Para que no me de error en el constructor de jackson
@RequiredArgsConstructor // Para que me cree un constructor con los atributos finales
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
