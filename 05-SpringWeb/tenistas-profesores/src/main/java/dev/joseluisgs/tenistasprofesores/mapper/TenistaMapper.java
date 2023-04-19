package dev.joseluisgs.tenistasprofesores.mapper;

import dev.joseluisgs.tenistasprofesores.dto.Tenistas.TenistaRequestDto;
import dev.joseluisgs.tenistasprofesores.dto.Tenistas.TenistaResponseDto;
import dev.joseluisgs.tenistasprofesores.models.Tenista;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class TenistaMapper {

    // Aquí iran los metodos para mapear los DTOs a los modelos y viceversa

    // Mapeamos de modelo a DTO
    public TenistaResponseDto toResponse(Tenista tenista) {
        return new TenistaResponseDto(
                tenista.getId(),
                tenista.getUuid(),
                tenista.getNombre(),
                tenista.getRanking(),
                tenista.getPais(),
                tenista.getImagen(),
                tenista.getRaquetaId(),
                tenista.getCreatedAt(),
                tenista.getUpdatedAt(),
                tenista.getDeleted()
        );
    }

    // Mapeamos de DTO a modelo
    public List<TenistaResponseDto> toResponse(List<Tenista> tenistas) {
        return tenistas.stream()
                .map(this::toResponse)
                .toList();
    }

    // Mapeamos de DTO a modelo
    public Tenista toModel(TenistaRequestDto dto) {
        return new Tenista(
                0L,
                UUID.randomUUID(),
                dto.getNombre(),
                dto.getRanking(),
                dto.getPais(),
                dto.getImagen(),
                dto.getRaquetaId(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                false
        );
    }
}
