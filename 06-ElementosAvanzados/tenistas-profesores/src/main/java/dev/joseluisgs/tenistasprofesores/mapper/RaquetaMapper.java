package dev.joseluisgs.tenistasprofesores.mapper;

import dev.joseluisgs.tenistasprofesores.dto.Raquetas.RaquetaRequestDto;
import dev.joseluisgs.tenistasprofesores.dto.Raquetas.RaquetaResponseDto;
import dev.joseluisgs.tenistasprofesores.models.Raqueta;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class RaquetaMapper {

    // Aquí iran los metodos para mapear los DTOs a los modelos y viceversa

    // Mapeamos de modelo a DTO
    public RaquetaResponseDto toResponse(Raqueta raqueta) {
        return new RaquetaResponseDto(
                raqueta.getId(),
                raqueta.getUuid(),
                raqueta.getMarca(),
                raqueta.getModelo(),
                raqueta.getPrecio(),
                raqueta.getImagen(),
                raqueta.getCreatedAt(),
                raqueta.getUpdatedAt(),
                raqueta.getDeleted()
        );
    }

    // Mapeamos de DTO a modelo
    public List<RaquetaResponseDto> toResponse(List<Raqueta> raquetas) {
        return raquetas.stream()
                .map(this::toResponse)
                .toList();
    }

    // Mapeamos de DTO a modelo
    public Raqueta toModel(RaquetaRequestDto dto) {
        return new Raqueta(
                0L,
                UUID.randomUUID(),
                dto.getMarca(),
                dto.getModelo(),
                dto.getPrecio(),
                dto.getImagen(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                false
        );
    }
}
