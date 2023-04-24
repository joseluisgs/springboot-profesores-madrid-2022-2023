package dev.joseluisgs.tenistasprofesores.services.tenistas;

import dev.joseluisgs.tenistasprofesores.data.raquetas.RaquetasFactory;
import dev.joseluisgs.tenistasprofesores.data.tenistas.TenistasFactory;
import dev.joseluisgs.tenistasprofesores.models.tenistas.Tenista;
import dev.joseluisgs.tenistasprofesores.repositories.raquetas.RaquetasRepositoryImpl;
import dev.joseluisgs.tenistasprofesores.repositories.tenistas.TenistasRepositoryImpl;
import dev.joseluisgs.tenistasprofesores.validators.tenistas.TenistaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class) // Extensión de Mockito para usarlo
class TenistasServiceImplTest {
    // Mis dependencias

    Map<Long, Tenista> tenistas = TenistasFactory.getTenistasDemoData();

    @Mock
    private TenistasRepositoryImpl tenistasRepository;

    @Mock
    private RaquetasRepositoryImpl raquetasRepository;

    @Mock
    private TenistaValidator tenistaValidator;

    @InjectMocks
    private TenistasServiceImpl tenistasService;

    @BeforeEach
    void setUp() {
        tenistas = TenistasFactory.getTenistasDemoData();
    }

    @Test
    void findAll() {
        // Lo que vamos a simular
        when(tenistasRepository.findAll())
                .thenReturn(List.copyOf(tenistas.values()));

        // test
        var list = tenistasService.findAll();

        // comprobaciones
        assertAll(
                () -> assertNotNull(list),
                () -> assertEquals(4, list.size())
        );

        // verificamos que se ha llamado al método
        verify(tenistasRepository, times(1))
                .findAll();

    }

    @Test
    void findById() {
        // Lo que vamos a simular
        when(tenistasRepository.findById(1L))
                .thenReturn(Optional.of(tenistas.get(1L)));

        // test
        var tenista = tenistasService.findById(1L);

        // comprobaciones
        assertAll(
                () -> assertNotNull(tenista),
                () -> assertEquals(1L, tenista.getId()),
                () -> assertEquals("Rafael Nadal", tenista.getNombre()),
                () -> assertEquals("España", tenista.getPais()),
                () -> assertEquals(1, tenista.getRanking())
        );

        // verificamos que se ha llamado al método
        verify(tenistasRepository, times(1))
                .findById(1L);
    }

    @Test
    void findByIdNotFound() {
        when(tenistasRepository.findById(-100L))
                .thenReturn(Optional.empty());

        // Salta la excepcion
        var res = assertThrows(ResponseStatusException.class, () -> {
            tenistasService.findById(-100L);
        });
        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("No se ha encontrado el tenista con id: -100"));

        // Verificamos que se ha llamado al método
        verify(tenistasRepository, times(1))
                .findById(-100L);
    }

    @Test
    void findByUuid() {
        // Lo que vamos a simular
        when(tenistasRepository.findByUuid(tenistas.get(1L).getUuid()))
                .thenReturn(Optional.of(tenistas.get(1L)));

        // test
        var tenista = tenistasService.findByUuid(tenistas.get(1L).getUuid());

        // comprobaciones
        assertAll(
                () -> assertNotNull(tenista),
                () -> assertEquals(1L, tenista.getId()),
                () -> assertEquals("Rafael Nadal", tenista.getNombre()),
                () -> assertEquals("España", tenista.getPais()),
                () -> assertEquals(1, tenista.getRanking())
        );

        // verificamos que se ha llamado al método
        verify(tenistasRepository, times(1))
                .findByUuid(tenistas.get(1L).getUuid());
    }

    @Test
    void findByUuidNotFound() {
        when(tenistasRepository.findByUuid(tenistas.get(1L).getUuid()))
                .thenReturn(Optional.empty());

        // Salta la excepcion
        var res = assertThrows(ResponseStatusException.class, () -> {
            tenistasService.findByUuid(tenistas.get(1L).getUuid());
        });
        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("No se ha encontrado el tenista con uuid: "));

        // Verificamos que se ha llamado al método
        verify(tenistasRepository, times(1))
                .findByUuid(tenistas.get(1L).getUuid());
    }

    @Test
    void findAllByNombre() {
        // Lo que vamos a simular
        when(tenistasRepository.findAllByNombre("Rafael Nadal"))
                .thenReturn(List.of(tenistas.get(1L)));

        // test
        var list = tenistasService.findAllByNombre("Rafael Nadal");

        // comprobaciones
        assertAll(
                () -> assertNotNull(list),
                () -> assertEquals(1, list.size()),
                () -> assertEquals(1L, list.get(0).getId()),
                () -> assertEquals("Rafael Nadal", list.get(0).getNombre()),
                () -> assertEquals("España", list.get(0).getPais()),
                () -> assertEquals(1, list.get(0).getRanking())
        );

        // verificamos que se ha llamado al método
        verify(tenistasRepository, times(1))
                .findAllByNombre("Rafael Nadal");
    }

    @Test
    void findAllByPais() {
        // Lo que vamos a simular
        when(tenistasRepository.findAllByPais("España"))
                .thenReturn(List.of(tenistas.get(1L)));

        // test
        var list = tenistasService.findAllByPais("España");

        // comprobaciones
        assertAll(
                () -> assertNotNull(list),
                () -> assertEquals(1, list.size()),
                () -> assertEquals(1L, list.get(0).getId()),
                () -> assertEquals("Rafael Nadal", list.get(0).getNombre()),
                () -> assertEquals("España", list.get(0).getPais()),
                () -> assertEquals(1, list.get(0).getRanking())
        );

        // verificamos que se ha llamado al método
    }

    @Test
    void findByRanking() {
        // Lo que vamos a simular
        when(tenistasRepository.findByRanking(1))
                .thenReturn(Optional.of(tenistas.get(1L)));

        // test
        var tenista = tenistasService.findByRanking(1);

        // comprobaciones
        assertAll(
                () -> assertNotNull(tenista),
                () -> assertEquals(1L, tenista.getId()),
                () -> assertEquals("Rafael Nadal", tenista.getNombre()),
                () -> assertEquals("España", tenista.getPais()),
                () -> assertEquals(1, tenista.getRanking())
        );

        // verificamos que se ha llamado al método
        verify(tenistasRepository, times(1))
                .findByRanking(1);
    }

    @Test
    void findByRankingNotFound() {
        when(tenistasRepository.findByRanking(1))
                .thenReturn(Optional.empty());

        // Salta la excepcion
        var res = assertThrows(ResponseStatusException.class, () -> {
            tenistasService.findByRanking(1);
        });
        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("No se ha encontrado el tenista con ranking: 1"));

        // Verificamos que se ha llamado al método
        verify(tenistasRepository, times(1))
                .findByRanking(1);
    }

    @Test
    void saveRaquetaNotNullYRanking() {
        // Lo que vamos a simular
        when(tenistasRepository.save(tenistas.get(1L)))
                .thenReturn(tenistas.get(1L));
        when(raquetasRepository.findById(1L))
                .thenReturn(Optional.of(RaquetasFactory.getRaquetasDemoData().get(1L)));
        doNothing().when(tenistaValidator).validate(tenistas.get(1L));
        when(tenistasRepository.findByRanking(1))
                .thenReturn(Optional.empty());

        // test
        var tenista = tenistasService.save(tenistas.get(1L));

        // comprobaciones
        assertAll(
                () -> assertNotNull(tenista),
                () -> assertEquals(1L, tenista.getId()),
                () -> assertEquals("Rafael Nadal", tenista.getNombre()),
                () -> assertEquals("España", tenista.getPais()),
                () -> assertEquals(1, tenista.getRanking())
        );

        // verificamos que se ha llamado al método
        verify(tenistasRepository, times(1))
                .save(tenistas.get(1L));
        verify(raquetasRepository, times(1))
                .findById(1L);
        verify(tenistaValidator, times(1))
                .validate(tenistas.get(1L));
        verify(tenistasRepository, times(1))
                .findByRanking(1);
    }

    @Test
    void saveFailsRaqueta() {
        // Lo que vamos a simular
        when(raquetasRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Salta la excepcion
        var res = assertThrows(ResponseStatusException.class, () -> {
            tenistasService.save(tenistas.get(1L));
        });
        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("No se puede almacenar pues no existe la raqueta con id: 1"));

        // verificamos que se ha llamado al método
        verify(tenistasRepository, times(0))
                .save(tenistas.get(1L));
        verify(raquetasRepository, times(1))
                .findById(1L);
        verify(tenistaValidator, times(0))
                .validate(tenistas.get(1L));
        verify(tenistasRepository, times(0))
                .findByRanking(1);
    }

    @Test
    void saveFailsMarca() {
        // Lo que vamos a simular
        when(raquetasRepository.findById(1L))
                .thenReturn(Optional.of(RaquetasFactory.getRaquetasDemoData().get(1L)));
        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre no puede estar vacío"))
                .when(tenistaValidator).validate(tenistas.get(1L));

        // Salta la excepcion
        var res = assertThrows(ResponseStatusException.class, () -> {
            tenistasService.save(tenistas.get(1L));
        });

        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("El nombre no puede estar vacío"));

        // Verificamos que se ha llamado al método
        verify(tenistasRepository, times(0))
                .save(tenistas.get(1L));
        verify(tenistaValidator, times(1))
                .validate(tenistas.get(1L));
        verify(raquetasRepository, times(1))
                .findById(1L);
        verify(tenistasRepository, times(0))
                .findByRanking(1);
    }

    @Test
    void saveRankingFails() {
        // Lo que vamos a simular
        when(raquetasRepository.findById(1L))
                .thenReturn(Optional.of(RaquetasFactory.getRaquetasDemoData().get(1L)));
        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ranking no puede ser negativo"))
                .when(tenistaValidator).validate(tenistas.get(1L));


        // Salta la excepcion
        var res = assertThrows(ResponseStatusException.class, () -> {
            tenistasService.save(tenistas.get(1L));
        });
        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("El ranking no puede ser negativo"));

        // verificamos que se ha llamado al método
        verify(tenistasRepository, times(0))
                .save(tenistas.get(1L));
        verify(raquetasRepository, times(1))
                .findById(1L);
        verify(tenistaValidator, times(1))
                .validate(tenistas.get(1L));
        verify(tenistasRepository, times(0))
                .findByRanking(1);
    }

    @Test
    void savePaisFails() {
        // Lo que vamos a simular
        when(raquetasRepository.findById(1L))
                .thenReturn(Optional.of(RaquetasFactory.getRaquetasDemoData().get(1L)));
        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "El país no puede estar vacío"))
                .when(tenistaValidator).validate(tenistas.get(1L));

        // Salta la excepcion
        var res = assertThrows(ResponseStatusException.class, () -> {
            tenistasService.save(tenistas.get(1L));
        });
        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("El país no puede estar vacío"));

        // verificamos que se ha llamado al método
        verify(tenistasRepository, times(0))
                .save(tenistas.get(1L));
        verify(raquetasRepository, times(1))
                .findById(1L);
        verify(tenistaValidator, times(1))
                .validate(tenistas.get(1L));
        verify(tenistasRepository, times(0))
                .findByRanking(1);
    }

    @Test
    void saveRankingExistsFails() {
        // Lo que vamos a simular
        when(raquetasRepository.findById(1L))
                .thenReturn(Optional.of(RaquetasFactory.getRaquetasDemoData().get(1L)));
        doNothing().when(tenistaValidator).validate(tenistas.get(1L));
        when(tenistasRepository.findByRanking(1))
                .thenReturn(Optional.of(tenistas.get(1L)));

        // Salta la excepcion
        var res = assertThrows(ResponseStatusException.class, () -> {
            tenistasService.save(tenistas.get(1L));
        });
        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("No se puede almacenar pues ya existe un tenista con el mismo ranking: 1"));

        // verificamos que se ha llamado al método
        verify(tenistasRepository, times(0))
                .save(tenistas.get(1L));
        verify(raquetasRepository, times(1))
                .findById(1L);
        verify(tenistaValidator, times(1))
                .validate(tenistas.get(1L));
        verify(tenistasRepository, times(1))
                .findByRanking(1);
    }


    @Test
    void update() {
        // Lo que vamos a simular
        when(tenistasRepository.findById(1L))
                .thenReturn(Optional.of(tenistas.get(1L)));
        when(raquetasRepository.findById(1L))
                .thenReturn(Optional.of(RaquetasFactory.getRaquetasDemoData().get(1L)));
        doNothing().when(tenistaValidator).validate(tenistas.get(1L));
        when(tenistasRepository.findByRanking(1))
                .thenReturn(Optional.empty());
        when(tenistasRepository.save(tenistas.get(1L)))
                .thenReturn(tenistas.get(1L));

        // test
        var res = tenistasService.update(1L, tenistas.get(1L));

        // comprobamos que el resultado es el esperado
        assertAll(
                () -> assertEquals(1L, res.getId()),
                () -> assertEquals("Rafael Nadal", res.getNombre()),
                () -> assertEquals("España", res.getPais()),
                () -> assertEquals(1, res.getRanking()),
                () -> assertEquals(1L, res.getRaqueta().getId())
        );

        // verificamos que se ha llamado al método
        verify(tenistasRepository, times(1))
                .findById(1L);
        verify(raquetasRepository, times(1))
                .findById(1L);
        verify(tenistaValidator, times(1))
                .validate(tenistas.get(1L));
        verify(tenistasRepository, times(1))
                .findByRanking(1);
        verify(tenistasRepository, times(1))
                .save(tenistas.get(1L));
    }

    @Test
    void updateIdNotFound() {
        // Lo que vamos a simular
        when(tenistasRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Salta la excepcion
        var res = assertThrows(ResponseStatusException.class, () -> {
            tenistasService.update(1L, tenistas.get(1L));
        });

        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("No se ha encontrado el tenista con id: 1"));

        // verificamos que se ha llamado al método
        verify(tenistasRepository, times(1))
                .findById(1L);
    }

    @Test
    void updateRaquetaNotFound() {
        // Lo que vamos a simular
        when(tenistasRepository.findById(1L))
                .thenReturn(Optional.of(tenistas.get(1L)));
        when(raquetasRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Salta la excepcion
        var res = assertThrows(ResponseStatusException.class, () -> {
            tenistasService.update(1L, tenistas.get(1L));
        });

        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("No se puede almacenar pues no existe la raqueta con id: 1"));

        // verificamos que se ha llamado al método
        verify(tenistasRepository, times(1))
                .findById(1L);
        verify(raquetasRepository, times(1))
                .findById(1L);
    }

    @Test
    void updateNombreFails() {
        // Lo que vamos a simular
        when(tenistasRepository.findById(1L))
                .thenReturn(Optional.of(tenistas.get(1L)));
        when(raquetasRepository.findById(1L))
                .thenReturn(Optional.of(RaquetasFactory.getRaquetasDemoData().get(1L)));
        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre no puede estar vacío"))
                .when(tenistaValidator).validate(tenistas.get(1L));

        // Salta la excepcion
        var res = assertThrows(ResponseStatusException.class, () -> {
            tenistasService.update(1L, tenistas.get(1L));
        });
        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("El nombre no puede estar vacío"));

        // verificamos que se ha llamado al método
        verify(tenistasRepository, times(1))
                .findById(1L);
        verify(raquetasRepository, times(1))
                .findById(1L);
        verify(tenistaValidator, times(1))
                .validate(tenistas.get(1L));
        verify(tenistasRepository, times(0))
                .findByRanking(1);
        verify(tenistasRepository, times(0))
                .save(tenistas.get(1L));
    }

    @Test
    void updatePaisFails() {
        // Lo que vamos a simular
        when(tenistasRepository.findById(1L))
                .thenReturn(Optional.of(tenistas.get(1L)));
        when(raquetasRepository.findById(1L))
                .thenReturn(Optional.of(RaquetasFactory.getRaquetasDemoData().get(1L)));
        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "El país no puede estar vacío"))
                .when(tenistaValidator).validate(tenistas.get(1L));

        // Salta la excepcion
        var res = assertThrows(ResponseStatusException.class, () -> {
            tenistasService.update(1L, tenistas.get(1L));
        });
        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("El país no puede estar vacío"));

        // verificamos que se ha llamado al método
        verify(tenistasRepository, times(1))
                .findById(1L);
        verify(raquetasRepository, times(1))
                .findById(1L);
        verify(tenistaValidator, times(1))
                .validate(tenistas.get(1L));
        verify(tenistasRepository, times(0))
                .findByRanking(1);
        verify(tenistasRepository, times(0))
                .save(tenistas.get(1L));
    }

    @Test
    void updateRankingFails() {
        // Lo que vamos a simular
        when(tenistasRepository.findById(1L))
                .thenReturn(Optional.of(tenistas.get(1L)));
        when(raquetasRepository.findById(1L))
                .thenReturn(Optional.of(RaquetasFactory.getRaquetasDemoData().get(1L)));
        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ranking no puede ser 0"))
                .when(tenistaValidator).validate(tenistas.get(1L));

        // Salta la excepcion
        var res = assertThrows(ResponseStatusException.class, () -> {
            tenistasService.update(1L, tenistas.get(1L));
        });
        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("El ranking no puede ser 0"));

        // verificamos que se ha llamado al método
        verify(tenistasRepository, times(1))
                .findById(1L);
        verify(raquetasRepository, times(1))
                .findById(1L);
        verify(tenistaValidator, times(1))
                .validate(tenistas.get(1L));
        verify(tenistasRepository, times(0))
                .findByRanking(1);
        verify(tenistasRepository, times(0))
                .save(tenistas.get(1L));
    }

    @Test
    void updateRankingExistsFails() {
        // Lo que vamos a simular
        when(tenistasRepository.findById(1L))
                .thenReturn(Optional.of(tenistas.get(1L)));
        when(raquetasRepository.findById(1L))
                .thenReturn(Optional.of(RaquetasFactory.getRaquetasDemoData().get(1L)));
        when(tenistasRepository.findByRanking(1))
                .thenReturn(Optional.of(tenistas.get(2L)));

        // Salta la excepcion
        var res = assertThrows(ResponseStatusException.class, () -> {
            tenistasService.update(1L, tenistas.get(1L));
        });

        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("No se puede almacenar pues ya existe un tenista con el mismo ranking: 1 y no es el mismo"));

        // verificamos que se ha llamado al método
        verify(tenistasRepository, times(1))
                .findById(1L);
        verify(raquetasRepository, times(1))
                .findById(1L);
        verify(tenistaValidator, times(1))
                .validate(tenistas.get(1L));
        verify(tenistasRepository, times(1))
                .findByRanking(1);
        verify(tenistasRepository, times(0))
                .save(tenistas.get(1L));
    }

    @Test
    void deleteById() {
        // Lo que vamos a simular
        when(tenistasRepository.findById(1L))
                .thenReturn(Optional.of(tenistas.get(1L)));

        doNothing().when(tenistasRepository)
                .deleteById(tenistas.get(1L).getId());

        // test
        tenistasService.deleteById(1L);

        // verificamos que se ha llamado al método
        verify(tenistasRepository, times(1))
                .findById(1L);
        verify(tenistasRepository, times(1))
                .deleteById(1L);
    }

    @Test
    void deleteByIdNotFound() {
        when(tenistasRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Salta la excepcion
        var res = assertThrows(ResponseStatusException.class, () -> {
            tenistasService.deleteById(1L);
        });
        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("No se ha encontrado el tenista con id: 1"));

        // Verificamos que se ha llamado al método
        verify(tenistasRepository, times(1))
                .findById(1L);
        verify(tenistasRepository, times(0))
                .deleteById(1L);
    }
}