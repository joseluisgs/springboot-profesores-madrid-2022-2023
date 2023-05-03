package dev.joseluisgs.tenistasprofesores.validators.tenistas;

import dev.joseluisgs.tenistasprofesores.exceptions.tenista.TenistaBadRequestException;
import dev.joseluisgs.tenistasprofesores.models.tenistas.Tenista;
import org.springframework.stereotype.Component;

@Component
public class TenistaValidator {
    public void validate(Tenista tenista) {
        if (tenista.getNombre() == null || tenista.getNombre().isEmpty()) {
            throw new TenistaBadRequestException("El nombre no puede estar vacío");
        }
        if (tenista.getRanking() == null || tenista.getRanking() < 0) {
            throw new TenistaBadRequestException("El ranking no puede ser negativo ni estar vacío");
        }
        if (tenista.getPais() == null || tenista.getPais().isEmpty()) {
            throw new TenistaBadRequestException("El país no puede estar vacío");
        }
    }
}
