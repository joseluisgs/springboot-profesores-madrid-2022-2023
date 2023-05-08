package dev.joseluisgs.tenistasprofesores.services.raquetas;

import dev.joseluisgs.tenistasprofesores.models.raquetas.Raqueta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface RaquetasService {
    List<Raqueta> findAll();

    Raqueta findById(Long id);

    List<Raqueta> findAllByMarca(String marca);

    Raqueta findByUuid(UUID uuid);

    Raqueta save(Raqueta raqueta);

    Raqueta update(Long id, Raqueta raqueta);

    void deleteById(Long id);

    Page<Raqueta> findAllUsingPage(Pageable pageable);


}
