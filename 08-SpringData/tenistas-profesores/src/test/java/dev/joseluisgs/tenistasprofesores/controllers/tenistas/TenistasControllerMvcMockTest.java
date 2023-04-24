package dev.joseluisgs.tenistasprofesores.controllers.tenistas;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.joseluisgs.tenistasprofesores.data.raquetas.RaquetasFactory;
import dev.joseluisgs.tenistasprofesores.data.tenistas.TenistasFactory;
import dev.joseluisgs.tenistasprofesores.dto.raquetas.RaquetaResponseDto;
import dev.joseluisgs.tenistasprofesores.dto.tenistas.TenistaRequestDto;
import dev.joseluisgs.tenistasprofesores.dto.tenistas.TenistaResponseDto;
import dev.joseluisgs.tenistasprofesores.mapper.tenistas.TenistaMapper;
import dev.joseluisgs.tenistasprofesores.models.raquetas.Raqueta;
import dev.joseluisgs.tenistasprofesores.models.tenistas.Tenista;
import dev.joseluisgs.tenistasprofesores.services.tenistas.TenistasServiceImpl;
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

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // Los metodos estan mockeados
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ExtendWith(MockitoExtension.class) // Extensión de Mockito para usarlo
public class TenistasControllerMvcMockTest {
    // Para mapear a JSON
    private final ObjectMapper mapper = new ObjectMapper();
    @MockBean
    TenistasServiceImpl tenistasService;
    @MockBean
    TenistaMapper tenistaMapper;
    @Autowired
    MockMvc mockMvc; // Cliente MVC
    Tenista tenista = TenistasFactory.getTenistasDemoData().get(1L);
    Raqueta raqueta = RaquetasFactory.getRaquetasDemoData().get(1L);
    RaquetaResponseDto raquetaResponseDto = new RaquetaResponseDto(
            raqueta.getId(),
            raqueta.getUuid(),
            raqueta.getMarca(),
            raqueta.getModelo(),
            raqueta.getPrecio(),
            raqueta.getImagen()
    );
    TenistaResponseDto tenistaResponseDto = new TenistaResponseDto(
            tenista.getId(),
            tenista.getUuid(),
            tenista.getNombre(),
            tenista.getRanking(),
            tenista.getPais(),
            tenista.getImagen(),
            raquetaResponseDto
    );

    TenistaRequestDto tenistaRequestDto = new TenistaRequestDto(
            tenista.getNombre(),
            tenista.getRanking(),
            tenista.getPais(),
            tenista.getImagen(),
            1L
    );
    String myEndpoint = "/api/tenistas";
    // Para los test de JSON
    @Autowired
    private JacksonTester<TenistaRequestDto> jsonTenistaRequestDto;
    @Autowired
    private JacksonTester<TenistaResponseDto> jsonTenistaResponseDto;

    @Autowired
    public TenistasControllerMvcMockTest(TenistasServiceImpl tenistasService, TenistaMapper tenistaMapper) {
        this.tenistasService = tenistasService;
        this.tenistaMapper = tenistaMapper;
    }

    @Test
    @Order(1)
    void findAllTest() throws Exception {
        // Lo que voy a simular
        // Lo que vamos a simular
        when(tenistasService.findAll())
                .thenReturn(List.of(tenista));
        when(tenistaMapper.toResponse(List.of(tenista)))
                .thenReturn(List.of(tenistaResponseDto));

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        get(myEndpoint)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        ObjectMapper mapper = new ObjectMapper();
        List<TenistaResponseDto> res = mapper.readValue(response.getContentAsString(),
                mapper.getTypeFactory().constructCollectionType(List.class, TenistaResponseDto.class));

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertTrue(response.getContentAsString().contains("\"id\":" + tenista.getId())),
                () -> assertTrue(res.size() > 0),
                () -> assertTrue(res.stream().anyMatch(r -> r.getId().equals(tenista.getId())))
        );

        // Verifico que se ha llamado al servicio
        // Verificamos que se ha llamado al método
        Mockito.verify(tenistasService, times(1))
                .findAll();
        Mockito.verify(tenistaMapper, times(1))
                .toResponse(List.of(tenista));
    }

    @Test
    @Order(2)
    void findByIdTest() throws Exception {
        // Lo que voy a simular
        // Lo que vamos a simular
        when(tenistasService.findById(tenista.getId()))
                .thenReturn(tenista);
        when(tenistaMapper.toResponse(tenista))
                .thenReturn(tenistaResponseDto);

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        get(myEndpoint + "/" + tenista.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        ObjectMapper mapper = new ObjectMapper();
        TenistaResponseDto res = mapper.readValue(response.getContentAsString(), TenistaResponseDto.class);

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertTrue(response.getContentAsString().contains("\"id\":" + tenista.getId())),
                () -> assertEquals(res.getId(), tenista.getId())
        );

        // Verifico que se ha llamado al servicio
        // Verificamos que se ha llamado al método
        Mockito.verify(tenistasService, times(1))
                .findById(tenista.getId());
        Mockito.verify(tenistaMapper, times(1))
                .toResponse(tenista);
    }

    @Test
    @Order(3)
    void findByUuid() throws Exception {
        // Lo que voy a simular
        // Lo que vamos a simular
        when(tenistasService.findByUuid(tenista.getUuid()))
                .thenReturn(tenista);
        when(tenistaMapper.toResponse(tenista))
                .thenReturn(tenistaResponseDto);

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        get(myEndpoint + "/find/" + tenista.getUuid())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        ObjectMapper mapper = new ObjectMapper();
        TenistaResponseDto res = mapper.readValue(response.getContentAsString(), TenistaResponseDto.class);

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertTrue(response.getContentAsString().contains("\"id\":" + tenista.getId())),
                () -> assertEquals(res.getId(), tenista.getId())
        );

        // Verifico que se ha llamado al servicio
        // Verificamos que se ha llamado al método
        Mockito.verify(tenistasService, times(1))
                .findByUuid(tenista.getUuid());
        Mockito.verify(tenistaMapper, times(1))
                .toResponse(tenista);
    }

    @Test
    @Order(4)
    void findByNombre() throws Exception {
        // Lo que voy a simular
        // Lo que vamos a simular
        when(tenistasService.findAllByNombre(tenista.getNombre()))
                .thenReturn(List.of(tenista));
        when(tenistaMapper.toResponse(List.of(tenista)))
                .thenReturn(List.of(tenistaResponseDto));

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        get(myEndpoint + "?nombre=" + tenista.getNombre())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        ObjectMapper mapper = new ObjectMapper();
        List<TenistaResponseDto> res = mapper.readValue(response.getContentAsString(),
                mapper.getTypeFactory().constructCollectionType(List.class, TenistaResponseDto.class));

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertTrue(res.size() > 0),
                () -> assertTrue(res.stream().allMatch(r -> r.getNombre().equals(tenista.getNombre())))
        );

        // Verifico que se ha llamado al servicio
        // Verificamos que se ha llamado al método
        Mockito.verify(tenistasService, times(1))
                .findAllByNombre(tenista.getNombre());
        Mockito.verify(tenistaMapper, times(1))
                .toResponse(List.of(tenista));
    }

    @Test
    @Order(5)
    void findByPais() throws Exception {
        // Lo que voy a simular
        // Lo que vamos a simular
        when(tenistasService.findAllByPais(tenista.getPais()))
                .thenReturn(List.of(tenista));
        when(tenistaMapper.toResponse(List.of(tenista)))
                .thenReturn(List.of(tenistaResponseDto));

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        get(myEndpoint + "?pais=" + tenista.getPais())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        ObjectMapper mapper = new ObjectMapper();
        List<TenistaResponseDto> res = mapper.readValue(response.getContentAsString(),
                mapper.getTypeFactory().constructCollectionType(List.class, TenistaResponseDto.class));

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertTrue(res.size() > 0)
        );

        // Verifico que se ha llamado al servicio
        // Verificamos que se ha llamado al método
        Mockito.verify(tenistasService, times(1))
                .findAllByPais(tenista.getPais());
        Mockito.verify(tenistaMapper, times(1))
                .toResponse(List.of(tenista));
    }

    @Test
    @Order(6)
    void postTenista() throws Exception {
        // Lo que voy a simular
        when(tenistaMapper.toModel(tenistaRequestDto))
                .thenReturn(tenista);
        when(tenistasService.save(tenista))
                .thenReturn(tenista);
        when(tenistaMapper.toResponse(tenista))
                .thenReturn(tenistaResponseDto);

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        post(myEndpoint)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonTenistaRequestDto.write(tenistaRequestDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        ObjectMapper mapper = new ObjectMapper();
        TenistaResponseDto res = mapper.readValue(response.getContentAsString(), TenistaResponseDto.class);

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.CREATED.value()),
                () -> assertTrue(response.getContentAsString().contains("\"id\":" + tenista.getId())),
                () -> assertEquals(res.getId(), tenista.getId())
        );

        // Verifico que se ha llamado al servicio
        // Verificamos que se ha llamado al método
        Mockito.verify(tenistaMapper, times(1))
                .toModel(tenistaRequestDto);
        Mockito.verify(tenistasService, times(1))
                .save(tenista);
        Mockito.verify(tenistaMapper, times(1))
                .toResponse(tenista);
    }

    @Test
    @Order(7)
    void updateTenista() throws Exception {
        // Lo que voy a simular
        when(tenistaMapper.toModel(tenistaRequestDto))
                .thenReturn(tenista);
        when(tenistasService.update(tenista.getId(), tenista))
                .thenReturn(tenista);
        when(tenistaMapper.toResponse(tenista))
                .thenReturn(tenistaResponseDto);

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        put(myEndpoint + "/" + tenista.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonTenistaRequestDto.write(tenistaRequestDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        ObjectMapper mapper = new ObjectMapper();
        TenistaResponseDto res = mapper.readValue(response.getContentAsString(), TenistaResponseDto.class);

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertTrue(response.getContentAsString().contains("\"id\":" + tenista.getId())),
                () -> assertEquals(res.getId(), tenista.getId())
        );

        // Verifico que se ha llamado al servicio
        // Verificamos que se ha llamado al método
        Mockito.verify(tenistaMapper, times(1))
                .toModel(tenistaRequestDto);
        Mockito.verify(tenistasService, times(1))
                .update(tenista.getId(), tenista);
        Mockito.verify(tenistaMapper, times(1))
                .toResponse(tenista);
    }

    @Test
    @Order(8)
    void deleteTenista() throws Exception {
        // Lo que voy a simular
        doNothing().when(tenistasService).deleteById(tenista.getId());

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        delete(myEndpoint + "/" + tenista.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.NO_CONTENT.value())
        );

        // Verificamos que se ha llamado al método
        verify(tenistasService, times(1))
                .deleteById(raqueta.getId());
    }
}
