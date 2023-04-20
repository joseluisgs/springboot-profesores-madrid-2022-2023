package dev.joseluisgs.tenistasprofesores.services.Tenistas;

import dev.joseluisgs.tenistasprofesores.models.Tenista;

import java.util.List;
import java.util.UUID;

public interface TenistasService {
    List<Tenista> findAll();

    Tenista findById(Long id);

    Tenista findByUuid(UUID uuid);

    List<Tenista> findAllByNombre(String nombre);

    List<Tenista> findAllByPais(String pais);

    Tenista findByRanking(Integer ranking);

    Tenista save(Tenista tenista);

    Tenista update(Long id, Tenista tenista);

    void deleteById(Long id);

}
