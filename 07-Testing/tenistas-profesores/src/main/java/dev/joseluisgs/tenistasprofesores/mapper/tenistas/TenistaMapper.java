package dev.joseluisgs.tenistasprofesores.mapper.tenistas;

import dev.joseluisgs.tenistasprofesores.dto.tenistas.TenistaRequestDto;
import dev.joseluisgs.tenistasprofesores.dto.tenistas.TenistaResponseDto;
import dev.joseluisgs.tenistasprofesores.mapper.raquetas.RaquetaMapper;
import dev.joseluisgs.tenistasprofesores.models.tenistas.Tenista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class TenistaMapper {

    // Aquí iran los metodos para mapear los DTOs a los modelos y viceversa
    private final RaquetaMapper raquetaMapper;

    @Autowired
    public TenistaMapper(RaquetaMapper raquetaMapper) {
        this.raquetaMapper = raquetaMapper;
    }

    // Mapeamos de modelo a DTO
    public TenistaResponseDto toResponse(Tenista tenista) {
        return new TenistaResponseDto(
                tenista.getId(),
                tenista.getUuid(),
                tenista.getNombre(),
                tenista.getRanking(),
                tenista.getPais(),
                tenista.getImagen(),
                tenista.getRaqueta() != null ? raquetaMapper.toResponse(tenista.getRaqueta()) : null
                // tenista.getCreatedAt(),
                // tenista.getUpdatedAt(),
                // tenista.getDeleted()
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
                dto.getRaquetaId() != null ? raquetaMapper.toModelfromRequestDto(dto.getRaquetaId()) : null,
                LocalDateTime.now(),
                LocalDateTime.now(),
                false
        );
    }
}
