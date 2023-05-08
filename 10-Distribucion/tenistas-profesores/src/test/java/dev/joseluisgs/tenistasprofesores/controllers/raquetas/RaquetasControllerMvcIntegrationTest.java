package dev.joseluisgs.tenistasprofesores.controllers.raquetas;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.joseluisgs.tenistasprofesores.data.raquetas.RaquetasFactory;
import dev.joseluisgs.tenistasprofesores.dto.raquetas.RaquetaRequestDto;
import dev.joseluisgs.tenistasprofesores.dto.raquetas.RaquetaResponseDto;
import dev.joseluisgs.tenistasprofesores.models.raquetas.Raqueta;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
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

/**
 * Vamos a usar el propio cliente MVC de Spring para testear nuestro controlador
 * Es como el Postman pero dentro de mi suite de test
 * Es un tes de integración porque vamos a testear el controlador, el servicio y el repositorio
 */

// Indicamos que es un test de Spring
@SpringBootTest
// Configuramos el cliente MVC
@AutoConfigureMockMvc
// Configuramos los testers de JSON
@AutoConfigureJsonTesters
// Limpiamos el contexto antes de cada test
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
// Configuramos la base de datos, reemplanzando
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
// Ordenamos los test por orden de ejecución
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RaquetasControllerMvcIntegrationTest {
    // Para mapear a JSON
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc; // Cliente MVC

    Raqueta raqueta = RaquetasFactory.getRaquetasDemoData().get(1L);
    String myEndpoint = "/api/raquetas";
    // Para los test de JSON
    @Autowired
    private JacksonTester<RaquetaRequestDto> jsonRaquetaRequestDto;
    @Autowired
    private JacksonTester<RaquetaResponseDto> jsonRaquetaResponseDto;

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
        List<RaquetaResponseDto> res = mapper.readValue(response.getContentAsString(),
                mapper.getTypeFactory().constructCollectionType(List.class, RaquetaResponseDto.class));

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertTrue(response.getContentAsString().contains("\"id\":" + raqueta.getId())),
                () -> assertTrue(res.size() > 0),
                () -> assertTrue(res.stream().anyMatch(r -> r.getId().equals(raqueta.getId())))
        );
    }

    @Test
    @Order(2)
    void findByIdTest() throws Exception {
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
            RaquetaResponseDto res = mapper.readValue(response.getContentAsString(), RaquetaResponseDto.class);
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
                        get(myEndpoint + "/find/" + raqueta.getUuid())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        RaquetaResponseDto res = mapper.readValue(response.getContentAsString(), RaquetaResponseDto.class);

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertEquals(res.getUuid(), raqueta.getUuid())
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
            RaquetaResponseDto res = mapper.readValue(response.getContentAsString(), RaquetaResponseDto.class);
        } catch (Exception ignored) {
        }

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value())
        );
    }

    @Test
    @Order(6)
    void findByMarca() throws Exception {
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
    }

    // Post
    @Test
    @Order(7)
    void postRaqueta() throws Exception {
        // Creo la raqueta
        RaquetaRequestDto newRaquetaDto = new RaquetaRequestDto(
                "Marca Nueva",
                "Modelo 1",
                99.99,
                "imagen nueva"
        );

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        post(myEndpoint)
                                .contentType(MediaType.APPLICATION_JSON)
                                // Le paso el body
                                .content(jsonRaquetaRequestDto.write(newRaquetaDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        RaquetaResponseDto res = mapper.readValue(response.getContentAsString(), RaquetaResponseDto.class);

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.CREATED.value()),
                () -> assertEquals(res.getMarca(), newRaquetaDto.getMarca()),
                () -> assertEquals(res.getModelo(), newRaquetaDto.getModelo()),
                () -> assertEquals(res.getPrecio(), newRaquetaDto.getPrecio()),
                () -> assertEquals(res.getImagen(), newRaquetaDto.getImagen())
        );
    }

    @Test
    @Order(8)
    void postFailsMarca() throws Exception {
        // Creo la raqueta
        RaquetaRequestDto newRaquetaDto = new RaquetaRequestDto(
                "",
                "Modelo 1",
                99.99,
                "imagen nueva"
        );

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        post(myEndpoint)
                                .contentType(MediaType.APPLICATION_JSON)
                                // Le paso el body
                                .content(jsonRaquetaRequestDto.write(newRaquetaDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        try {
            RaquetaResponseDto res = mapper.readValue(response.getContentAsString(), RaquetaResponseDto.class);
        } catch (Exception ignored) {
        }

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value())
        );
    }

    @Test
    @Order(9)
    void postFailsModelo() throws Exception {
        // Creo la raqueta
        RaquetaRequestDto newRaquetaDto = new RaquetaRequestDto(
                "Marca Nueva",
                "",
                99.99,
                "imagen nueva"
        );

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        post(myEndpoint)
                                .contentType(MediaType.APPLICATION_JSON)
                                // Le paso el body
                                .content(jsonRaquetaRequestDto.write(newRaquetaDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        try {
            RaquetaResponseDto res = mapper.readValue(response.getContentAsString(), RaquetaResponseDto.class);
        } catch (Exception ignored) {
        }

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value())
        );
    }

    @Test
    @Order(10)
    void postFailsPrecio() throws Exception {
        // Creo la raqueta
        RaquetaRequestDto newRaquetaDto = new RaquetaRequestDto(
                "Marca Nueva",
                "Modelo 1",
                -99.99,
                "imagen nueva"
        );

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        post(myEndpoint)
                                .contentType(MediaType.APPLICATION_JSON)
                                // Le paso el body
                                .content(jsonRaquetaRequestDto.write(newRaquetaDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        try {
            RaquetaResponseDto res = mapper.readValue(response.getContentAsString(), RaquetaResponseDto.class);
        } catch (Exception ignored) {
        }

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value())
        );
    }

    @Test
    @Order(11)
    void putRaqueta() throws Exception {
        // Creo la raqueta
        RaquetaRequestDto newRaquetaDto = new RaquetaRequestDto(
                "Marca Update",
                "Modelo Update",
                99.99,
                "imagen nueva"
        );

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        put(myEndpoint + "/" + raqueta.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                // Le paso el body
                                .content(jsonRaquetaRequestDto.write(newRaquetaDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        RaquetaResponseDto res = mapper.readValue(response.getContentAsString(), RaquetaResponseDto.class);

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertEquals(res.getMarca(), newRaquetaDto.getMarca()),
                () -> assertEquals(res.getModelo(), newRaquetaDto.getModelo()),
                () -> assertEquals(res.getPrecio(), newRaquetaDto.getPrecio()),
                () -> assertEquals(res.getImagen(), newRaquetaDto.getImagen())
        );
    }

    @Test
    @Order(12)
    void putRaquetaIdNotFound() throws Exception {
        // Creo la raqueta
        RaquetaRequestDto newRaquetaDto = new RaquetaRequestDto(
                "Marca Update",
                "Modelo Update",
                99.99,
                "imagen nueva"
        );

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        put(myEndpoint + "/" + -999999)
                                .contentType(MediaType.APPLICATION_JSON)
                                // Le paso el body
                                .content(jsonRaquetaRequestDto.write(newRaquetaDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        try {
            RaquetaResponseDto res = mapper.readValue(response.getContentAsString(), RaquetaResponseDto.class);
        } catch (Exception ignored) {
        }

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value())
        );
    }

    // Podríamos probar todos los errores de los campos

    @Test
    @Order(13)
    void putRaquetaFailsMarca() throws Exception {
        // Creo la raqueta
        RaquetaRequestDto newRaquetaDto = new RaquetaRequestDto(
                "",
                "Modelo Update",
                99.99,
                "imagen nueva"
        );

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        put(myEndpoint + "/" + raqueta.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                // Le paso el body
                                .content(jsonRaquetaRequestDto.write(newRaquetaDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        try {
            RaquetaResponseDto res = mapper.readValue(response.getContentAsString(), RaquetaResponseDto.class);
        } catch (Exception ignored) {
        }

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value())
        );
    }

    @Test
    @Order(14)
    void putRaquetaFailsModelo() throws Exception {
        // Creo la raqueta
        RaquetaRequestDto newRaquetaDto = new RaquetaRequestDto(
                "Marca Update",
                "",
                99.99,
                "imagen nueva"
        );

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        put(myEndpoint + "/" + raqueta.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                // Le paso el body
                                .content(jsonRaquetaRequestDto.write(newRaquetaDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        try {
            RaquetaResponseDto res = mapper.readValue(response.getContentAsString(), RaquetaResponseDto.class);
        } catch (Exception ignored) {
        }

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value())
        );
    }

    @Test
    @Order(15)
    void putRaquetaFailsPrecio() throws Exception {
        // Creo la raqueta
        RaquetaRequestDto newRaquetaDto = new RaquetaRequestDto(
                "Marca Update",
                "Modelo Update",
                -99.99,
                "imagen nueva"
        );

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        put(myEndpoint + "/" + raqueta.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                // Le paso el body
                                .content(jsonRaquetaRequestDto.write(newRaquetaDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        try {
            RaquetaResponseDto res = mapper.readValue(response.getContentAsString(), RaquetaResponseDto.class);
        } catch (Exception ignored) {
        }

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value())
        );

    }

    @Test
    @Order(16)
    void deleteRaqueta() throws Exception {
        // Creo la raqueta
        RaquetaRequestDto newRaquetaDto = new RaquetaRequestDto(
                "Marca Nueva",
                "Modelo 1",
                99.99,
                "imagen nueva"
        );

        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        post(myEndpoint)
                                .contentType(MediaType.APPLICATION_JSON)
                                // Le paso el body
                                .content(jsonRaquetaRequestDto.write(newRaquetaDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        RaquetaResponseDto res = mapper.readValue(response.getContentAsString(), RaquetaResponseDto.class);

        // Consulto el endpoint
        var responseDel = mockMvc.perform(
                        delete(myEndpoint + "/" + res.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertAll(
                () -> assertEquals(responseDel.getStatus(), HttpStatus.NO_CONTENT.value())
        );
    }

    @Test
    @Order(17)
    void deleteRaquetaIdNotFound() throws Exception {
        // Consulto el endpoint
        MockHttpServletResponse response = mockMvc.perform(
                        delete(myEndpoint + "/" + -999999)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Proceso la respuesta
        try {
            RaquetaResponseDto res = mapper.readValue(response.getContentAsString(), RaquetaResponseDto.class);
        } catch (Exception ignored) {
        }

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value())
        );
    }

}
