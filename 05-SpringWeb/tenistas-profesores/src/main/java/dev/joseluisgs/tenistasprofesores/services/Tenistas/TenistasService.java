package dev.joseluisgs.tenistasprofesores.services.Tenistas;

import dev.joseluisgs.tenistasprofesores.models.Tenista;

import java.util.Optional;
import java.util.UUID;

public interface TenistasService {
    Iterable<Tenista> findAll();

    Optional<Tenista> findById(Long id);

    Optional<Tenista> findByUuid(UUID uuid);

    Iterable<Tenista> findAllByNombre(String nombre);

    Iterable<Tenista> findAllByPais(String pais);

    Optional<Tenista> findByRanking(Integer ranking);

    Tenista save(Tenista raqueta);

    void deleteById(Long id);

}
