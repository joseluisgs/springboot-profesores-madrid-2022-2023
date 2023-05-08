package dev.joseluisgs.tenistasprofesores.services.tenistas;

import dev.joseluisgs.tenistasprofesores.exceptions.tenista.TenistaBadRequestException;
import dev.joseluisgs.tenistasprofesores.exceptions.tenista.TenistaNotFoundException;
import dev.joseluisgs.tenistasprofesores.models.raquetas.Raqueta;
import dev.joseluisgs.tenistasprofesores.models.tenistas.Tenista;
import dev.joseluisgs.tenistasprofesores.repositories.raquetas.RaquetasRepository;
import dev.joseluisgs.tenistasprofesores.repositories.tenistas.TenistasRepository;
import dev.joseluisgs.tenistasprofesores.validators.tenistas.TenistaValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@CacheConfig(cacheNames = {"tenistas"}) // Nombre del caché
public class TenistasServiceImpl implements TenistasService {
    // Mis dependencias
    private final TenistasRepository tenistasRepository;
    private final RaquetasRepository raquetasRepository;
    private final TenistaValidator tenistaValidator;

    // Inyectamos los repositorios
    @Autowired
    public TenistasServiceImpl(TenistasRepository tenistasRepository, RaquetasRepository raquetasService, TenistaValidator tenistaValidator) {
        this.tenistasRepository = tenistasRepository;
        this.raquetasRepository = raquetasService;
        this.tenistaValidator = tenistaValidator;
    }

    @Override
    @Cacheable // Indicamos que se cachee, no es recomendable si hay muchos!!
    public List<Tenista> findAll() {
        log.info("findAll");
        return tenistasRepository.findAll();
    }

    @Override
    @Cacheable // Indicamos que se cachee
    public Tenista findById(Long id) {
        log.info("findById");
        return tenistasRepository.findById(id).orElseThrow(
                () -> new TenistaNotFoundException("No se ha encontrado el tenista con id: " + id)
        );
    }

    @Override
    @Cacheable // Indicamos que se cachee
    public Tenista findByUuid(UUID uuid) {
        log.info("findByUuid");
        return tenistasRepository.findByUuid(uuid).orElseThrow(
                () -> new TenistaNotFoundException("No se ha encontrado el tenista con uuid: " + uuid)
        );
    }

    @Override
    public List<Tenista> findAllByNombre(String nombre) {
        log.info("findAllByNombre");
        return tenistasRepository.findByNombreContainsIgnoreCase(nombre);
    }

    @Override
    public List<Tenista> findAllByPais(String pais) {
        log.info("findAllByPais");
        return tenistasRepository.findByPaisContainsIgnoreCase(pais);
    }

    @Override
    @Cacheable // Indicamos que se cachee
    public Tenista findByRanking(Integer ranking) {
        log.info("findByRanking");
        return tenistasRepository.findByRanking(ranking).orElseThrow(
                () -> new TenistaNotFoundException("No se ha encontrado el tenista con ranking: " + ranking)
        );
    }

    @Override
    @CachePut // Indicamos que se actualice el caché
    public Tenista save(Tenista tenista) {
        log.info("save");

        // Debemos asignarnos ahora el objeto completo y no el id
        Raqueta miRaqueta = null;

        // Si me pasan la raqueta es porque debe existir
        if (tenista.getRaqueta() != null) {
            miRaqueta = raquetasRepository.findById(tenista.getRaqueta().getId()).orElseThrow(
                    () -> new TenistaBadRequestException("No se puede almacenar pues no existe la raqueta con id: " + tenista.getRaqueta().getId())
            );
        }
        // Si no me pasan la raqueta es porque debe existir o es null porque permitimos nulos!
        // validamos --> Ya lo estamos haciendo con @Valid en el controlador
        // tenistaValidator.validate(tenista);

        // No puede existir otro tenista con el mismo ranking
        if (tenistasRepository.findByRanking(tenista.getRanking()).isPresent()) {
            throw new TenistaBadRequestException("No se puede almacenar pues ya existe un tenista con el mismo ranking: " + tenista.getRanking());
        }

        // Ajustamos los campos
        tenista.setUuid(UUID.randomUUID());
        tenista.setRaqueta(miRaqueta);
        tenista.setCreatedAt(LocalDateTime.now());
        tenista.setUpdatedAt(LocalDateTime.now());
        tenista.setDeleted(false);

        // Guardamos
        return tenistasRepository.save(tenista);
    }

    @Override
    @CachePut // Indicamos que se actualice el caché
    public Tenista update(Long id, Tenista tenista) {
        log.info("update");
        // existe el id?
        var updated = this.findById(id);

        // Debemos asignarnos ahora el objeto completo y no el id
        Raqueta miRaqueta = null;

        // Si me pasan la raqueta es porque debe existir
        if (tenista.getRaqueta() != null) {
            miRaqueta = raquetasRepository.findById(tenista.getRaqueta().getId()).orElseThrow(
                    () -> new TenistaBadRequestException("No se puede almacenar pues no existe la raqueta con id: " + tenista.getRaqueta().getId())
            );
        }

        // validamos --> Ya lo estamos haciendo con @Valid en el controlador
        // tenistaValidator.validate(tenista);

        // No puede existir otro tenista con el mismo ranking y que no sea el mismo
        var ranking = tenistasRepository.findByRanking(tenista.getRanking());
        if (ranking.isPresent() && !ranking.get().getUuid().equals(updated.getUuid())) {
            throw new TenistaBadRequestException("No se puede almacenar pues ya existe un tenista con el mismo ranking: " + tenista.getRanking() + " y no es el mismo");
        }

        // asignamos los nuevos valores
        updated.setNombre(tenista.getNombre());
        updated.setPais(tenista.getPais());
        updated.setRanking(tenista.getRanking());
        updated.setImagen(tenista.getImagen());
        updated.setRaqueta(miRaqueta);
        updated.setUpdatedAt(LocalDateTime.now());

        // Guardamos
        return tenistasRepository.save(updated);
    }

    @Override
    @CacheEvict // Indicamos que se elimine el caché
    public void deleteById(Long id) {
        log.info("deleteById");
        // existe el tenista?
        this.findById(id);
        tenistasRepository.deleteById(id);
    }

    @Override
    public Page<Tenista> findAllUsingPage(Pageable pageable) {
        log.info("findAllUsingPage");
        return tenistasRepository.findAll(pageable);
    }
}
