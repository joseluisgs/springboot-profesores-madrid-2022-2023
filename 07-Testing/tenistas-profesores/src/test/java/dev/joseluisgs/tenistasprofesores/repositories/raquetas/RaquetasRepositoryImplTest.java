package dev.joseluisgs.tenistasprofesores.repositories.raquetas;

import dev.joseluisgs.tenistasprofesores.data.raquetas.RaquetasFactory;
import dev.joseluisgs.tenistasprofesores.models.raquetas.Raqueta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

// @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RaquetasRepositoryImplTest {
    RaquetasRepository repository = new RaquetasRepositoryImpl();

    @BeforeEach
    void setUp() {
        // Inicializamos las el repositorio
        repository = new RaquetasRepositoryImpl();

    }


    @Test
        // @Order(1)
    void findAll() {
        var raquetas = repository.findAll();

        assertAll(
                () -> assertNotNull(raquetas),
                () -> assertEquals(3, raquetas.size()),
                () -> assertTrue(raquetas.size() > 0)
        );
    }

    @Test
    void findById() {
        var raqueta = repository.findById(1L);

        assertAll(
                () -> assertNotNull(raqueta),
                () -> assertTrue(raqueta.isPresent()),
                () -> assertEquals(1L, raqueta.get().getId()),
                () -> assertEquals("Babolat", raqueta.get().getMarca()),
                () -> assertEquals("Pure Aero", raqueta.get().getModelo()),
                () -> assertEquals(199.95, raqueta.get().getPrecio())
        );
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
        var raqueta = repository.existsById(1L);

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
        var raqueta = new Raqueta(
                1L,
                UUID.randomUUID(),
                "Test Update",
                "Test Update",
                999.99,
                null,
                null,
                null,
                false
        );

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
        repository.deleteById(1L);

        var raqueta = repository.findById(1L);

        assertAll(
                () -> assertNotNull(raqueta),
                () -> assertFalse(raqueta.isPresent())
        );
    }

    @Test
    void delete() {
        repository.delete(RaquetasFactory.getRaquetasDemoData().get(1L));

        var raqueta = repository.findById(1L);

        assertAll(
                () -> assertNotNull(raqueta),
                () -> assertFalse(raqueta.isPresent())
        );

    }

    @Test
    void count() {
        var raquetas = repository.count();

        assertAll(
                () -> assertNotNull(raquetas),
                () -> assertEquals(3, raquetas)
        );
    }

    @Test
    void deleteAll() {
        repository.deleteAll();

        var raquetas = repository.findAll();

        assertAll(
                () -> assertNotNull(raquetas),
                () -> assertEquals(0, raquetas.size())
        );

    }

    @Test
    void findAllByMarca() {
        var raquetas = repository.findAllByMarca("Babolat");

        assertAll(
                () -> assertNotNull(raquetas),
                () -> assertEquals(1, raquetas.size())
        );
    }

    @Test
    void findByUuidExists() {
        var raqueta = repository.findByUuid(RaquetasFactory.getRaquetasDemoData().get(1L).getUuid());

        assertAll(
                () -> assertNotNull(raqueta),
                () -> assertEquals("Babolat", raqueta.get().getMarca()),
                () -> assertEquals("Pure Aero", raqueta.get().getModelo()),
                () -> assertEquals(199.95, raqueta.get().getPrecio())
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