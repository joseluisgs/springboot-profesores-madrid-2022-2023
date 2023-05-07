package dev.joseluisgs.tenistasprofesores.mapper.tenistas;

import dev.joseluisgs.tenistasprofesores.data.tenistas.TenistasFactory;
import dev.joseluisgs.tenistasprofesores.dto.tenistas.TenistaRequestDto;
import dev.joseluisgs.tenistasprofesores.mapper.raquetas.RaquetaMapper;
import dev.joseluisgs.tenistasprofesores.models.tenistas.Tenista;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TenistaMapperTest {
    TenistaMapper tenistaMapper = new TenistaMapper(new RaquetaMapper());
    Map<Long, Tenista> tenistas = TenistasFactory.getTenistasDemoData();

    @Test
    void toResponse() {
        var response = tenistaMapper.toResponse(tenistas.get(1L));
        assertAll(
                () -> assertEquals(1L, response.getId()),
                () -> assertEquals("Rafael Nadal", response.getNombre()),
                () -> assertEquals(1, response.getRanking()),
                () -> assertEquals("España", response.getPais())
        );
    }

    @Test
    void testToResponse() {
        var response = tenistaMapper.toResponse(tenistas.values().stream().toList());
        assertAll(
                () -> assertEquals(4, response.size()),
                () -> assertEquals("Rafael Nadal", response.get(0).getNombre()),
                () -> assertEquals(1, response.get(0).getRanking()),
                () -> assertEquals("España", response.get(0).getPais())
        );
    }

    @Test
    void toModel() {
        var model = new TenistaRequestDto(
                "Demo",
                99,
                "España",
                "https://www.google.es",
                null
        );

        var response = tenistaMapper.toModel(model);
        assertAll(
                () -> assertEquals("Demo", response.getNombre()),
                () -> assertEquals(99, response.getRanking()),
                () -> assertEquals("España", response.getPais()),
                () -> assertEquals("https://www.google.es", response.getImagen())
        );
    }
}