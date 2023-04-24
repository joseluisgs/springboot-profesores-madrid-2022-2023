package dev.joseluisgs.tenistasprofesores.controllers.tenistas;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.joseluisgs.tenistasprofesores.data.tenistas.TenistasFactory;
import dev.joseluisgs.tenistasprofesores.dto.tenistas.TenistaRequestDto;
import dev.joseluisgs.tenistasprofesores.dto.tenistas.TenistaResponseDto;
import dev.joseluisgs.tenistasprofesores.models.tenistas.Tenista;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
// Configuramos el cliente MVC
@AutoConfigureMockMvc
// Configuramos los testers de JSON
@AutoConfigureJsonTesters
// Limpiamos el contexto antes de cada test
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
// Configuramos la base de datos, reemplanzando
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
// Ordenamos los test por orden de ejecución
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TenistasControllerMvcIntegrationTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc; // Cliente MVC

    Tenista tenista = TenistasFactory.getTenistasDemoData().get(1L);
    String myEndpoint = "/api/tenistas";
    // Para los test de JSON
    @Autowired
    private JacksonTester<TenistaRequestDto> jsonTenistaRequestDto;
    @Autowired
    private JacksonTester<TenistaResponseDto> jsonTenistaResponseDto;

    @Test
    @Order(1)
    void findAllTest() throws Exception {

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
    }

    @Test
    @Order(2)
    void findByIdTest() throws Exception {
        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        get(myEndpoint + "/" + tenista.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        TenistaResponseDto res = mapper.readValue(response.getContentAsString(), TenistaResponseDto.class);

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertEquals(res.getId(), tenista.getId())
        );
    }

    @Test
    @Order(3)
    void findByIdNotFound() throws Exception {
        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        get(myEndpoint + "/" + -1000L)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        try {
            TenistaResponseDto res = mapper.readValue(response.getContentAsString(), TenistaResponseDto.class);
        } catch (Exception ignored) {
        }

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value())
        );
    }

    @Test
    @Order(4)
    void findByUuid() throws Exception {
        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        get(myEndpoint + "/find/" + tenista.getUuid())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        TenistaResponseDto res = mapper.readValue(response.getContentAsString(), TenistaResponseDto.class);

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertEquals(res.getUuid(), tenista.getUuid())
        );
    }

    @Test
    @Order(5)
    void findByUuidNotFound() throws Exception {
        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        get(myEndpoint + "/find/" + UUID.randomUUID())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        try {
            TenistaResponseDto res = mapper.readValue(response.getContentAsString(), TenistaResponseDto.class);
        } catch (Exception ignored) {
        }

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value())
        );
    }

    @Test
    @Order(6)
    void findByNombre() throws Exception {
        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        get(myEndpoint + "?nombre=" + tenista.getNombre())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        List<TenistaResponseDto> res = mapper.readValue(response.getContentAsString(),
                mapper.getTypeFactory().constructCollectionType(List.class, TenistaResponseDto.class));

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertTrue(res.size() > 0),
                () -> assertTrue(res.stream().allMatch(r -> r.getNombre().equals(tenista.getNombre())))
        );
    }

    @Test
    @Order(6)
    void findByPais() throws Exception {
        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        get(myEndpoint + "?pais=" + tenista.getPais())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        List<TenistaResponseDto> res = mapper.readValue(response.getContentAsString(),
                mapper.getTypeFactory().constructCollectionType(List.class, TenistaResponseDto.class));

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertTrue(res.size() > 0)
        );
    }

    // Post
    @Test
    @Order(7)
    void postTenista() throws Exception {
        // Creo la raqueta
        TenistaRequestDto newTenistaDto = new TenistaRequestDto(
                "Tenista Nuevo",
                99,
                "Pais Nuevo",
                "Imagen new",
                1L
        );

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        post(myEndpoint)
                                .contentType(MediaType.APPLICATION_JSON)
                                // Le paso el body
                                .content(jsonTenistaRequestDto.write(newTenistaDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        TenistaResponseDto res = mapper.readValue(response.getContentAsString(), TenistaResponseDto.class);

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.CREATED.value()),
                () -> assertEquals(res.getNombre(), newTenistaDto.getNombre()),
                () -> assertEquals(res.getPais(), newTenistaDto.getPais()),
                () -> assertEquals(res.getImagen(), newTenistaDto.getImagen())
        );
    }

    @Test
    @Order(8)
    void postFailsNombre() throws Exception {
        // Creo la raqueta
        TenistaRequestDto newTenistaDto = new TenistaRequestDto(
                "",
                99,
                "Pais Nuevo",
                "Imagen new",
                1L
        );

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        post(myEndpoint)
                                .contentType(MediaType.APPLICATION_JSON)
                                // Le paso el body
                                .content(jsonTenistaRequestDto.write(newTenistaDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        try {
            TenistaResponseDto res = mapper.readValue(response.getContentAsString(), TenistaResponseDto.class);
        } catch (Exception ignored) {
        }

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value())
        );

    }

    @Test
    @Order(9)
    void postFailsRanking() throws Exception {
        // Creo la raqueta
        TenistaRequestDto newTenistaDto = new TenistaRequestDto(
                "Tenista Nuevo",
                -99,
                "Pais Nuevo",
                "Imagen new",
                1L
        );

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        post(myEndpoint)
                                .contentType(MediaType.APPLICATION_JSON)
                                // Le paso el body
                                .content(jsonTenistaRequestDto.write(newTenistaDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        try {
            TenistaResponseDto res = mapper.readValue(response.getContentAsString(), TenistaResponseDto.class);
        } catch (Exception ignored) {
        }

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value())
        );
    }

    @Test
    @Order(10)
    void postFailsPais() throws Exception {
        // Creo la raqueta
        TenistaRequestDto newTenistaDto = new TenistaRequestDto(
                "Tenista Nuevo",
                99,
                "",
                "Imagen new",
                1L
        );

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        post(myEndpoint)
                                .contentType(MediaType.APPLICATION_JSON)
                                // Le paso el body
                                .content(jsonTenistaRequestDto.write(newTenistaDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        try {
            TenistaResponseDto res = mapper.readValue(response.getContentAsString(), TenistaResponseDto.class);
        } catch (Exception ignored) {
        }

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value())
        );
    }

    @Test
    @Order(11)
    void updateTenista() throws Exception {
        // Creo la raqueta
        TenistaRequestDto newTenistaDto = new TenistaRequestDto(
                "Tenista Nuevo",
                99,
                "Pais Nuevo",
                "Imagen new",
                1L
        );

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        put(myEndpoint + "/" + tenista.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                // Le paso el body
                                .content(jsonTenistaRequestDto.write(newTenistaDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        TenistaResponseDto res = mapper.readValue(response.getContentAsString(), TenistaResponseDto.class);

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertEquals(res.getNombre(), newTenistaDto.getNombre()),
                () -> assertEquals(res.getPais(), newTenistaDto.getPais()),
                () -> assertEquals(res.getImagen(), newTenistaDto.getImagen())
        );
    }

    @Test
    @Order(12)
    void updateTenistaNotFound() throws Exception {
        // Creo la raqueta
        TenistaRequestDto newTenistaDto = new TenistaRequestDto(
                "Tenista Nuevo",
                99,
                "Pais Nuevo",
                "Imagen new",
                1L
        );

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        put(myEndpoint + "/" + -99)
                                .contentType(MediaType.APPLICATION_JSON)
                                // Le paso el body
                                .content(jsonTenistaRequestDto.write(newTenistaDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        try {
            TenistaResponseDto res = mapper.readValue(response.getContentAsString(), TenistaResponseDto.class);
        } catch (Exception ignored) {
        }

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value())
        );
    }

    // Ahora todos los fallos
    @Test
    @Order(13)
    void updateTenistaFailsNombre() throws Exception {
        // Creo la raqueta
        TenistaRequestDto newTenistaDto = new TenistaRequestDto(
                "",
                99,
                "Pais Nuevo",
                "Imagen new",
                1L
        );

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        put(myEndpoint + "/" + tenista.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                // Le paso el body
                                .content(jsonTenistaRequestDto.write(newTenistaDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        try {
            TenistaResponseDto res = mapper.readValue(response.getContentAsString(), TenistaResponseDto.class);
        } catch (Exception ignored) {
        }

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value())
        );
    }

    @Test
    @Order(14)
    void updateTenistaFailsRanking() throws Exception {
        // Creo la raqueta
        TenistaRequestDto newTenistaDto = new TenistaRequestDto(
                "Tenista Nuevo",
                -99,
                "Pais Nuevo",
                "Imagen new",
                1L
        );

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        put(myEndpoint + "/" + tenista.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                // Le paso el body
                                .content(jsonTenistaRequestDto.write(newTenistaDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        try {
            TenistaResponseDto res = mapper.readValue(response.getContentAsString(), TenistaResponseDto.class);
        } catch (Exception ignored) {
        }

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value())
        );
    }

    @Test
    @Order(15)
    void updateTenistaFailsPais() throws Exception {
        // Creo la raqueta
        TenistaRequestDto newTenistaDto = new TenistaRequestDto(
                "Tenista Nuevo",
                99,
                "",
                "Imagen new",
                1L
        );

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        put(myEndpoint + "/" + tenista.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                // Le paso el body
                                .content(jsonTenistaRequestDto.write(newTenistaDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        try {
            TenistaResponseDto res = mapper.readValue(response.getContentAsString(), TenistaResponseDto.class);
        } catch (Exception ignored) {
        }

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value())
        );
    }

    @Test
    @Order(16)
    void updateTenistaFailsRaqueta() throws Exception {
        // Creo la raqueta
        TenistaRequestDto newTenistaDto = new TenistaRequestDto(
                "Tenista Nuevo",
                99,
                "Pais Nuevo",
                "Imagen new",
                -99L
        );

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        put(myEndpoint + "/" + tenista.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                // Le paso el body
                                .content(jsonTenistaRequestDto.write(newTenistaDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        try {
            TenistaResponseDto res = mapper.readValue(response.getContentAsString(), TenistaResponseDto.class);
        } catch (Exception ignored) {
        }

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value())
        );
    }

    @Test
    @Order(17)
    void deleteTenista() throws Exception {
        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        delete(myEndpoint + "/" + tenista.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.NO_CONTENT.value())
        );
    }

    @Test
    @Order(18)
    void deleteTenistaNotFound() throws Exception {
        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        delete(myEndpoint + "/" + -99)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value())
        );
    }

}
