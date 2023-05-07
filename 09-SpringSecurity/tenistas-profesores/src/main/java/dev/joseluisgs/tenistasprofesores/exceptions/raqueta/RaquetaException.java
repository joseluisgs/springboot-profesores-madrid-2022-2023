package dev.joseluisgs.tenistasprofesores.exceptions.raqueta;

import java.io.Serial;

// Nos permite devolver un estado cuando salta la excepción
public abstract class RaquetaException extends RuntimeException {
    // Por si debemos serializar
    @Serial
    private static final long serialVersionUID = 43876691117560211L;

    public RaquetaException(String mensaje) {
        super(mensaje);
    }
}