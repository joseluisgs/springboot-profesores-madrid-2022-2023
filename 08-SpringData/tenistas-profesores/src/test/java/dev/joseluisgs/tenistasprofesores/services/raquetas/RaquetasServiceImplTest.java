package dev.joseluisgs.tenistasprofesores.services.raquetas;

import dev.joseluisgs.tenistasprofesores.data.raquetas.RaquetasFactory;
import dev.joseluisgs.tenistasprofesores.exceptions.raqueta.RaquetaNotFoundException;
import dev.joseluisgs.tenistasprofesores.models.raquetas.Raqueta;
import dev.joseluisgs.tenistasprofesores.repositories.raquetas.RaquetasRepository;
import dev.joseluisgs.tenistasprofesores.validators.raquetas.RaquetaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class) // Extensión de Mockito para usarlo
class RaquetasServiceImplTest {
    // Datos de demo
    Map<Long, Raqueta> raquetas = RaquetasFactory.getRaquetasDemoData();
    // Creo los mocks
    @Mock
    private RaquetaValidator raquetaValidator;
    @Mock
    private RaquetasRepository raquetasRepository;
    @InjectMocks
    private RaquetasServiceImpl raquetasService;

    @BeforeEach
    void setUp() {
        raquetas = RaquetasFactory.getRaquetasDemoData();
    }


    @Test
    void findAll() {
        // Lo que vamos a simular
        when(raquetasRepository.findAll())
                .thenReturn(List.copyOf(raquetas.values()));

        //test
        var list = raquetasService.findAll();

        // comprobaciones
        assertAll(
                () -> assertNotNull(list),
                () -> assertEquals(3, list.size())
        );

        // verificamos que se ha llamado al método
        verify(raquetasRepository, times(1))
                .findAll();
    }

    @Test
    void findById() {
        // Lo que vamos a simular
        when(raquetasRepository.findById(1L))
                .thenReturn(Optional.of(raquetas.get(1L)));

        // Test
        var raqueta = raquetasService.findById(1L);

        // Comprobaciones
        assertAll(
                () -> assertNotNull(raqueta),
                () -> assertEquals("Babolat", raqueta.getMarca()),
                () -> assertEquals("Pure Aero", raqueta.getModelo()),
                () -> assertEquals(199.95, raqueta.getPrecio())
        );

        // Verificamos que se ha llamado al método
        verify(raquetasRepository, times(1))
                .findById(1L);
    }

    @Test
    void findByIdNotFound() {
        when(raquetasRepository.findById(-100L))
                .thenReturn(Optional.empty());

        // Salta la excepcion
        var res = assertThrows(RaquetaNotFoundException.class, () -> {
            raquetasService.findById(-100L);
        });
        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("No se ha encontrado la raqueta con id: -100"));

        // Verificamos que se ha llamado al método
        verify(raquetasRepository, times(1))
                .findById(-100L);
    }

    @Test
    void findAllByMarca() {
        // Lo que vamos a simular
        when(raquetasRepository.findByMarcaContainsIgnoreCase("Babolat"))
                .thenReturn(List.of(raquetas.get(1L)));

        // Test

        var list = raquetasService.findAllByMarca("Babolat");

        // Comprobaciones
        assertAll(
                () -> assertNotNull(list),
                () -> assertEquals(1, list.size()),
                () -> assertEquals("Babolat", list.get(0).getMarca()),
                () -> assertEquals("Pure Aero", list.get(0).getModelo()),
                () -> assertEquals(199.95, list.get(0).getPrecio())
        );

        // Verificamos que se ha llamado al método
        verify(raquetasRepository, times(1))
                .findByMarcaContainsIgnoreCase("Babolat");
    }

    @Test
    void findByUuid() {
        // Lo que vamos a simular
        when(raquetasRepository.findByUuid(raquetas.get(1L).getUuid()))
                .thenReturn(Optional.of(raquetas.get(1L)));

        // Test
        var raqueta = raquetasService.findByUuid(raquetas.get(1L).getUuid());

        // Comprobaciones
        assertAll(
                () -> assertNotNull(raqueta),
                () -> assertEquals("Babolat", raqueta.getMarca()),
                () -> assertEquals("Pure Aero", raqueta.getModelo()),
                () -> assertEquals(199.95, raqueta.getPrecio())
        );

        // Verificamos que se ha llamado al método
        verify(raquetasRepository, times(1))
                .findByUuid(raquetas.get(1L).getUuid());
    }

    @Test
    void findByUuidNotFound() {
        // Lo que vamos a simular
        when(raquetasRepository.findByUuid(raquetas.get(1L).getUuid()))
                .thenReturn(Optional.empty());

        // Salta la excepcion
        var res = assertThrows(RaquetaNotFoundException.class, () -> {
            raquetasService.findByUuid(raquetas.get(1L).getUuid());
        });

        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("No se ha encontrado la raqueta con uuid: "));

        // Verificamos que se ha llamado al método
        verify(raquetasRepository, times(1))
                .findByUuid(raquetas.get(1L).getUuid());
    }

    @Test
    void save() {
        // Lo que vamos a simular
        when(raquetasRepository.save(raquetas.get(1L)))
                .thenReturn(raquetas.get(1L));

       /* doNothing().when(raquetaValidator)
                .validate(raquetas.get(1L)); // No hace nada, solo no fallar*/

        // Test
        var raqueta = raquetasService.save(raquetas.get(1L));

        // Comprobaciones
        assertAll(
                () -> assertNotNull(raqueta),
                () -> assertEquals("Babolat", raqueta.getMarca()),
                () -> assertEquals("Pure Aero", raqueta.getModelo()),
                () -> assertEquals(199.95, raqueta.getPrecio())
        );

        // Verificamos que se ha llamado al método
        verify(raquetasRepository, times(1))
                .save(raquetas.get(1L));
       /* verify(raquetaValidator, times(1))
                .validate(raquetas.get(1L));*/
    }
/*

    @Test
    void saveFailsMarca() {
        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "La marca no puede estar vacía"))
                .when(raquetaValidator).validate(raquetas.get(1L)); // Simulamos que falla

        // Salta la excepcion
        var res = assertThrows(ResponseStatusException.class, () -> {
            raquetasService.save(raquetas.get(1L));
        });

        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("La marca no puede estar vacía"));

        // Verificamos que se ha llamado al método
        verify(raquetasRepository, times(0))
                .save(raquetas.get(1L));

        verify(raquetaValidator, times(1))
                .validate(raquetas.get(1L));
    }

    @Test
    void saveFailsModelo() {
        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "El modelo no puede estar vacío"))
                .when(raquetaValidator).validate(raquetas.get(1L)); // Simulamos que falla

        // Salta la excepcion
        var res = assertThrows(ResponseStatusException.class, () -> {
            raquetasService.save(raquetas.get(1L));
        });

        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("El modelo no puede estar vacío"));

        // Verificamos que se ha llamado al método
        verify(raquetasRepository, times(0))
                .save(raquetas.get(1L));

        verify(raquetaValidator, times(1))
                .validate(raquetas.get(1L));
    }

    @Test
    void saveFailsPrecio() {
        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "El precio no puede ser negativo"))
                .when(raquetaValidator).validate(raquetas.get(1L)); // Simulamos que falla

        // Salta la excepcion
        var res = assertThrows(ResponseStatusException.class, () -> {
            raquetasService.save(raquetas.get(1L));
        });

        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("El precio no puede ser negativo"));

        // Verificamos que se ha llamado al método
        verify(raquetasRepository, times(0))
                .save(raquetas.get(1L));

        verify(raquetaValidator, times(1))
                .validate(raquetas.get(1L));
    }
*/

    @Test
    void update() {
        // Lo que vamos a simular
        when(raquetasRepository.findById(raquetas.get(1L).getId()))
                .thenReturn(Optional.of(raquetas.get(1L)));

        when(raquetasRepository.save(raquetas.get(1L)))
                .thenReturn(raquetas.get(1L));

        /*doNothing().when(raquetaValidator)
                .validate(raquetas.get(1L)); // No hace nada, solo no fallar
*/
        // Test
        var raqueta = raquetasService.update(raquetas.get(1L).getId(), raquetas.get(1L));

        // Comprobaciones
        assertAll(
                () -> assertNotNull(raqueta),
                () -> assertEquals("Babolat", raqueta.getMarca()),
                () -> assertEquals("Pure Aero", raqueta.getModelo()),
                () -> assertEquals(199.95, raqueta.getPrecio())
        );

        // Verificamos que se ha llamado al método
        verify(raquetasRepository, times(1))
                .findById(raquetas.get(1L).getId());
        verify(raquetasRepository, times(1))
                .save(raquetas.get(1L));
        /*verify(raquetaValidator, times(1))
                .validate(raquetas.get(1L));*/
    }

    @Test
    void updateNotFound() {
        // Lo que vamos a simular
        when(raquetasRepository.findById(raquetas.get(1L).getId()))
                .thenReturn(Optional.empty());

        // Salta la excepcion
        var res = assertThrows(RaquetaNotFoundException.class, () -> {
            raquetasService.update(raquetas.get(1L).getId(), raquetas.get(1L));
        });

        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("No se ha encontrado la raqueta"));

        // Verificamos que se ha llamado al método
        verify(raquetasRepository, times(1))
                .findById(raquetas.get(1L).getId());
        verify(raquetasRepository, times(0))
                .save(raquetas.get(1L));
        verify(raquetaValidator, times(0))
                .validate(raquetas.get(1L));
    }

    // podríamos hacer un test para cada uno de los campos, pero no es necesario
    @Test
    void updateIdNotFound() {
        // Lo que vamos a simular
        when(raquetasRepository.findById(raquetas.get(1L).getId()))
                .thenReturn(Optional.empty());

        // Salta la excepcion
        var res = assertThrows(RaquetaNotFoundException.class, () -> {
            raquetasService.update(1L, raquetas.get(1L));
        });

        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("No se ha encontrado la raqueta"));

        // Verificamos que se ha llamado al método
        verify(raquetasRepository, times(1))
                .findById(1L);
        verify(raquetasRepository, times(0))
                .save(raquetas.get(1L));
        verify(raquetaValidator, times(0))
                .validate(raquetas.get(1L));
    }

    // lo pasa el controlador, por eso no lo testeamos aquñi ya!!!
    /*

    @Test
    void updateFailsMarca() {
        // Lo que vamos a simular
        when(raquetasRepository.findById(raquetas.get(1L).getId()))
                .thenReturn(Optional.of(raquetas.get(1L)));

        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "La marca no puede estar vacía"))
                .when(raquetaValidator).validate(raquetas.get(1L)); // Simulamos que falla

        // Salta la excepcion
        var res = assertThrows(ResponseStatusException.class, () -> {
            raquetasService.update(raquetas.get(1L).getId(), raquetas.get(1L));
        });

        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("La marca no puede estar vacía"));

        // Verificamos que se ha llamado al método
        verify(raquetasRepository, times(1))
                .findById(raquetas.get(1L).getId());
        verify(raquetasRepository, times(0))
                .save(raquetas.get(1L));
        verify(raquetaValidator, times(1))
                .validate(raquetas.get(1L));
    }

    @Test
    void updateFailsModelo() {
        // Lo que vamos a simular
        when(raquetasRepository.findById(raquetas.get(1L).getId()))
                .thenReturn(Optional.of(raquetas.get(1L)));

        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "El modelo no puede estar vacío"))
                .when(raquetaValidator).validate(raquetas.get(1L)); // Simulamos que falla

        // Salta la excepcion
        var res = assertThrows(ResponseStatusException.class, () -> {
            raquetasService.update(raquetas.get(1L).getId(), raquetas.get(1L));
        });

        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("El modelo no puede estar vacío"));

        // Verificamos que se ha llamado al método
        verify(raquetasRepository, times(1))
                .findById(raquetas.get(1L).getId());
        verify(raquetasRepository, times(0))
                .save(raquetas.get(1L));
        verify(raquetaValidator, times(1))
                .validate(raquetas.get(1L));
    }

    @Test
    void updateFailsPrecio() {
        // Lo que vamos a simular
        when(raquetasRepository.findById(raquetas.get(1L).getId()))
                .thenReturn(Optional.of(raquetas.get(1L)));

        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "El precio no puede ser negativo"))
                .when(raquetaValidator).validate(raquetas.get(1L)); // Simulamos que falla

        // Salta la excepcion
        var res = assertThrows(ResponseStatusException.class, () -> {
            raquetasService.update(raquetas.get(1L).getId(), raquetas.get(1L));
        });

        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("El precio no puede ser negativo"));

        // Verificamos que se ha llamado al método
        verify(raquetasRepository, times(1))
                .findById(raquetas.get(1L).getId());
        verify(raquetasRepository, times(0))
                .save(raquetas.get(1L));
        verify(raquetaValidator, times(1))
                .validate(raquetas.get(1L));
    }

   */
    @Test
    void deleteById() {
        // Lo que vamos a simular
        when(raquetasRepository.findById(raquetas.get(1L).getId()))
                .thenReturn(Optional.of(raquetas.get(1L)));

        doNothing().when(raquetasRepository)
                .deleteById(raquetas.get(1L).getId());

        // Test
        raquetasService.deleteById(raquetas.get(1L).getId());

        // Verificamos que se ha llamado al método
        verify(raquetasRepository, times(1))
                .findById(raquetas.get(1L).getId());
        verify(raquetasRepository, times(1))
                .deleteById(raquetas.get(1L).getId());
    }

    @Test
    void deleteByIdNotFound() {
        // Lo que vamos a simular
        when(raquetasRepository.findById(raquetas.get(1L).getId()))
                .thenReturn(Optional.empty());

        // Salta la excepcion
        var res = assertThrows(RaquetaNotFoundException.class, () -> {
            raquetasService.deleteById(raquetas.get(1L).getId());
        });

        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("No se ha encontrado la raqueta con id: "));

        // Verificamos que se ha llamado al método
        verify(raquetasRepository, times(1))
                .findById(raquetas.get(1L).getId());
        verify(raquetasRepository, times(0))
                .deleteById(raquetas.get(1L).getId());
    }
}