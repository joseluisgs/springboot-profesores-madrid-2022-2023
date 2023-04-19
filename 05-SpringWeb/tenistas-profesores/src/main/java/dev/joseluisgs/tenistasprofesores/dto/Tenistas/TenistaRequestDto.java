package dev.joseluisgs.tenistasprofesores.dto.Tenistas;

import lombok.Data;

@Data
public class TenistaRequestDto {
    private String nombre;
    private Integer ranking;
    private String pais;
    private String imagen;
    private Long raquetaId; // Id de la raqueta, puede ser null
}
