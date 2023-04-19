package dev.joseluisgs.tenistasprofesores.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Tenista {
    Long id;
    UUID uuid;
    String nombre;
    Integer ranking;
    String pais;
    String imagen;
    Long raquetaId; // Id de la raqueta, puede ser null
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean deleted = false;
}
