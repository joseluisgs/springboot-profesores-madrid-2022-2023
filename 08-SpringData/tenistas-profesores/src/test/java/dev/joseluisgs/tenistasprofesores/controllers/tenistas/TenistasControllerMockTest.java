package dev.joseluisgs.tenistasprofesores.controllers.tenistas;

import dev.joseluisgs.tenistasprofesores.data.tenistas.TenistasFactory;
import dev.joseluisgs.tenistasprofesores.dto.raquetas.RaquetaResponseDto;
import dev.joseluisgs.tenistasprofesores.dto.tenistas.TenistaRequestDto;
import dev.joseluisgs.tenistasprofesores.dto.tenistas.TenistaResponseDto;
import dev.joseluisgs.tenistasprofesores.mapper.tenistas.TenistaMapper;
import dev.joseluisgs.tenistasprofesores.models.tenistas.Tenista;
import dev.joseluisgs.tenistasprofesores.services.tenistas.TenistasServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/*
Esta no es la mejor forma de hacerlo, pero es la que veremos ahora, es una clase más para testear
 */

@ExtendWith(MockitoExtension.class) // Extensión de Mockito para usarlo
class TenistasControllerMockTest {
    @Mock
    TenistasServiceImpl tenistasService;

    @Mock
    TenistaMapper tenistasMapper;

    @InjectMocks
    TenistasController tenistasController;

    Tenista tenista = TenistasFactory.getTenistasDemoData().get(1L);

    TenistaResponseDto tenistaResponseDto = new TenistaResponseDto(
            tenista.getId(),
            tenista.getUuid(),
            tenista.getNombre(),
            tenista.getRanking(),
            tenista.getPais(),
            tenista.getImagen(),
            new RaquetaResponseDto(
                    tenista.getRaqueta().getId(),
                    tenista.getRaqueta().getUuid(),
                    tenista.getRaqueta().getMarca(),
                    tenista.getRaqueta().getModelo(),
                    tenista.getRaqueta().getPrecio(),
                    tenista.getRaqueta().getImagen()
            )
    );

    TenistaRequestDto tenistaRequestDto = new TenistaRequestDto(
            tenista.getNombre(),
            tenista.getRanking(),
            tenista.getPais(),
            tenista.getImagen(),
            tenista.getRaqueta().getId()
    );

    @Test
    void getAllTenistas() {
        // Lo que vamos a simular
        // Lo que vamos a simular
        when(tenistasService.findAll())
                .thenReturn(List.of(tenista));
        when(tenistasMapper.toResponse(List.of(tenista)))
                .thenReturn(List.of(tenistaResponseDto));

        // Llamamos al método
        var response = tenistasController.getAllTenistas(null, null);
        var res = response.getBody(); // Conseguimos el cuerpo de la respuesta

        // Comprobamos
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.get(0).getNombre(), tenista.getNombre()),
                () -> assertEquals(res.get(0).getRanking(), tenista.getRanking()),
                () -> assertEquals(res.get(0).getPais(), tenista.getPais()),
                () -> assertEquals(res.get(0).getImagen(), tenista.getImagen())
        );

        // Verificamos que se ha llamado al método
        verify(tenistasService, times(1))
                .findAll();
        verify(tenistasMapper, times(1))
                .toResponse(List.of(tenista));
    }

    @Test
    void getTenistaById() {
        // Lo que vamos a simular
        when(tenistasService.findById(1L))
                .thenReturn(tenista);
        when(tenistasMapper.toResponse(tenista))
                .thenReturn(tenistaResponseDto);

        // Llamamos al método
        var response = tenistasController.getTenistaById(1L);
        // Conseguimos el cuerpo de la respuesta
        var res = response.getBody();

        // Comprobamos
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getNombre(), tenista.getNombre()),
                () -> assertEquals(res.getRanking(), tenista.getRanking()),
                () -> assertEquals(res.getPais(), tenista.getPais()),
                () -> assertEquals(res.getImagen(), tenista.getImagen())
        );

        // Verificamos que se ha llamado al método
        verify(tenistasService, times(1))
                .findById(1L);
        verify(tenistasMapper, times(1))
                .toResponse(tenista);
    }

    @Test
    void getTenistaByIdNotFound() {
        // Lo que vamos a simular
        when(tenistasService.findById(1L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Llamamos al método
        var response = assertThrows(ResponseStatusException.class,
                () -> tenistasController.getTenistaById(1L));

        // Comprobamos
        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value())
        );

        // Verificamos que se ha llamado al método
        verify(tenistasService, times(1))
                .findById(1L);
        verify(tenistasMapper, times(0))
                .toResponse(tenista);
    }

    @Test
    void getTenistaByUuid() {
        // Lo que vamos a simular
        when(tenistasService.findByUuid(tenista.getUuid()))
                .thenReturn(tenista);
        when(tenistasMapper.toResponse(tenista))
                .thenReturn(tenistaResponseDto);

        // Llamamos al método
        var response = tenistasController.getTenistaByUuid(tenista.getUuid());
        // Conseguimos el cuerpo de la respuesta
        var res = response.getBody();

        // Comprobamos
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getNombre(), tenista.getNombre()),
                () -> assertEquals(res.getRanking(), tenista.getRanking()),
                () -> assertEquals(res.getPais(), tenista.getPais()),
                () -> assertEquals(res.getImagen(), tenista.getImagen())
        );

        // Verificamos que se ha llamado al método
        verify(tenistasService, times(1))
                .findByUuid(tenista.getUuid());
        verify(tenistasMapper, times(1))
                .toResponse(tenista);
    }

    @Test
    void getTenistaByUuidNotFound() {
        // Lo que vamos a simular
        when(tenistasService.findByUuid(tenista.getUuid()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Llamamos al método
        var response = assertThrows(ResponseStatusException.class,
                () -> tenistasController.getTenistaByUuid(tenista.getUuid()));

        // Comprobamos
        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value())
        );

        // Verificamos que se ha llamado al método
        verify(tenistasService, times(1))
                .findByUuid(tenista.getUuid());
        verify(tenistasMapper, times(0))
                .toResponse(tenista);
    }

    @Test
    void getTenistaByNombre() {
        // Lo que vamos a simular
        when(tenistasService.findAllByNombre(tenista.getNombre()))
                .thenReturn(List.of(tenista));
        when(tenistasMapper.toResponse(List.of(tenista)))
                .thenReturn(List.of(tenistaResponseDto));

        // Llamamos al método
        var response = tenistasController.getAllTenistas(tenista.getNombre(), null);
        // Conseguimos el cuerpo de la respuesta
        var res = response.getBody();

        // Comprobamos
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.get(0).getNombre(), tenista.getNombre()),
                () -> assertEquals(res.get(0).getRanking(), tenista.getRanking()),
                () -> assertEquals(res.get(0).getPais(), tenista.getPais()),
                () -> assertEquals(res.get(0).getImagen(), tenista.getImagen())
        );

        // Verificamos que se ha llamado al método
        verify(tenistasService, times(1))
                .findAllByNombre(tenista.getNombre());
        verify(tenistasMapper, times(1))
                .toResponse(List.of(tenista));
    }

    @Test
    void getTenistaByPais() {
        // Lo que vamos a simular
        when(tenistasService.findAllByPais(tenista.getPais()))
                .thenReturn(List.of(tenista));
        when(tenistasMapper.toResponse(List.of(tenista)))
                .thenReturn(List.of(tenistaResponseDto));

        // Llamamos al método
        var response = tenistasController.getAllTenistas(null, tenista.getPais());
        // Conseguimos el cuerpo de la respuesta
        var res = response.getBody();

        // Comprobamos
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.get(0).getNombre(), tenista.getNombre()),
                () -> assertEquals(res.get(0).getRanking(), tenista.getRanking()),
                () -> assertEquals(res.get(0).getPais(), tenista.getPais()),
                () -> assertEquals(res.get(0).getImagen(), tenista.getImagen())
        );

        // Verificamos que se ha llamado al método
        verify(tenistasService, times(1))
                .findAllByPais(tenista.getPais());
        verify(tenistasMapper, times(1))
                .toResponse(List.of(tenista));
    }


    @Test
    void postTenista() {
        // Lo que vamos a simular
        when(tenistasMapper.toModel(tenistaRequestDto))
                .thenReturn(tenista);
        when(tenistasService.save(tenista))
                .thenReturn(tenista);
        when(tenistasMapper.toResponse(tenista))
                .thenReturn(tenistaResponseDto);

        // Llamamos al método
        var response = tenistasController.postTenista(tenistaRequestDto);
        // Conseguimos el cuerpo de la respuesta
        var res = response.getBody();

        // Comprobamos
        assertAll(
                () -> assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getNombre(), tenista.getNombre()),
                () -> assertEquals(res.getRanking(), tenista.getRanking()),
                () -> assertEquals(res.getPais(), tenista.getPais()),
                () -> assertEquals(res.getImagen(), tenista.getImagen())
        );

        // Verificamos que se ha llamado al método
        verify(tenistasMapper, times(1))
                .toModel(tenistaRequestDto);
        verify(tenistasService, times(1))
                .save(tenista);
        verify(tenistasMapper, times(1))
                .toResponse(tenista);
    }

    @Test
    void postTenistaFails() {
        // Lo que vamos a simular
        when(tenistasService.save(tenista))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
        when(tenistasMapper.toModel(tenistaRequestDto))
                .thenReturn(tenista);

        // Llamamos al método
        var response = assertThrows(ResponseStatusException.class, () ->
                tenistasController.postTenista(tenistaRequestDto));

        // Comprobamos
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());

        // Verificamos que se ha llamado al método
        verify(tenistasMapper, times(1))
                .toModel(tenistaRequestDto);
        verify(tenistasService, times(1))
                .save(tenista);
    }

    @Test
    void putTenista() {
        // Lo que vamos a simular
        when(tenistasMapper.toModel(tenistaRequestDto))
                .thenReturn(tenista);
        when(tenistasService.update(tenista.getId(), tenista))
                .thenReturn(tenista);
        when(tenistasMapper.toResponse(tenista))
                .thenReturn(tenistaResponseDto);

        // Llamamos al método
        var response = tenistasController.putTenista(tenista.getId(), tenistaRequestDto);
        // Conseguimos el cuerpo de la respuesta
        var res = response.getBody();

        // Comprobamos
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getNombre(), tenista.getNombre()),
                () -> assertEquals(res.getRanking(), tenista.getRanking()),
                () -> assertEquals(res.getPais(), tenista.getPais()),
                () -> assertEquals(res.getImagen(), tenista.getImagen())
        );

        // Verificamos que se ha llamado al método
        verify(tenistasMapper, times(1))
                .toModel(tenistaRequestDto);
        verify(tenistasService, times(1))
                .update(tenista.getId(), tenista);
        verify(tenistasMapper, times(1))
                .toResponse(tenista);
    }

    @Test
    void updateTenistaFails() {
        // Lo que vamos a simular
        when(tenistasService.update(tenista.getId(), tenista))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
        when(tenistasMapper.toModel(tenistaRequestDto))
                .thenReturn(tenista);

        // Llamamos al método
        var response = assertThrows(ResponseStatusException.class, () ->
                tenistasController.putTenista(tenista.getId(), tenistaRequestDto));

        // Comprobamos
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());

        // Verificamos que se ha llamado al método
        verify(tenistasMapper, times(1))
                .toModel(tenistaRequestDto);
        verify(tenistasService, times(1))
                .update(tenista.getId(), tenista);
    }

    @Test
    void deleteRaqueta() {
        // Lo que vamos a simular
        doNothing().when(tenistasService).deleteById(tenista.getId());

        // Llamamos al método
        var response = tenistasController.deleteTenista(tenista.getId());

        // Comprobamos
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode().value());

        // Verificamos que se ha llamado al método
        verify(tenistasService, times(1))
                .deleteById(tenista.getId());
    }

    @Test
    void deleteRaquetaNotFound() {
        // Lo que vamos a simular
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
                .when(tenistasService).deleteById(tenista.getId());

        // Llamamos al método
        var response = assertThrows(ResponseStatusException.class, () -> {
            tenistasController.deleteTenista(tenista.getId());
        });

        // Comprobamos
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());

        // Verificamos que se ha llamado al método
        verify(tenistasService, times(1))
                .deleteById(tenista.getId());
    }

}