package dev.joseluisgs.tenistasprofesores.dto.Raquetas;

import lombok.Data;

/**
 * DTO para crear una raqueta
 */
@Data
public class RaquetaRequestDto {
    private final String marca;
    private final String modelo;
    private final double precio;
    private final String imagen;
}
