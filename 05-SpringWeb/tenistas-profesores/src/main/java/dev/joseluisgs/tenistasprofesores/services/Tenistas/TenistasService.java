package dev.joseluisgs.tenistasprofesores.services.Tenistas;

import dev.joseluisgs.tenistasprofesores.models.Tenista;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TenistasService {
    List<Tenista> findAll();

    Optional<Tenista> findById(Long id);

    Optional<Tenista> findByUuid(UUID uuid);

    List<Tenista> findAllByNombre(String nombre);

    List<Tenista> findAllByPais(String pais);

    Optional<Tenista> findByRanking(Integer ranking);

    Tenista save(Tenista raqueta);

    void deleteById(Long id);

}
