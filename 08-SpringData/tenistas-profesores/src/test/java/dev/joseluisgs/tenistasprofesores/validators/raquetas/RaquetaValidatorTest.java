package dev.joseluisgs.tenistasprofesores.validators.raquetas;

import dev.joseluisgs.tenistasprofesores.data.raquetas.RaquetasFactory;
import dev.joseluisgs.tenistasprofesores.models.raquetas.Raqueta;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class RaquetaValidatorTest {

    private final Raqueta raqueta = RaquetasFactory.getRaquetasDemoData().get(1L);
    private final RaquetaValidator validator = new RaquetaValidator();

    @Test
    void validateOk() {
        // Creamos una raqueta válida
        raqueta.setMarca("Babolat");
        raqueta.setModelo("Pure Drive");
        raqueta.setPrecio(200.0);
        // La validamos
        validator.validate(raqueta);

    }

    @Test
    void validateFailsMarca() {
        // Creamos una raqueta válida
        raqueta.setMarca("");
        raqueta.setModelo("Pure Drive");
        raqueta.setPrecio(200.0);
        // La validamos
        // Comprobamos que da una excepción
        var res = assertThrows(ResponseStatusException.class, () -> {
            validator.validate(raqueta);
        });
        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("La marca no puede estar vacía"));
    }

    @Test
    void validateFailsModelo() {
        // Creamos una raqueta válida
        raqueta.setMarca("Babolat");
        raqueta.setModelo("");
        raqueta.setPrecio(200.0);
        // La validamos
        // Comprobamos que da una excepción
        var res = assertThrows(ResponseStatusException.class, () -> {
            validator.validate(raqueta);
        });
        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("El modelo no puede estar vacío"));
    }

    @Test
    void validateFailsPrecio() {
        // Creamos una raqueta válida
        raqueta.setMarca("Babolat");
        raqueta.setModelo("Pure Drive");
        raqueta.setPrecio(-1.0);
        // La validamos
        // Comprobamos que da una excepción
        var res = assertThrows(ResponseStatusException.class, () -> {
            validator.validate(raqueta);
        });
        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("El precio no puede ser negativo"));
    }
}