package dev.joseluisgs.tenistasprofesores.repositories.raquetas;

import dev.joseluisgs.tenistasprofesores.models.Raqueta;
import dev.joseluisgs.tenistasprofesores.repositories.base.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interfaz para los repositorios de Raquetas con las operaciones CRUD
 */
public interface RaquetasRepository extends CrudRepository<Raqueta, Long> {
    // Aquí puedes añadir métodos propios de la clase Raqueta
    List<Raqueta> findAllByMarca(String marca);

    Optional<Raqueta> findByUuid(UUID uuid);
}