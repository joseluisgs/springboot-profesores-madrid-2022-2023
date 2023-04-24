package dev.joseluisgs.tenistasprofesores.models.tenistas;

import dev.joseluisgs.tenistasprofesores.models.raquetas.Raqueta;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder // Para poder usar el patrón Builder
public class Tenista {
    @Id // Indicamos que es el ID de la tabla
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;
    private UUID uuid;
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    @Min(value = 0, message = "El ranking no puede ser negativo")
    private Integer ranking;
    @NotBlank(message = "El país no puede estar vacío")
    private String pais;
    private String imagen;
    // @NotNull(message = "El id de la raqueta no puede ser nulo") --> Si puede estar vacío
    private Raqueta raqueta; // Referencia a la raqueta, puede ser null
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean deleted;
}
