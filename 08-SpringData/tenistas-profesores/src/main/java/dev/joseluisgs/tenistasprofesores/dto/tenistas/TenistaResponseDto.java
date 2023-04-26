package dev.joseluisgs.tenistasprofesores.dto.tenistas;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import dev.joseluisgs.tenistasprofesores.dto.raquetas.RaquetaResponseDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor(force = true) // Para que no me de error en el constructor de jackson
@RequiredArgsConstructor // Para que me cree un constructor con los atributos finales
@Builder // Para poder usar el patrón Builder
@JacksonXmlRootElement(localName = "tenista") // Para XML
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
