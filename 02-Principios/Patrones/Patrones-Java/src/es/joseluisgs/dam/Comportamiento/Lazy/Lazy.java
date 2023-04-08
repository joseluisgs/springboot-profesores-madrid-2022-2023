package es.joseluisgs.dam.Comportamiento.Lazy;

import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * Clase Lazy, Funcional. Vamos a hacer uso de Supplier y como es una interfaz podemos programarla
 * de forma funcional.
 * @param <T>
 */
public final class Lazy<T> {
    private T value;

    public T get(Supplier<T> supplier) {
        final T result = value; // Le metemos el valor de la variable value a una variable temporal
        // Si el valor es nulo, lo inicializamos
        return result == null ? initValue(supplier) : result;
    }
    private T initValue(Supplier<T> supplier) {
        // Si el valor es nulo, lo inicializamos
        if (value == null) {
            System.out.println("Inicializando valor...");
            value = requireNonNull(supplier.get());
        }
        return value;
    }
}