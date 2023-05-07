package dev.joseluisgs.tenistasprofesores.exceptions.tenista;

import java.io.Serial;

// Nos permite devolver un estado cuando salta la excepción
public abstract class TenistaException extends RuntimeException {
    // Por si debemos serializar
    @Serial
    private static final long serialVersionUID = 43876691117560211L;

    public TenistaException(String mensaje) {
        super(mensaje);
    }
}