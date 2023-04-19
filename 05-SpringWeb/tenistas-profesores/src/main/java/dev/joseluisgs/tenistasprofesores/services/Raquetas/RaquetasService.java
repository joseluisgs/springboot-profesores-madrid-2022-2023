package dev.joseluisgs.tenistasprofesores.services.Raquetas;

import dev.joseluisgs.tenistasprofesores.models.Raqueta;

import java.util.Optional;
import java.util.UUID;

public interface RaquetasService {
    Iterable<Raqueta> findAll();

    Optional<Raqueta> findById(Long id);

    Iterable<Raqueta> findAllByMarca(String marca);

    Optional<Raqueta> findByUuid(UUID uuid);

    Raqueta save(Raqueta raqueta);

    void deleteById(Long id);

}
