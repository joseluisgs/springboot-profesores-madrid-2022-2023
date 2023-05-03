package dev.joseluisgs.tenistasprofesores.repositories.tenistas;

import dev.joseluisgs.tenistasprofesores.data.tenistas.TenistasFactory;
import dev.joseluisgs.tenistasprofesores.models.tenistas.Tenista;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TenistasRepositoryImplTest {

    TenistasRepository repository = new TenistasRepositoryImpl();

    @BeforeEach
    void setUp() {
        // Inicializamos las el repositorio
        repository = new TenistasRepositoryImpl();
    }


    @Test
    void findAll() {
        var tenistas = repository.findAll();

        assertAll(
                () -> assertNotNull(tenistas),
                () -> assertEquals(4, tenistas.size())
        );
    }

    @Test
    void findById() {
        var tenista = repository.findById(1L);

        assertAll(
                () -> assertNotNull(tenista),
                () -> assertEquals("Rafael Nadal", tenista.get().getNombre()),
                () -> assertEquals("España", tenista.get().getPais()),
                () -> assertEquals(1, tenista.get().getRanking())
        );
    }

    @Test
    void findByIdNotFound() {
        var tenista = repository.findById(100L);

        assertAll(
                () -> assertNotNull(tenista),
                () -> assertFalse(tenista.isPresent())
        );
    }

    @Test
    void existsById() {
        var tenista = repository.existsById(1L);

        assertAll(
                () -> assertNotNull(tenista),
                () -> assertTrue(tenista)
        );
    }

    @Test
    @DisplayName("existsById not found y devuelve false")
    void existsByIdNotFound() {
        var tenista = repository.existsById(100L);

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
        var tenista = new Tenista(
                1L,
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
    void deleteById() {
        repository.deleteById(1L);

        var tenista = repository.findById(1L);

        assertAll(
                () -> assertNotNull(tenista),
                () -> assertFalse(tenista.isPresent())
        );
    }

    @Test
    void delete() {
        repository.delete(TenistasFactory.getTenistasDemoData().get(1L));

        var tenista = repository.findById(1L);

        assertAll(
                () -> assertNotNull(tenista),
                () -> assertFalse(tenista.isPresent())
        );
    }

    @Test
    void count() {
        var count = repository.count();

        assertAll(
                () -> assertNotNull(count),
                () -> assertEquals(4, count)
        );
    }

    @Test
    void deleteAll() {
        repository.deleteAll();

        var tenistas = repository.findAll();

        assertAll(
                () -> assertNotNull(tenistas),
                () -> assertEquals(0, tenistas.size())
        );
    }

    @Test
    void findAllByNombre() {
        var tenistas = repository.findAllByNombre("Rafael Nadal");

        assertAll(
                () -> assertNotNull(tenistas),
                () -> assertEquals(1, tenistas.size())
        );
    }

    @Test
    void findAllByPais() {
        var tenistas = repository.findAllByPais("España");

        assertAll(
                () -> assertNotNull(tenistas),
                () -> assertEquals(2, tenistas.size())
        );
    }

    @Test
    void findByUuid() {
        var tenista = repository.findByUuid(TenistasFactory.getTenistasDemoData().get(1L).getUuid());

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
        var tenista = repository.findByRanking(1);

        assertAll(
                () -> assertNotNull(tenista),
                () -> assertEquals("Rafael Nadal", tenista.get().getNombre()),
                () -> assertEquals("España", tenista.get().getPais()),
                () -> assertEquals(1, tenista.get().getRanking())
        );

    }

    @Test
    void findByRankingNotFound() {
        var tenista = repository.findByRanking(100);

        assertAll(
                () -> assertNotNull(tenista),
                () -> assertFalse(tenista.isPresent())
        );
    }
}