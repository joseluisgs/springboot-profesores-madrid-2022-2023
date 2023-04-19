package dev.joseluisgs.tenistasprofesores.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Tenista {
    private Long id;
    private UUID uuid;
    private String nombre;
    private Integer ranking;
    private String pais;
    private String imagen;
    private Long raquetaId; // Id de la raqueta, puede ser null
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean deleted;
}
