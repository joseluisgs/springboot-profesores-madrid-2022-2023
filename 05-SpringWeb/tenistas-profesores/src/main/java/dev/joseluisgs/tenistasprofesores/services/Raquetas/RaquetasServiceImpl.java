package dev.joseluisgs.tenistasprofesores.services.Raquetas;

import dev.joseluisgs.tenistasprofesores.models.Raqueta;
import dev.joseluisgs.tenistasprofesores.repositories.raquetas.RaquetasRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@CacheConfig(cacheNames = {"raquetas"}) // Nombre del caché
public class RaquetasServiceImpl implements RaquetasService {
    // Mis repositorios
    private final RaquetasRepository raquetasRepository;

    // Inyectamos los repositorios
    @Autowired
    public RaquetasServiceImpl(RaquetasRepository raquetasRepository) {
        this.raquetasRepository = raquetasRepository;
    }

    @Override
    @Cacheable // Indicamos que se cachee, no es recomendable si hay muchos!!
    public List<Raqueta> findAll() {
        log.info("findAll");
        return raquetasRepository.findAll();
    }

    @Override
    @Cacheable // Indicamos que se cachee
    public Optional<Raqueta> findById(Long id) {
        log.info("findById");
        return raquetasRepository.findById(id);
    }

    @Override
    public List<Raqueta> findAllByMarca(String marca) {
        log.info("findAllByMarca");
        return raquetasRepository.findAllByMarca(marca);
    }

    @Override
    @Cacheable
    public Optional<Raqueta> findByUuid(UUID uuid) {
        log.info("findByUuid");
        return raquetasRepository.findByUuid(uuid);
    }

    @Override
    @CachePut // Indicamos que se actualice el caché
    public Raqueta save(Raqueta raqueta) {
        log.info("save");
        return raquetasRepository.save(raqueta);
    }

    @Override
    @CacheEvict // Indicamos que se elimine del caché
    public void deleteById(Long id) {
        log.info("deleteById");
        raquetasRepository.deleteById(id);
    }
}
