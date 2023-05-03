package dev.joseluisgs.tenistasprofesores.dto.tenistas;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true) // Para que no me de error en el constructor de jackson
public class TenistaRequestDto {
    @NotBlank(message = "El nombre no puede estar vacío")
    private final String nombre;
    @Min(value = 0, message = "El ranking no puede ser negativo")
    private final Integer ranking;
    @NotBlank(message = "El país no puede estar vacío")
    private final String pais;
    private final String imagen;
    @Min(value = 0, message = "El id de la raqueta no puede ser negativo")
    private final Long raquetaId; // Id de la raqueta, puede ser null
}
