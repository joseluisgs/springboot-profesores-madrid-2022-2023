package dev.joseluisgs.tenistasprofesores.controllers.raquetas;

import dev.joseluisgs.tenistasprofesores.data.raquetas.RaquetasFactory;
import dev.joseluisgs.tenistasprofesores.dto.raquetas.RaquetaRequestDto;
import dev.joseluisgs.tenistasprofesores.dto.raquetas.RaquetaResponseDto;
import dev.joseluisgs.tenistasprofesores.mapper.raquetas.RaquetaMapper;
import dev.joseluisgs.tenistasprofesores.models.raquetas.Raqueta;
import dev.joseluisgs.tenistasprofesores.services.raquetas.RaquetasServiceImpl;
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
class RaquetasControllerMockTest {
    @Mock
    RaquetasServiceImpl raquetasService;

    @Mock
    RaquetaMapper raquetaMapper;

    @InjectMocks
    RaquetasController raquetasController;

    Raqueta raqueta = RaquetasFactory.getRaquetasDemoData().get(1L);
    RaquetaResponseDto raquetaResponseDto = new RaquetaResponseDto(
            raqueta.getId(),
            raqueta.getUuid(),
            raqueta.getMarca(),
            raqueta.getModelo(),
            raqueta.getPrecio(),
            raqueta.getImagen()
    );

    RaquetaRequestDto raquetaRequestDto = new RaquetaRequestDto(
            raqueta.getMarca(),
            raqueta.getModelo(),
            raqueta.getPrecio(),
            raqueta.getImagen()
    );

    @Test
    void getAllRaquetas() {
        // Lo que vamos a simular
        when(raquetasService.findAll())
                .thenReturn(List.of(raqueta));
        when(raquetaMapper.toResponse(List.of(raqueta)))
                .thenReturn(List.of(raquetaResponseDto));

        // Llamamos al método
        var response = raquetasController.getAllRaquetas(null);
        var res = response.getBody(); // Conseguimos el cuerpo de la respuesta

        // Comprobamos
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.get(0).getMarca(), raqueta.getMarca()),
                () -> assertEquals(res.get(0).getModelo(), raqueta.getModelo()),
                () -> assertEquals(res.get(0).getPrecio(), raqueta.getPrecio()),
                () -> assertEquals(res.get(0).getImagen(), raqueta.getImagen())
        );

        // Verificamos que se ha llamado al método
        verify(raquetasService, times(1))
                .findAll();
        verify(raquetaMapper, times(1))
                .toResponse(List.of(raqueta));
    }

    @Test
    void getAllRaquetasByMarca() {
        // Lo que vamos a simular
        when(raquetasService.findAllByMarca(raqueta.getMarca()))
                .thenReturn(List.of(raqueta));
        when(raquetaMapper.toResponse(List.of(raqueta)))
                .thenReturn(List.of(raquetaResponseDto));

        // Llamamos al método
        var response = raquetasController.getAllRaquetas(raqueta.getMarca());
        var res = response.getBody(); // Conseguimos el cuerpo de la respuesta

        // Comprobamos
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.get(0).getMarca(), raqueta.getMarca()),
                () -> assertEquals(res.get(0).getModelo(), raqueta.getModelo()),
                () -> assertEquals(res.get(0).getPrecio(), raqueta.getPrecio()),
                () -> assertEquals(res.get(0).getImagen(), raqueta.getImagen())
        );

        // Verificamos que se ha llamado al método
        verify(raquetasService, times(1))
                .findAllByMarca(raqueta.getMarca());
        verify(raquetaMapper, times(1))
                .toResponse(List.of(raqueta));
    }

    @Test
    void getRaquetaById() {
        // Lo que vamos a simular
        when(raquetasService.findById(raqueta.getId()))
                .thenReturn(raqueta);
        when(raquetaMapper.toResponse(raqueta))
                .thenReturn(raquetaResponseDto);

        // Llamamos al método
        var response = raquetasController.getRaquetaById(raqueta.getId());
        var res = response.getBody(); // Conseguimos el cuerpo de la respuesta

        // Comprobamos
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getMarca(), raqueta.getMarca()),
                () -> assertEquals(res.getModelo(), raqueta.getModelo()),
                () -> assertEquals(res.getPrecio(), raqueta.getPrecio()),
                () -> assertEquals(res.getImagen(), raqueta.getImagen())
        );

        // Verificamos que se ha llamado al método
        verify(raquetasService, times(1))
                .findById(raqueta.getId());
        verify(raquetaMapper, times(1))
                .toResponse(raqueta);
    }

    @Test
    void getRaquetaByIdNotFound() {
        // Lo que vamos a simular
        when(raquetasService.findById(raqueta.getId()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Llamamos al método
        var response = assertThrows(ResponseStatusException.class,
                () -> raquetasController.getRaquetaById(raqueta.getId()));

        // Comprobamos
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());

        // Verificamos que se ha llamado al método
        verify(raquetasService, times(1))
                .findById(raqueta.getId());
    }

    @Test
    void getRaquetaByUuid() {
        // Lo que vamos a simular
        when(raquetasService.findByUuid(raqueta.getUuid()))
                .thenReturn(raqueta);
        when(raquetaMapper.toResponse(raqueta))
                .thenReturn(raquetaResponseDto);

        // Llamamos al método
        var response = raquetasController.getRaquetaByUuid(raqueta.getUuid());
        var res = response.getBody(); // Conseguimos el cuerpo de la respuesta

        // Comprobamos
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getMarca(), raqueta.getMarca()),
                () -> assertEquals(res.getModelo(), raqueta.getModelo()),
                () -> assertEquals(res.getPrecio(), raqueta.getPrecio()),
                () -> assertEquals(res.getImagen(), raqueta.getImagen())
        );

        // Verificamos que se ha llamado al método
        verify(raquetasService, times(1))
                .findByUuid(raqueta.getUuid());
        verify(raquetaMapper, times(1))
                .toResponse(raqueta);
    }

    @Test
    void getRaquetaByUuidNotFound() {
        // Lo que vamos a simular
        when(raquetasService.findByUuid(raqueta.getUuid()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Llamamos al método
        var response = assertThrows(ResponseStatusException.class,
                () -> raquetasController.getRaquetaByUuid(raqueta.getUuid()));

        // Comprobamos
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());

        // Verificamos que se ha llamado al método
        verify(raquetasService, times(1))
                .findByUuid(raqueta.getUuid());
    }

    @Test
    void postRaqueta() {
        // Lo que vamos a simular
        when(raquetasService.save(raqueta))
                .thenReturn(raqueta);
        when(raquetaMapper.toModel(raquetaRequestDto))
                .thenReturn(raqueta);
        when(raquetaMapper.toResponse(raqueta))
                .thenReturn(raquetaResponseDto);

        // Llamamos al método
        var response = raquetasController.postRaqueta(raquetaRequestDto);
        var res = response.getBody(); // Conseguimos el cuerpo de la respuesta

        // Comprobamos
        assertAll(
                () -> assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getMarca(), raqueta.getMarca()),
                () -> assertEquals(res.getModelo(), raqueta.getModelo()),
                () -> assertEquals(res.getPrecio(), raqueta.getPrecio()),
                () -> assertEquals(res.getImagen(), raqueta.getImagen())
        );

        // Verificamos que se ha llamado al método
        verify(raquetasService, times(1))
                .save(raqueta);
        verify(raquetaMapper, times(1))
                .toResponse(raqueta);
        verify(raquetaMapper, times(1))
                .toModel(raquetaRequestDto);
    }

    @Test
    void postRaquetaFails() {
        // Lo que vamos a simular
        when(raquetasService.save(raqueta))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
        when(raquetaMapper.toModel(raquetaRequestDto))
                .thenReturn(raqueta);

        // Llamamos al método
        var response = assertThrows(ResponseStatusException.class, () ->
                raquetasController.postRaqueta(raquetaRequestDto));

        // Comprobamos
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());

        // Verificamos que se ha llamado al método
        verify(raquetasService, times(1))
                .save(raqueta);
        verify(raquetaMapper, times(1))
                .toModel(raquetaRequestDto);
    }

    @Test
    void putRaqueta() {
        // Lo que vamos a simular
        when(raquetasService.update(raqueta.getId(), raqueta))
                .thenReturn(raqueta);
        when(raquetaMapper.toModel(raquetaRequestDto))
                .thenReturn(raqueta);
        when(raquetaMapper.toResponse(raqueta))
                .thenReturn(raquetaResponseDto);

        // Llamamos al método
        var response = raquetasController.putRaqueta(raqueta.getId(), raquetaRequestDto);
        var res = response.getBody(); // Conseguimos el cuerpo de la respuesta

        // Comprobamos
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getMarca(), raqueta.getMarca()),
                () -> assertEquals(res.getModelo(), raqueta.getModelo()),
                () -> assertEquals(res.getPrecio(), raqueta.getPrecio()),
                () -> assertEquals(res.getImagen(), raqueta.getImagen())
        );

        // Verificamos que se ha llamado al método
        verify(raquetasService, times(1))
                .update(raqueta.getId(), raqueta);
        verify(raquetaMapper, times(1))
                .toResponse(raqueta);
        verify(raquetaMapper, times(1))
                .toModel(raquetaRequestDto);
    }

    @Test
    void updateRaquetaIdNotFound() {
        // Lo que vamos a simular
        when(raquetasService.update(raqueta.getId(), raqueta))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        when(raquetaMapper.toModel(raquetaRequestDto))
                .thenReturn(raqueta);

        // Llamamos al método
        var response = assertThrows(ResponseStatusException.class, () -> {
            raquetasController.putRaqueta(raqueta.getId(), raquetaRequestDto);
        });

        // Comprobamos
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());

        // Verificamos que se ha llamado al método
        verify(raquetasService, times(1))
                .update(raqueta.getId(), raqueta);
    }

    @Test
    void updateRaquetaFails() {
        // Lo que vamos a simular
        when(raquetasService.update(raqueta.getId(), raqueta))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
        when(raquetaMapper.toModel(raquetaRequestDto))
                .thenReturn(raqueta);

        // Llamamos al método
        var response = assertThrows(ResponseStatusException.class, () -> {
            raquetasController.putRaqueta(raqueta.getId(), raquetaRequestDto);
        });

        // Comprobamos
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());

        // Verificamos que se ha llamado al método
        verify(raquetasService, times(1))
                .update(raqueta.getId(), raqueta);
    }


    @Test
    void deleteRaqueta() {
        // Lo que vamos a simular
        doNothing().when(raquetasService).deleteById(raqueta.getId());

        // Llamamos al método
        var response = raquetasController.deleteRaqueta(raqueta.getId());

        // Comprobamos
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode().value());

        // Verificamos que se ha llamado al método
        verify(raquetasService, times(1))
                .deleteById(raqueta.getId());
    }

    @Test
    void deleteRaquetaNotFound() {
        // Lo que vamos a simular
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
                .when(raquetasService).deleteById(raqueta.getId());

        // Llamamos al método
        var response = assertThrows(ResponseStatusException.class, () -> {
            raquetasController.deleteRaqueta(raqueta.getId());
        });

        // Comprobamos
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());

        // Verificamos que se ha llamado al método
        verify(raquetasService, times(1))
                .deleteById(raqueta.getId());
    }
}