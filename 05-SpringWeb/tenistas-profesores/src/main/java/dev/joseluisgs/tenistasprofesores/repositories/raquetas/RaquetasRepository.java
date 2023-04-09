package dev.joseluisgs.tenistasprofesores.repositories.raquetas;

import dev.joseluisgs.tenistasprofesores.models.Raqueta;
import dev.joseluisgs.tenistasprofesores.repositories.base.CrudRepository;

/**
 * Interfaz para los repositorios de Raquetas con las operaciones CRUD
 */
public interface RaquetasRepository extends CrudRepository<Raqueta, Long> {
    // Aquí puedes añadir métodos propios de la clase Raqueta
    Iterable<Raqueta> findAllByMarca(String marca);
}