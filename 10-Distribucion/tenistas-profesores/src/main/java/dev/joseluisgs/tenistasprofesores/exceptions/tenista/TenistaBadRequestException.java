package dev.joseluisgs.tenistasprofesores.exceptions.tenista;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

// Nos permite devolver un estado cuando salta la excepción
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TenistaBadRequestException extends TenistaException {
    // Por si debemos serializar
    @Serial
    private static final long serialVersionUID = 43876691117560211L;

    public TenistaBadRequestException(String mensaje) {
        super(mensaje);
    }
}