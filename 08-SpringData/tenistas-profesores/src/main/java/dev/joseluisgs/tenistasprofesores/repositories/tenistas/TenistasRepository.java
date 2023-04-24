package dev.joseluisgs.tenistasprofesores.repositories.tenistas;

import dev.joseluisgs.tenistasprofesores.models.tenistas.Tenista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interfaz para los repositorios de Raquetas con las operaciones CRUD
 */
public interface TenistasRepository extends JpaRepository<Tenista, Long> {
    List<Tenista> findByNombreContainsIgnoreCase(String nombre);

    List<Tenista> findByPaisContainsIgnoreCase(String pais);

    Optional<Tenista> findByUuid(UUID uuid);

    Optional<Tenista> findByRanking(Integer ranking);

}