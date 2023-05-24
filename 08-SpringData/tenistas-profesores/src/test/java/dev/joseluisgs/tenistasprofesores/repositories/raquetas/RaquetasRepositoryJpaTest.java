package dev.joseluisgs.tenistasprofesores.repositories.raquetas;

import dev.joseluisgs.tenistasprofesores.data.raquetas.RaquetasFactory;
import dev.joseluisgs.tenistasprofesores.models.raquetas.Raqueta;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

// Lo mejor es mockear el repositorio (hibernate y probarlo)
// Desde aquí


@SpringBootTest
@Transactional
@TypeExcludeFilters(value = DataJpaTypeExcludeFilter.class)
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@ImportAutoConfiguration


// Todo eso es @DataJpaTest, pero a veces falla al cargar la configuración
// @DataJpaTest
class RaquetasRepositoryJpaTest {
    private final Raqueta raqueta = RaquetasFactory.getRaquetasDemoData().get(1L);

    @Autowired
    private RaquetasRepository repository;
    @Autowired
    private TestEntityManager entityManager; // EntityManager para hacer las pruebas

    @Test
    void findAll() {
        // Que queremos
        entityManager.merge(raqueta);
        entityManager.flush();

        var raquetas = repository.findAll();


        assertAll(
                () -> assertNotNull(raquetas),
                () -> assertTrue(raquetas.size() > 0),
                () -> assertEquals(1, raquetas.size())
        );
    }

    @Test
    void findById() {
        // que queremos
        var res = entityManager.merge(raqueta);
        entityManager.flush();

        var raqueta = repository.findById(res.getId());

        assertAll(
                () -> assertNotNull(raqueta),
                () -> assertEquals("Babolat", raqueta.get().getMarca()),
                () -> assertEquals("Pure Aero", raqueta.get().getModelo()),
                () -> assertEquals(199.95, raqueta.get().getPrecio())
        );
        entityManager.clear();
    }

    @Test
    void findByIdNotFound() {

        var raqueta = repository.findById(100L);

        assertAll(
                () -> assertNotNull(raqueta),
                () -> assertFalse(raqueta.isPresent())
        );
    }

    @Test
    void existsById() {
        // Que queremos
        var res = entityManager.merge(raqueta);
        entityManager.flush();

        var raqueta = repository.existsById(res.getId());

        assertAll(
                () -> assertNotNull(raqueta),
                () -> assertTrue(raqueta)
        );
    }

    @Test
    void existsByIdNotFound() {
        var raqueta = repository.existsById(100L);

        assertAll(
                () -> assertNotNull(raqueta),
                () -> assertFalse(raqueta)
        );
    }

    @Test
    void saveCreate() {
        var raqueta = new Raqueta(
                0L,
                UUID.randomUUID(),
                "Test Insert",
                "Test Insert",
                999.99,
                null,
                null,
                null,
                false
        );

        var raquetaSaved = repository.save(raqueta);

        assertAll(
                () -> assertNotNull(raquetaSaved),
                () -> assertEquals("Test Insert", raquetaSaved.getMarca()),
                () -> assertEquals("Test Insert", raquetaSaved.getModelo()),
                () -> assertEquals(999.99, raquetaSaved.getPrecio())
        );
    }

    @Test
    void saveUpdate() {
        // Que queremos
        var res = entityManager.merge(raqueta);
        entityManager.flush();

        var raqueta = repository.findById(res.getId()).get();
        raqueta.setMarca("Test Update");
        raqueta.setModelo("Test Update");
        raqueta.setPrecio(999.99);

        var raquetaSaved = repository.save(raqueta);

        assertAll(
                () -> assertNotNull(raquetaSaved),
                () -> assertEquals(1L, raquetaSaved.getId()),
                () -> assertEquals("Test Update", raquetaSaved.getMarca()),
                () -> assertEquals("Test Update", raquetaSaved.getModelo()),
                () -> assertEquals(999.99, raquetaSaved.getPrecio())
        );
    }

    @Test
    void deleteById() {
        // Que queremos
        var res = entityManager.merge(raqueta);
        entityManager.flush();

        repository.deleteById(res.getId());

        var raqueta = repository.findById(1L);

        assertAll(
                () -> assertNotNull(raqueta),
                () -> assertFalse(raqueta.isPresent())
        );
    }

    @Test
    void delete() {
        // Que queremos
        var res = entityManager.merge(raqueta);
        entityManager.flush();


        repository.delete(res);

        var raqueta = repository.findById(1L);

        assertAll(
                () -> assertNotNull(raqueta),
                () -> assertFalse(raqueta.isPresent())
        );

    }

    @Test
    void count() {
        // Que queremos
        entityManager.merge(raqueta);
        entityManager.flush();

        var raquetas = repository.count();

        assertAll(
                () -> assertNotNull(raquetas),
                () -> assertTrue(raquetas > 0)
        );
    }

    // No lo hacemos porque no podemos borrar todas las raquetas
    // al tener tenistas que tienen raquetas en el script de datos
    /*@Test
    void deleteAll() {
        entityManager.merge(raqueta);
        entityManager.flush();

        repository.deleteAll();

        var raquetas = repository.findAll();

        assertAll(
                () -> assertNotNull(raquetas),
                () -> assertEquals(0, raquetas.size())
        );

    }*/

    @Test
    void findAllByMarca() {
        // Que queremos
        entityManager.merge(raqueta);
        entityManager.flush();

        var raquetas = repository.findByMarcaContainsIgnoreCase("Babolat");

        assertAll(
                () -> assertNotNull(raquetas),
                () -> assertTrue(raquetas.size() > 0)
        );
    }

    @Test
    void findByUuidExists() {
        // Que queremos
        entityManager.merge(raqueta);
        entityManager.flush();

        var res = repository.findByUuid(raqueta.getUuid());

        assertAll(
                () -> assertNotNull(raqueta),
                () -> assertEquals("Babolat", res.get().getMarca()),
                () -> assertEquals("Pure Aero", res.get().getModelo()),
                () -> assertEquals(199.95, res.get().getPrecio())
        );
    }

    @Test
    void findByUuidNotFound() {
        var raqueta = repository.findByUuid(UUID.randomUUID());

        assertAll(
                () -> assertNotNull(raqueta),
                () -> assertFalse(raqueta.isPresent())
        );
    }
}