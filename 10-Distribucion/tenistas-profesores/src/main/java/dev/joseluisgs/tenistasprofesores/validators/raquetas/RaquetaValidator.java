package dev.joseluisgs.tenistasprofesores.validators.raquetas;

import dev.joseluisgs.tenistasprofesores.exceptions.raqueta.RaquetaBadRequestException;
import dev.joseluisgs.tenistasprofesores.models.raquetas.Raqueta;
import org.springframework.stereotype.Component;

@Component
public class RaquetaValidator {

    public void validate(Raqueta raqueta) {
        // las distintas condiciones
        if (raqueta.getMarca() == null || raqueta.getMarca().isEmpty()) {
            throw new RaquetaBadRequestException("La marca no puede estar vacía");
        }
        if (raqueta.getModelo() == null || raqueta.getModelo().isEmpty()) {
            throw new RaquetaBadRequestException("El modelo no puede estar vacío");
        }
        if (raqueta.getPrecio() == null || raqueta.getPrecio() < 0) {
            throw new RaquetaBadRequestException("El precio no puede ser negativo");
        }
    }

}
