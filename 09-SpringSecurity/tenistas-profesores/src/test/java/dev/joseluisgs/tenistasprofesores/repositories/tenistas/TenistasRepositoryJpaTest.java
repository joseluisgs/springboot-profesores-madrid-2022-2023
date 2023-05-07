package dev.joseluisgs.tenistasprofesores.repositories.tenistas;

import dev.joseluisgs.tenistasprofesores.data.tenistas.TenistasFactory;
import dev.joseluisgs.tenistasprofesores.models.tenistas.Tenista;
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


@SpringBootTest
@Transactional
@TypeExcludeFilters(value = DataJpaTypeExcludeFilter.class)
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@ImportAutoConfiguration

// Todo eso es @DataJpaTest, pero a veces falla al cargar la configuración
//@DataJpaTest
class TenistasRepositoryJpaTest {

    private final Tenista tenista = TenistasFactory.getTenistasDemoData().get(1L);
    @Autowired
    private TenistasRepository repository;
    @Autowired
    private TestEntityManager entityManager; // EntityManager para hacer las pruebas


    @Test
    void findAll() {
        // Que queremos
        entityManager.merge(tenista);
        entityManager.flush();

        var tenistas = repository.findAll();

        assertAll(
                () -> assertNotNull(tenistas),
                () -> assertTrue(tenistas.size() > 0)
        );
    }

    @Test
    void findById() {
        // que queremos
        var res = entityManager.merge(tenista);
        entityManager.flush();

        var tenista = repository.findById(res.getId());

        assertAll(
                () -> assertNotNull(tenista),
                () -> assertEquals("Rafael Nadal", tenista.get().getNombre()),
                () -> assertEquals("España", tenista.get().getPais()),
                () -> assertEquals(1, tenista.get().getRanking())
        );
    }

    @Test
    void findByIdNotFound() {
        var tenista = repository.findById(-100L);

        assertAll(
                () -> assertNotNull(tenista),
                () -> assertFalse(tenista.isPresent())
        );
    }

    @Test
    void existsById() {
        // que queremos
        var res = entityManager.merge(tenista);
        entityManager.flush();

        var tenista = repository.existsById(res.getId());

        assertAll(
                () -> assertNotNull(tenista),
                () -> assertTrue(tenista)
        );
    }

    @Test
    void existsByIdNotFound() {
        var tenista = repository.existsById(-100L);

        assertAll(
                () -> assertNotNull(tenista),
                () -> assertFalse(tenista)
        );
    }

    @Test
    void saveCreate() {
        var tenista = new Tenista(
                0L,
                UUID.randomUUID(),
                "Test",
                99,
                "Test",
                null,
                null,
                null,
                null,
                null
        );

        var tenistaSaved = repository.save(tenista);

        assertAll(
                () -> assertNotNull(tenistaSaved),
                () -> assertEquals("Test", tenistaSaved.getNombre()),
                () -> assertEquals(99, tenistaSaved.getRanking()),
                () -> assertEquals("Test", tenistaSaved.getPais())
        );

    }

    @Test
    void saveUpdate() {
        // Que queremos
        var res = entityManager.merge(tenista);
        entityManager.flush();

        var tenista = repository.findById(res.getId()).get();
        tenista.setNombre("Test");
        tenista.setRanking(99);
        tenista.setPais("Test");

        var tenistaSaved = repository.save(tenista);

        assertAll(
                () -> assertNotNull(tenistaSaved),
                () -> assertEquals("Test", tenistaSaved.getNombre()),
                () -> assertEquals(99, tenistaSaved.getRanking()),
                () -> assertEquals("Test", tenistaSaved.getPais())
        );

    }

    @Test
    void deleteById() {
        // Que queremos
        var res = entityManager.merge(tenista);
        entityManager.flush();

        repository.deleteById(res.getId());

        var tenista = repository.findById(res.getId());

        assertAll(
                () -> assertNotNull(tenista),
                () -> assertFalse(tenista.isPresent())
        );
    }

    @Test
    void delete() {
        // Que queremos
        var res = entityManager.merge(tenista);
        entityManager.flush();

        repository.delete(res);

        var tenista = repository.findById(1L);

        assertAll(
                () -> assertNotNull(tenista),
                () -> assertFalse(tenista.isPresent())
        );
    }

    @Test
    void count() {
        // Que queremos
        entityManager.merge(tenista);
        entityManager.flush();

        var count = repository.count();

        assertAll(
                () -> assertNotNull(count),
                () -> assertTrue(count > 0)
        );
    }

    @Test
    void deleteAll() {
        // Que queremos
        entityManager.merge(tenista);
        entityManager.flush();

        repository.deleteAll();

        var tenistas = repository.findAll();

        assertAll(
                () -> assertNotNull(tenistas),
                () -> assertEquals(0, tenistas.size())
        );
    }

    @Test
    void findAllByNombre() {
        // Que queremos
        entityManager.merge(tenista);
        entityManager.flush();

        var tenistas = repository.findByNombreContainsIgnoreCase("Rafael Nadal");

        assertAll(
                () -> assertNotNull(tenistas),
                () -> assertTrue(tenistas.size() > 0)
        );
    }

    @Test
    void findAllByPais() {
        // Que queremos
        entityManager.merge(tenista);
        entityManager.flush();

        var tenistas = repository.findByPaisContainsIgnoreCase("España");

        assertAll(
                () -> assertNotNull(tenistas),
                () -> assertEquals(2, tenistas.size())
        );
    }

    @Test
    void findByUuid() {
        // Que queremos
        var res = entityManager.merge(tenista);
        entityManager.flush();

        var tenista = repository.findByUuid(res.getUuid());

        assertAll(
                () -> assertNotNull(tenista),
                () -> assertEquals("Rafael Nadal", tenista.get().getNombre()),
                () -> assertEquals("España", tenista.get().getPais()),
                () -> assertEquals(1, tenista.get().getRanking())
        );
    }

    @Test
    void findByUuidNotFound() {
        var tenista = repository.findByUuid(UUID.randomUUID());

        assertAll(
                () -> assertNotNull(tenista),
                () -> assertFalse(tenista.isPresent())
        );
    }

    @Test
    void findByRanking() {
        // Que queremos
        var res = entityManager.merge(tenista);
        entityManager.flush();

        var tenista = repository.findByRanking(res.getRanking());

        assertAll(
                () -> assertNotNull(tenista),
                () -> assertEquals("Rafael Nadal", tenista.get().getNombre()),
                () -> assertEquals("España", tenista.get().getPais()),
                () -> assertEquals(1, tenista.get().getRanking())
        );

    }

    @Test
    void findByRankingNotFound() {
        var tenista = repository.findByRanking(-100);

        assertAll(
                () -> assertNotNull(tenista),
                () -> assertFalse(tenista.isPresent())
        );
    }
}