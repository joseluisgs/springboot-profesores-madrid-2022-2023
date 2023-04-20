package dev.joseluisgs.tenistasprofesores.validators;

import dev.joseluisgs.tenistasprofesores.models.Tenista;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class TenistaValidator {
    public void validate(Tenista tenista) {
        if (tenista.getNombre() == null || tenista.getNombre().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El nombre no puede estar vacío");
        }
        if (tenista.getRanking() == null || tenista.getRanking() < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El ranking no puede ser negativo");
        }
        if (tenista.getPais() == null || tenista.getPais().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El país no puede estar vacío");
        }
    }
}
