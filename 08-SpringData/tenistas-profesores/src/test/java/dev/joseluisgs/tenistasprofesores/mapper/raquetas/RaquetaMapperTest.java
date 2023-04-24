package dev.joseluisgs.tenistasprofesores.mapper.raquetas;

import dev.joseluisgs.tenistasprofesores.data.raquetas.RaquetasFactory;
import dev.joseluisgs.tenistasprofesores.dto.raquetas.RaquetaRequestDto;
import dev.joseluisgs.tenistasprofesores.models.raquetas.Raqueta;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RaquetaMapperTest {
    RaquetaMapper raquetaMapper = new RaquetaMapper();
    Map<Long, Raqueta> raquetas = RaquetasFactory.getRaquetasDemoData();

    @Test
    void toResponse() {
        var response = raquetaMapper.toResponse(raquetas.get(1L));
        assertAll(
                () -> assertEquals(1L, response.getId()),
                () -> assertEquals("Babolat", response.getMarca()),
                () -> assertEquals("Pure Aero", response.getModelo()),
                () -> assertEquals(199.95, response.getPrecio()),
                () -> assertEquals("https://img.tenniswarehouse-europe.com/watermark/rs.php?path=BPAR22-1.jpg", response.getImagen())
        );
    }

    @Test
    void testToResponse() {
        var response = raquetaMapper.toResponse(raquetas.values().stream().toList());
        assertAll(
                () -> assertEquals(3, response.size()),
                () -> assertEquals("Babolat", response.get(0).getMarca()),
                () -> assertEquals("Pure Aero", response.get(0).getModelo()),
                () -> assertEquals(199.95, response.get(0).getPrecio()),
                () -> assertEquals("https://img.tenniswarehouse-europe.com/watermark/rs.php?path=BPAR22-1.jpg", response.get(0).getImagen())
        );
    }

    @Test
    void toModel() {
        var dto = new RaquetaRequestDto(
                "Babolat",
                "Pure Aero",
                199.95,
                "https://img.tenniswarehouse-europe.com/watermark/rs.php?path=BPAR22-1.jpg"
        );

        var response = raquetaMapper.toModel(dto);

        assertAll(
                () -> assertEquals("Babolat", response.getMarca()),
                () -> assertEquals("Pure Aero", response.getModelo()),
                () -> assertEquals(199.95, response.getPrecio()),
                () -> assertEquals("https://img.tenniswarehouse-europe.com/watermark/rs.php?path=BPAR22-1.jpg", response.getImagen())
        );
    }

    @Test
    void toModelfromRequestDto() {
        var id = 1L;

        var response = raquetaMapper.toModelfromRequestDto(id);

        assertAll(
                () -> assertEquals(1L, response.getId()),
                () -> assertNull(response.getMarca()),
                () -> assertNull(response.getModelo()),
                () -> assertNull(response.getPrecio()),
                () -> assertNull(response.getImagen())
        );

    }
}