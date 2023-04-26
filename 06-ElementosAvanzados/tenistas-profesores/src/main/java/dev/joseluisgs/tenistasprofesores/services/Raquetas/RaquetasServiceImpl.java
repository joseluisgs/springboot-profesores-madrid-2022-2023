package dev.joseluisgs.tenistasprofesores.services.Raquetas;

import dev.joseluisgs.tenistasprofesores.models.Raqueta;
import dev.joseluisgs.tenistasprofesores.repositories.raquetas.RaquetasRepository;
import dev.joseluisgs.tenistasprofesores.validators.RaquetaValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@CacheConfig(cacheNames = {"raquetas"}) // Nombre del caché
public class RaquetasServiceImpl implements RaquetasService {
    // Mis repositorios
    private final RaquetasRepository raquetasRepository;
    private final RaquetaValidator raquetaValidator;

    // Inyectamos los repositorios
    @Autowired
    public RaquetasServiceImpl(RaquetasRepository raquetasRepository, RaquetaValidator raquetaValidator) {
        this.raquetasRepository = raquetasRepository;
        this.raquetaValidator = raquetaValidator;
    }

    @Override
    //@Cacheable // Indicamos que se cachee, no es recomendable si hay muchos!!
    public List<Raqueta> findAll() {
        log.info("findAll");
        return raquetasRepository.findAll();
    }

    @Override
    @Cacheable // Indicamos que se cachee
    public Raqueta findById(Long id) {
        log.info("findById");
        return raquetasRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No se ha encontrado la raqueta con id: " + id)
        );
    }

    @Override
    public List<Raqueta> findAllByMarca(String marca) {
        log.info("findAllByMarca");
        return raquetasRepository.findAllByMarca(marca);
    }

    @Override
    @Cacheable
    public Raqueta findByUuid(UUID uuid) {
        log.info("findByUuid");
        return raquetasRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No se ha encontrado la raqueta con uuid: " + uuid)
        );
    }

    @Override
    @CachePut // Indicamos que se actualice el caché
    public Raqueta save(Raqueta raqueta) {
        log.info("save");

        // Validamos!!!
        raquetaValidator.validate(raqueta);

        // Ajustamos los campos
        raqueta.setUuid(UUID.randomUUID());
        raqueta.setCreatedAt(LocalDateTime.now());
        raqueta.setUpdatedAt(LocalDateTime.now());
        raqueta.setDeleted(false);

        // Si todo va bien guardamos
        return raquetasRepository.save(raqueta);
    }

    @Override
    @CachePut // Indicamos que se actualice el caché
    public Raqueta update(Long id, Raqueta raqueta) {
        log.info("update");
        // existe el id?
        var updated = this.findById(id);

        // Validamos!!!
        raquetaValidator.validate(raqueta);

        // asignamos los nuevos valores
        updated.setMarca(raqueta.getMarca());
        updated.setModelo(raqueta.getModelo());
        updated.setPrecio(raqueta.getPrecio());
        updated.setImagen(raqueta.getImagen());
        updated.setUpdatedAt(LocalDateTime.now());

        // Si todo va bien guardamos
        return raquetasRepository.save(updated);
    }

    @Override
    @CacheEvict // Indicamos que se elimine del caché
    public void deleteById(Long id) {
        log.info("deleteById");
        // Existe el id?
        this.findById(id);
        raquetasRepository.deleteById(id);
    }
}
