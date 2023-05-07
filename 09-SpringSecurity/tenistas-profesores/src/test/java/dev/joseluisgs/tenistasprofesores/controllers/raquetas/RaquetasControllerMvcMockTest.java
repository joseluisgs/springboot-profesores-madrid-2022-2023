package dev.joseluisgs.tenistasprofesores.controllers.raquetas;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.joseluisgs.tenistasprofesores.data.raquetas.RaquetasFactory;
import dev.joseluisgs.tenistasprofesores.dto.raquetas.RaquetaRequestDto;
import dev.joseluisgs.tenistasprofesores.dto.raquetas.RaquetaResponseDto;
import dev.joseluisgs.tenistasprofesores.mapper.raquetas.RaquetaMapper;
import dev.joseluisgs.tenistasprofesores.models.raquetas.Raqueta;
import dev.joseluisgs.tenistasprofesores.services.raquetas.RaquetasServiceImpl;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * Vamos a usar el propio cliente MVC de Spring para testear nuestro controlador
 * Pero ahora para evitar que se conecte realmente vamos a usar un Mock
 * Al final es una mezcla de los otros dos
 * Va más rapido porque en el fondo todo está mockeado y no se hacen las llamadas de verdad
 * Es por eso que este es unitario, y el otro es de integración
 */

// Indicamos que es un test de Spring
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // Los métodos se ejecutan en orden
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ExtendWith(MockitoExtension.class) // Extensión de Mockito para usarlo
public class RaquetasControllerMvcMockTest {
    // Para mapear a JSON
    private final ObjectMapper mapper = new ObjectMapper();
    @MockBean
    RaquetasServiceImpl raquetasService;
    @MockBean
    RaquetaMapper raquetaMapper;
    @Autowired
    MockMvc mockMvc; // Cliente MVC
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
    String myEndpoint = "/api/raquetas";
    // Para los test de JSON
    @Autowired
    private JacksonTester<RaquetaRequestDto> jsonRaquetaRequestDto;
    @Autowired
    private JacksonTester<RaquetaResponseDto> jsonRaquetaResponseDto;

    @Autowired
    public RaquetasControllerMvcMockTest(RaquetasServiceImpl raquetasService, RaquetaMapper raquetaMapper) {
        this.raquetasService = raquetasService;
        this.raquetaMapper = raquetaMapper;
    }

    @Test
    @Order(1)
    void findAllTest() throws Exception {
        // Lo que voy a simular
        // Lo que vamos a simular
        when(raquetasService.findAll())
                .thenReturn(List.of(raqueta));
        when(raquetaMapper.toResponse(List.of(raqueta)))
                .thenReturn(List.of(raquetaResponseDto));

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        get(myEndpoint)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        ObjectMapper mapper = new ObjectMapper();
        List<RaquetaResponseDto> res = mapper.readValue(response.getContentAsString(),
                mapper.getTypeFactory().constructCollectionType(List.class, RaquetaResponseDto.class));

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertTrue(response.getContentAsString().contains("\"id\":" + raqueta.getId())),
                () -> assertTrue(res.size() > 0),
                () -> assertTrue(res.stream().anyMatch(r -> r.getId().equals(raqueta.getId())))
        );

        // Verifico que se ha llamado al servicio
        // Verificamos que se ha llamado al método
        Mockito.verify(raquetasService, times(1))
                .findAll();
        Mockito.verify(raquetaMapper, times(1))
                .toResponse(List.of(raqueta));
    }

    @Test
    @Order(2)
    void findByIdTest() throws Exception {
        // Lo que vamos a simular
        when(raquetasService.findById(raqueta.getId()))
                .thenReturn(raqueta);
        when(raquetaMapper.toResponse(raqueta))
                .thenReturn(raquetaResponseDto);

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        get(myEndpoint + "/" + raqueta.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        RaquetaResponseDto res = mapper.readValue(response.getContentAsString(), RaquetaResponseDto.class);

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertEquals(res.getId(), raqueta.getId())
        );

        // Verificamos que se ha llamado al método
        verify(raquetasService, times(1))
                .findById(raqueta.getId());
        verify(raquetaMapper, times(1))
                .toResponse(raqueta);
    }

    @Test
    @Order(4)
    void findByUuid() throws Exception {
        // Lo que vamos a simular
        when(raquetasService.findByUuid(raqueta.getUuid()))
                .thenReturn(raqueta);
        when(raquetaMapper.toResponse(raqueta))
                .thenReturn(raquetaResponseDto);

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        get(myEndpoint + "/find/" + raqueta.getUuid())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        RaquetaResponseDto res = mapper.readValue(response.getContentAsString(), RaquetaResponseDto.class);

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertEquals(res.getUuid(), raqueta.getUuid())
        );

        // Verificamos que se ha llamado al método
        verify(raquetasService, times(1))
                .findByUuid(raqueta.getUuid());
        verify(raquetaMapper, times(1))
                .toResponse(raqueta);
    }

    @Test
    @Order(6)
    void findByMarca() throws Exception {
        // Lo que vamos a simular
        when(raquetasService.findAllByMarca(raqueta.getMarca()))
                .thenReturn(List.of(raqueta));
        when(raquetaMapper.toResponse(List.of(raqueta)))
                .thenReturn(List.of(raquetaResponseDto));

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        get(myEndpoint + "?marca=" + raqueta.getMarca())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        List<RaquetaResponseDto> res = mapper.readValue(response.getContentAsString(),
                mapper.getTypeFactory().constructCollectionType(List.class, RaquetaResponseDto.class));

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertTrue(res.size() > 0),
                () -> assertTrue(res.stream().allMatch(r -> r.getMarca().equals(raqueta.getMarca())))
        );

        // Verificamos que se ha llamado al método
        verify(raquetasService, times(1))
                .findAllByMarca(raqueta.getMarca());
        verify(raquetaMapper, times(1))
                .toResponse(List.of(raqueta));
    }

    // Post
    @Test
    @Order(7)
    void postRaqueta() throws Exception {

        // Lo que vamos a simular
        when(raquetasService.save(raqueta))
                .thenReturn(raqueta);
        when(raquetaMapper.toModel(raquetaRequestDto))
                .thenReturn(raqueta);
        when(raquetaMapper.toResponse(raqueta))
                .thenReturn(raquetaResponseDto);

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        post(myEndpoint)
                                .contentType(MediaType.APPLICATION_JSON)
                                // Le paso el body
                                .content(jsonRaquetaRequestDto.write(raquetaRequestDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        RaquetaResponseDto res = mapper.readValue(response.getContentAsString(), RaquetaResponseDto.class);

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.CREATED.value()),
                () -> assertEquals(res.getMarca(), raquetaResponseDto.getMarca()),
                () -> assertEquals(res.getModelo(), raquetaResponseDto.getModelo()),
                () -> assertEquals(res.getPrecio(), raquetaResponseDto.getPrecio()),
                () -> assertEquals(res.getImagen(), raquetaResponseDto.getImagen())
        );

        // Verificamos que se ha llamado al método
        verify(raquetasService, times(1))
                .save(raqueta);
        verify(raquetaMapper, times(1))
                .toResponse(raqueta);
        verify(raquetaMapper, times(1))
                .toModel(raquetaRequestDto);
    }

    // No voy a hacer el resto que ya todos sabeis como se hacen

    @Test
    @Order(11)
    void putRaqueta() throws Exception {
        // Lo que vamos a simular
        when(raquetasService.update(raqueta.getId(), raqueta))
                .thenReturn(raqueta);
        when(raquetaMapper.toModel(raquetaRequestDto))
                .thenReturn(raqueta);
        when(raquetaMapper.toResponse(raqueta))
                .thenReturn(raquetaResponseDto);


        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        put(myEndpoint + "/" + raqueta.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                // Le paso el body
                                .content(jsonRaquetaRequestDto.write(raquetaRequestDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        RaquetaResponseDto res = mapper.readValue(response.getContentAsString(), RaquetaResponseDto.class);

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertEquals(res.getMarca(), raquetaResponseDto.getMarca()),
                () -> assertEquals(res.getModelo(), raquetaResponseDto.getModelo()),
                () -> assertEquals(res.getPrecio(), raquetaResponseDto.getPrecio()),
                () -> assertEquals(res.getImagen(), raquetaResponseDto.getImagen())
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
    @Order(16)
    void deleteRaqueta() throws Exception {
        // Lo que vamos a simular
        doNothing().when(raquetasService).deleteById(raqueta.getId());

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        delete(myEndpoint + "/" + raqueta.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.NO_CONTENT.value())
        );

        // Verificamos que se ha llamado al método
        verify(raquetasService, times(1))
                .deleteById(raqueta.getId());
    }

}
