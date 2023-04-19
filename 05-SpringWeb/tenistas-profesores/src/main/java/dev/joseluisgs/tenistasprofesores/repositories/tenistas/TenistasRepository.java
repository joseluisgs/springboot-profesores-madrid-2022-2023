package dev.joseluisgs.tenistasprofesores.repositories.tenistas;

import dev.joseluisgs.tenistasprofesores.models.Tenista;
import dev.joseluisgs.tenistasprofesores.repositories.base.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interfaz para los repositorios de Raquetas con las operaciones CRUD
 */
public interface TenistasRepository extends CrudRepository<Tenista, Long> {
    List<Tenista> findAllByNombre(String nombre);

    List<Tenista> findAllByPais(String pais);

    Optional<Tenista> findByUuid(UUID uuid);

    Optional<Tenista> findByRanking(Integer ranking);

}