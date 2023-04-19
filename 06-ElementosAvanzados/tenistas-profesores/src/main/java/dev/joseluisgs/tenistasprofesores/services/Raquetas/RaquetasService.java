package dev.joseluisgs.tenistasprofesores.services.Raquetas;

import dev.joseluisgs.tenistasprofesores.models.Raqueta;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RaquetasService {
    List<Raqueta> findAll();

    Optional<Raqueta> findById(Long id);

    List<Raqueta> findAllByMarca(String marca);

    Optional<Raqueta> findByUuid(UUID uuid);

    Raqueta save(Raqueta raqueta);

    void deleteById(Long id);

}
