package dev.joseluisgs.tenistasprofesores.repositories.tenistas;

import dev.joseluisgs.tenistasprofesores.data.tenistas.TenistasFactory;
import dev.joseluisgs.tenistasprofesores.models.Tenista;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@Slf4j
public class TenistasRepositoryImpl implements TenistasRepository {

    private final Map<Long, Tenista> tenistas = TenistasFactory.getTenistasDemoData();

    @Override
    public List<Tenista> findAll() {
        log.info("findAll");
        return List.copyOf(tenistas.values());
    }

    @Override
    public Optional<Tenista> findById(Long id) {
        log.info("findById");
        return Optional.ofNullable(tenistas.get(id));
    }

    @Override
    public Boolean existsById(Long id) {
        log.info("existsById");
        return tenistas.containsKey(id);
    }

    @Override
    public Tenista save(Tenista tenista) {
        log.info("save");
        // Guardamos la raqueta en el mapa, si no existe la crea
        // Existe?
        if (tenistas.containsKey(tenista.getId())) {
            // Si existe la actualizamos
            return update(tenista);

        } else {
            // Si no existe la creamos
            return create(tenista);
        }
    }

    private Tenista create(Tenista tenista) {
        log.info("create");
        // Lo primero es obtener el último ID
        long lastId = tenistas.keySet().stream().max(Long::compareTo).orElse(0L);
        // Aumentamos el ID en 1
        lastId++;
        // Asignamos el nuevo ID a la raqueta, el UUID y actualizamos el createdAt y el update
        var newTenista = new Tenista(
                lastId,
                tenista.getUuid(),
                tenista.getNombre(),
                tenista.getRanking(),
                tenista.getPais(),
                tenista.getImagen(),
                tenista.getRaqueta(),
                tenista.getCreatedAt(),
                tenista.getUpdatedAt(),
                tenista.getDeleted()
        );
        // Guardamos la raqueta en el mapa
        tenistas.put(lastId, newTenista);
        // Devolvemos la raqueta
        return newTenista;
    }

    private Tenista update(Tenista tenista) {
        log.info("update");
        // Obtenemos la raqueta por su ID
        // Guardamos la raqueta en el mapa
        tenistas.put(tenista.getId(), tenista);
        // Devolvemos la raqueta
        return tenista;
    }


    @Override
    public void deleteById(Long id) {
        log.info("deleteById");
        // Obtenemos la raqueta por su ID
        tenistas.remove(id);

    }

    @Override
    public void delete(Tenista tenista) {
        log.info("delete");
        // Obtenemos la raqueta por su ID
        tenistas.remove(tenista.getId());

    }

    @Override
    public long count() {
        log.info("count");
        return tenistas.size();
    }

    @Override
    public void deleteAll() {
        log.info("deleteAll");
        tenistas.clear();
    }

    @Override
    public List<Tenista> findAllByNombre(String nombre) {
        log.info("findAllByNombre");
        return tenistas.values().stream().
                filter(tenista -> tenista.getNombre().toLowerCase()
                        .contains(nombre.toLowerCase()))
                .toList();
    }

    @Override
    public List<Tenista> findAllByPais(String pais) {
        log.info("findAllByPais");
        return tenistas.values().stream().
                filter(tenista -> tenista.getPais().toLowerCase()
                        .contains(pais.toLowerCase()))
                .toList();
    }

    @Override
    public Optional<Tenista> findByUuid(UUID uuid) {
        log.info("findByUuid");
        return tenistas.values().stream().
                filter(tenista -> tenista.getUuid().equals(uuid))
                .findFirst();
    }

    @Override
    public Optional<Tenista> findByRanking(Integer ranking) {
        log.info("findByRanking");
        return tenistas.values().stream().
                filter(tenista -> tenista.getRanking().equals(ranking))
                .findFirst();
    }
}
