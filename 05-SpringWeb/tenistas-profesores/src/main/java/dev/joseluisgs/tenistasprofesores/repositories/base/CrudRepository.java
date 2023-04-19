package dev.joseluisgs.tenistasprofesores.repositories.base;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz base para los repositorios con las operaciones CRUD
 *
 * @param <T>  Tipo de dato
 * @param <ID> Tipo de dato del ID
 */
public interface CrudRepository<T, ID> {
    List<T> findAll();

    Optional<T> findById(ID id);

    Boolean existsById(ID id);

    T save(T t);

    void deleteById(ID id);

    void delete(T t);

    long count();

    void deleteAll();
}