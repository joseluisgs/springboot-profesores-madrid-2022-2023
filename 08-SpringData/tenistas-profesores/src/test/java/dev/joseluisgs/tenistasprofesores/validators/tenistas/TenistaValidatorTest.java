package dev.joseluisgs.tenistasprofesores.validators.tenistas;

import dev.joseluisgs.tenistasprofesores.data.tenistas.TenistasFactory;
import dev.joseluisgs.tenistasprofesores.models.tenistas.Tenista;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TenistaValidatorTest {
    private final Tenista tenista = TenistasFactory.getTenistasDemoData().get(1L);
    private final TenistaValidator validator = new TenistaValidator();

    @Test
    void validate() {
        tenista.setNombre("Rafael Nadal");
        tenista.setPais("España");
        tenista.setRanking(1);
        validator.validate(tenista);
    }

    @Test
    void validateNombreFails() {
        tenista.setNombre("");
        tenista.setPais("España");
        tenista.setRanking(1);
        // Capturamos la excepción
        var res = assertThrows(ResponseStatusException.class, () -> {
            validator.validate(tenista);
        });
        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("El nombre no puede estar vacío"));
    }

    @Test
    void validatePaisFails() {
        tenista.setNombre("Rafael Nadal");
        tenista.setPais("");
        tenista.setRanking(1);
        // Capturamos la excepción
        var res = assertThrows(ResponseStatusException.class, () -> {
            validator.validate(tenista);
        });
        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("El país no puede estar vacío"));
    }

    @Test
    void validateRankingFails() {
        tenista.setNombre("Rafael Nadal");
        tenista.setPais("España");
        tenista.setRanking(-1);
        // Capturamos la excepción
        var res = assertThrows(ResponseStatusException.class, () -> {
            validator.validate(tenista);
        });
        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("El ranking no puede ser negativo"));
    }
}