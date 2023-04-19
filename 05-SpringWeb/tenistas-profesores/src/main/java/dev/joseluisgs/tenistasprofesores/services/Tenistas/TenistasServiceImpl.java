package dev.joseluisgs.tenistasprofesores.services.Tenistas;

import dev.joseluisgs.tenistasprofesores.models.Tenista;
import dev.joseluisgs.tenistasprofesores.repositories.raquetas.RaquetasRepository;
import dev.joseluisgs.tenistasprofesores.repositories.tenistas.TenistasRepository;
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
@CacheConfig(cacheNames = {"tenistas"}) // Nombre del caché
public class TenistasServiceImpl implements TenistasService {
    // Mis dependencias
    private final TenistasRepository tenistasRepository;
    private final RaquetasRepository raquetasService;

    // Inyectamos los repositorios
    @Autowired
    public TenistasServiceImpl(TenistasRepository tenistasRepository, RaquetasRepository raquetasService) {
        this.tenistasRepository = tenistasRepository;
        this.raquetasService = raquetasService;
    }

    @Override
    @Cacheable // Indicamos que se cachee, no es recomendable si hay muchos!!
    public List<Tenista> findAll() {
        log.info("findAll");
        return tenistasRepository.findAll();
    }

    @Override
    @Cacheable // Indicamos que se cachee
    public Optional<Tenista> findById(Long id) {
        log.info("findById");
        return tenistasRepository.findById(id);
    }

    @Override
    @Cacheable // Indicamos que se cachee
    public Optional<Tenista> findByUuid(UUID uuid) {
        log.info("findByUuid");
        return tenistasRepository.findByUuid(uuid);
    }

    @Override
    public List<Tenista> findAllByNombre(String nombre) {
        log.info("findAllByNombre");
        return tenistasRepository.findAllByNombre(nombre);
    }

    @Override
    public List<Tenista> findAllByPais(String pais) {
        log.info("findAllByPais");
        return tenistasRepository.findAllByPais(pais);
    }

    @Override
    @Cacheable // Indicamos que se cachee
    public Optional<Tenista> findByRanking(Integer ranking) {
        log.info("findByRanking");
        return tenistasRepository.findByRanking(ranking);
    }

    @Override
    @CachePut // Indicamos que se actualice el caché
    public Tenista save(Tenista raqueta) {
        log.info("save");
        return tenistasRepository.save(raqueta);
    }

    @Override
    @CacheEvict // Indicamos que se elimine el caché
    public void deleteById(Long id) {
        log.info("deleteById");
        tenistasRepository.deleteById(id);
    }
}
