package dev.joseluisgs.tenistasprofesores.dto.raquetas;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * DTO para crear una raqueta
 */
@Data
@NoArgsConstructor(force = true) // Para que no me de error en el constructor de jackson
@RequiredArgsConstructor // Para que me cree un constructor con los atributos finales
@Builder // Para poder usar el patrón Builder
public class RaquetaRequestDto {
    @NotBlank(message = "La marca no puede estar vacía")
    private final String marca;
    @NotBlank(message = "El modelo no puede estar vacío")
    private final String modelo;
    @Min(value = 0, message = "El precio no puede ser negativo")
    private final double precio;
    private final String imagen;
}
