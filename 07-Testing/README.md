# Iniciación al testing en Spring Boot

![logo](https://rubensa.files.wordpress.com/2021/05/spring-boot-logo.png)

- [Iniciación al testing en Spring Boot](#iniciación-al-testing-en-spring-boot)
  - [Testing](#testing)
  - [Test unitarios](#test-unitarios)
  - [Test de integración](#test-de-integración)
  - [Testeando los controladores](#testeando-los-controladores)

## Testing
El testing es una parte fundamental en el desarrollo de software. Nos permite asegurar que nuestro código funciona correctamente y que no se rompe cuando hacemos cambios en él. Para ello debe es importante que diseñemos nuestros test de forma que sean fáciles de mantener y que nos permitan detectar errores de forma rápida y cubran todos los aspectos de nuestro código.

Tenemos varios niveles de tests:
- Test unitarios: estos tests se encargan de probar una unidad de código (una clase, un método, etc.) de forma aislada. Para ello se suelen utilizar mocks para aislar la unidad de código que estamos probando de las dependencias que tiene.
- Test de integración: estos tests se encargan de probar que las distintas unidades de código funcionan correctamente cuando se integran entre ellas. Para ello se suelen utilizar bases de datos en memoria para simular el acceso a datos.
- Test End-to-End: estos tests se encargan de probar que todo el sistema funciona correctamente. Para ello se suelen utilizar herramientas que simulan un navegador web y que permiten simular las acciones que haría un usuario en la aplicación. Por eejmplo cuando usamos Postman.

## Test unitarios
Para realizar los test unitarios con Spring Boot podemos utilizar [JUnit 5](https://www.baeldung.com/junit-5) y [Mockito](https://www.baeldung.com/mockito-series). Para ello debemos añadir la dependencia de Starter de Test en nuestro proyecto:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

Para realizar los test unitarios en clases que no dependan de otras, no necesitamos realizar mocks. Podremos usar JUnit 5 para realizar los test.

Es importante que los test sean independientes, es decir, que no dependan unos de otros. Para ello debemos asegurarnos que cada test se encarga de inicializar los datos que necesita para funcionar. Para ello podemos utilizar los métodos `@BeforeEach` y `@AfterEach` que se ejecutan antes y después de cada test. También podemos utilizar los métodos `@BeforeAll` y `@AfterAll` que se ejecutan antes y después de todos los test de la clase.

Además, usaremos las aserciones de JUnit 5 para comprobar que el resultado de la ejecución de nuestro código es el esperado. Por ejemplo:
```java
class RaquetasRepositoryImplTest {
    RaquetasRepository repository = new RaquetasRepositoryImpl();

    @BeforeEach
    void setUp() {
        // Inicializamos las el repositorio
        repository = new RaquetasRepositoryImpl();

    }


    @Test
    void findAll() {
        var raquetas = repository.findAll();

        assertAll(
                () -> assertNotNull(raquetas),
                () -> assertEquals(3, raquetas.size())
        );
    }

    @Test
    void findById() {
        var raqueta = repository.findById(1L);

        assertAll(
                () -> assertNotNull(raqueta),
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
}
```

Si estamos testeando controladores o servicios, es posible que necesitemos realizar mocks de las dependencias que tienen. Para ello podemos utilizar Mockito.

Es por ello que debemos extender nuestra clase de test con `@ExtendWith(MockitoExtension.class)` y utilizar la anotación `@Mock` para indicar que queremos que se cree un mock de la dependencia. Además, debemos indicarle a Mockito que inyecte los mocks en la clase que estamos testeando con la anotación `@InjectMocks`.

De esta manera cada vez que se use un método de la clase mockeada, se ejecutará el código que hemos definido en el mock. Des esta manera nos concentraremos en probar el código de la clase que estamos testeando y no el de las dependencias.

Para simular el comportamiento de los mocks, podemos utilizar el método `when` de Mockito. Para verificar que se ha llamado a un método de un mock, podemos utilizar el método `verify` de Mockito.


```java
@ExtendWith(MockitoExtension.class) // Extensión de Mockito para usarlo
class RaquetasServiceImplTest {
    // Datos de demo
    Map<Long, Raqueta> raquetas = RaquetasFactory.getRaquetasDemoData();
    // Creo los mocks
    @Mock
    private RaquetaValidator raquetaValidator;
    @Mock
    private RaquetasRepositoryImpl raquetasRepository;
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
        var res = assertThrows(ResponseStatusException.class, () -> {
            raquetasService.findById(-100L);
        });
        // Comprobamos que la excepción es la esperada
        assert (res.getMessage().contains("No se ha encontrado la raqueta con id: -100"));

        // Verificamos que se ha llamado al método
        verify(raquetasRepository, times(1))
                .findById(-100L);
    }
}
```

## Test de integración
Para hacer los test de integration podemos usar solo JUnit con las clases con sus respectivas dependencias reales. Sin embargo, si queremos hacer los test de integración con Spring Boot, debemos usar la anotación `@SpringBootTest` en la clase de test. De esta manera Spring Boot se encargará de inicializar el contexto de la aplicación y de inyectar las dependencias que necesitemos.

```java
@SpringBootTest
class RaquetasControllerTest {
    @Autowired
    private RaquetasController controller;

    @Test
    void findAll() {
        var list = controller.findAll();

        assertAll(
                () -> assertNotNull(list),
                () -> assertEquals(3, list.size())
        );
    }

    @Test
    void findById() {
        var raqueta = controller.findById(1L);

        assertAll(
                () -> assertNotNull(raqueta),
                () -> assertEquals("Babolat", raqueta.getMarca()),
                () -> assertEquals("Pure Aero", raqueta.getModelo()),
                () -> assertEquals(199.95, raqueta.getPrecio())
        );
    }

    @Test
    void findByIdNotFound() {
        var res = assertThrows(ResponseStatusException.class, () -> {
            controller.findById(-100L);
        });
        assert (res.getMessage().contains("No se ha encontrado la raqueta con id: -100"));
    }
}
```

## Testeando los controladores
Aunque podemos testear los controladores como una clase más, mockeando o integrando, tal y como hemos visto en los apartados anteriores, Spring Boot nos proporciona una serie de herramientas para testear los controladores de una manera más sencilla y se simula a lo que hemos hecho con Postman.

Para ello debemos usar la anotación `@AutoConfigureMockMvc` en la clase de test. De esta manera Spring Boot se encargará de inicializar el contexto de la aplicación y de inyectar las dependencias que necesitemos. Además, nos proporciona un objeto `MockMvc` que nos permite simular las peticiones HTTP. Nos apoyaremos en `@AutoConfigureJsonTesters` para poder convertir los objetos a JSON y viceversa.

```java
/ Indicamos que es un test de Spring
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
}
```

De la misma manera que en apartado anterior, podemos mockear las dependencias de los controladores para que no se conecten a la base de datos. Para ello debemos usar la anotación `@MockBean` en la clase de test. De esta manera Spring Boot se encargará de inicializar el contexto de la aplicación y de inyectar las dependencias que necesitemos. 

```java
/ Indicamos que es un test de Spring
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
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
}
```