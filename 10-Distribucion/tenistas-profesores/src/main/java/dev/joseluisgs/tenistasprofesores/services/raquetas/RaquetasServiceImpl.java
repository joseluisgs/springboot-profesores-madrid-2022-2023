package dev.joseluisgs.tenistasprofesores.services.raquetas;

import dev.joseluisgs.tenistasprofesores.exceptions.raqueta.RaquetaConflicIntegrityException;
import dev.joseluisgs.tenistasprofesores.exceptions.raqueta.RaquetaNotFoundException;
import dev.joseluisgs.tenistasprofesores.models.raquetas.Raqueta;
import dev.joseluisgs.tenistasprofesores.repositories.raquetas.RaquetasRepository;
import dev.joseluisgs.tenistasprofesores.repositories.tenistas.TenistasRepository;
import dev.joseluisgs.tenistasprofesores.validators.raquetas.RaquetaValidator;
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
import java.util.ArrayList;
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
    public RaquetasServiceImpl(RaquetasRepository raquetasRepository, TenistasRepository tenistasRepository, RaquetaValidator raquetaValidator) {
        this.raquetasRepository = raquetasRepository;
        this.raquetaValidator = raquetaValidator;
    }

    @Override
    @Cacheable // Indicamos que se cachee, no es recomendable si hay muchos!!
    public List<Raqueta> findAll() {
        log.info("findAll");
        // Para no cambiar todos los iterbles lo cambio a lista
        var res = new ArrayList<Raqueta>();
        raquetasRepository.findAll().forEach(res::add);
        return res;
    }

    @Override
    @Cacheable // Indicamos que se cachee
    public Raqueta findById(Long id) {
        log.info("findById");
        return raquetasRepository.findById(id).orElseThrow(
                () -> new RaquetaNotFoundException("No se ha encontrado la raqueta con id: " + id)
        );
    }

    @Override
    public List<Raqueta> findAllByMarca(String marca) {
        log.info("findAllByMarca");
        return raquetasRepository.findByMarcaContainsIgnoreCase(marca);
    }

    @Override
    @Cacheable
    public Raqueta findByUuid(UUID uuid) {
        log.info("findByUuid");
        return raquetasRepository.findByUuid(uuid).orElseThrow(
                () -> new RaquetaNotFoundException("No se ha encontrado la raqueta con uuid: " + uuid)
        );
    }

    @Override
    @CachePut // Indicamos que se actualice el caché
    public Raqueta save(Raqueta raqueta) {
        log.info("save");

        // validamos --> Ya lo estamos haciendo con @Valid en el controlador
        // raquetaValidator.validate(raqueta);

        // Ajustamos los campos
        raqueta.setUuid(UUID.randomUUID());
        raqueta.setCreatedAt(LocalDateTime.now());
        raqueta.setUpdatedAt(LocalDateTime.now());
        raqueta.setDeleted(false);

        System.out.println(raqueta);

        // Si todo va bien guardamos
        return raquetasRepository.save(raqueta);
    }

    @Override
    @CachePut // Indicamos que se actualice el caché
    public Raqueta update(Long id, Raqueta raqueta) {
        log.info("update");
        // existe el id?
        var updated = this.findById(id);

        // validamos --> Ya lo estamos haciendo con @Valid en el controlador
        // raquetaValidator.validate(raqueta);

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
        try {
            raquetasRepository.deleteById(id);
        } catch (Exception e) {
            throw new RaquetaConflicIntegrityException("No se ha podido eliminar la raqueta con id: " + id + " ya que tiene tenistas asociados");
        }
    }

    @Override
    public Page<Raqueta> findAllUsingPage(Pageable pageable) {
        log.info("findAllUsingPage");
        return raquetasRepository.findAll(pageable);
    }

}
