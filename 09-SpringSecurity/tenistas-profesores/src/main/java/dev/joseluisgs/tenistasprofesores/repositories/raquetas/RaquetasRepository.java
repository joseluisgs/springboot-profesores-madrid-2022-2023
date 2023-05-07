package dev.joseluisgs.tenistasprofesores.repositories.raquetas;

import dev.joseluisgs.tenistasprofesores.models.raquetas.Raqueta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interfaz para los repositorios de Raquetas con las operaciones CRUD
 * Usamos la interfaz  JpaRepository de Spring Data
 * https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html
 */
public interface RaquetasRepository extends JpaRepository<Raqueta, Long> {
    // Aquí puedes añadir métodos propios de la clase Raqueta
    // Genera automáticamente las consultas
    // https://www.baeldung.com/spring-data-derived-queries
    List<Raqueta> findByMarcaContainsIgnoreCase(String marca);

    // Otra forma es hacerlo con @Query
    @Query("SELECT r FROM Raqueta r WHERE r.modelo LIKE %?1%")
    List<Raqueta> findAllByModeloContainsIgnoreCase(String modelo);

    Optional<Raqueta> findByUuid(UUID uuid);

}