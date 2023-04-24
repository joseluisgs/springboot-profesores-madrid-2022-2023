package dev.joseluisgs.tenistasprofesores.models.tenistas;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.joseluisgs.tenistasprofesores.models.raquetas.Raqueta;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor // Necesario para JPA
@AllArgsConstructor
@Builder // Para poder usar el patrón Builder
@Entity // Para que sea una entidad de JPA
@Table(name = "tenistas") // Para indicar la tabla de la BD, si no coge el nombre de la clase
public class Tenista {
    @Id // Indicamos que es el ID de la tabla
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, updatable = false)
    private UUID uuid;
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    @Min(value = 0, message = "El ranking no puede ser negativo")
    private Integer ranking;
    @NotBlank(message = "El país no puede estar vacío")
    private String pais;
    private String imagen;
    // Relación con la tabla de raquetas, es decir, un tenista tiene una raqueta
    // y una raqueta puede ser de varios tenistas
    @ManyToOne
    @JoinColumn(name = "raqueta_id", referencedColumnName = "id", nullable = true)
    @JsonBackReference // Evitamos recursividad
    private Raqueta raqueta;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    private Boolean deleted;
}
