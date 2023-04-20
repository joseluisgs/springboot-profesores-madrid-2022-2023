package dev.joseluisgs.tenistasprofesores.validators;

import dev.joseluisgs.tenistasprofesores.models.Tenista;
import org.springframework.stereotype.Component;

@Component
public class TenistaValidator {
    public void isValid(Tenista tenista) {
        if (tenista.getNombre() == null || tenista.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (tenista.getRanking() == null || tenista.getRanking() < 0) {
            throw new IllegalArgumentException("El ranking no puede ser negativo");
        }
        if (tenista.getPais() == null || tenista.getPais().isEmpty()) {
            throw new IllegalArgumentException("El país no puede estar vacío");
        }
    }
}
