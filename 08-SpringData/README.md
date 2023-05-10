# SpringData y Bases de Datos en Spring

![logo](https://rubensa.files.wordpress.com/2021/05/spring-boot-logo.png)

- [SpringData y Bases de Datos en Spring](#springdata-y-bases-de-datos-en-spring)
  - [Spring Data y Spring Data JPA](#spring-data-y-spring-data-jpa)
  - [Repositorios en Spring Data JPA](#repositorios-en-spring-data-jpa)
    - [Creando consultas para nuestros repositorios](#creando-consultas-para-nuestros-repositorios)
  - [Entidades y Modelos](#entidades-y-modelos)
  - [Relaciones entre entidades](#relaciones-entre-entidades)
    - [Opciones de cascada](#opciones-de-cascada)
    - [La opción OrphanRemoval](#la-opción-orphanremoval)
  - [Excepciones Personalizadas](#excepciones-personalizadas)
  - [Cargando Datos de Prueba](#cargando-datos-de-prueba)
  - [Testeando el Repositorio](#testeando-el-repositorio)
  - [Subida de Ficheros](#subida-de-ficheros)
    - [Añadiendo subida de ficheros a nuestros endpoints](#añadiendo-subida-de-ficheros-a-nuestros-endpoints)

## Spring Data y Spring Data JPA

Spring Data es un proyecto dentro del ecosistema de Spring Framework que tiene como objetivo proporcionar una forma más fácil y consistente de interactuar con diferentes tipos de bases de datos desde aplicaciones Java. 

Spring Data ofrece una abstracción de alto nivel sobre las tecnologías de acceso a datos, lo que permite a los desarrolladores centrarse en la lógica de negocio en lugar de preocuparse por los detalles técnicos de la integración con la base de datos. 

Spring Data proporciona soporte para una variedad de tecnologías de acceso a datos, incluyendo bases de datos relacionales, bases de datos NoSQL, bases de datos en memoria y sistemas de archivos. Además, Spring Data proporciona integración con otras tecnologías de Spring, como Spring MVC y Spring Boot, lo que permite a los desarrolladores construir aplicaciones web escalables y fáciles de mantener. 

En resumen, Spring Data es una capa de abstracción que permite a los desarrolladores interactuar con diferentes tipos de bases de datos de una manera coherente y sencilla, reduciendo la complejidad de la integración con la base de datos y acelerando el desarrollo de aplicaciones.

Spring Data JPA es un proyecto dentro del ecosistema de Spring que proporciona una implementación de la API JPA (Java Persistence API) estándar para la persistencia de datos en bases de datos relacionales y se superpone a Hibernate.

JPA es una especificación estándar de Java para el mapeo objeto-relacional (ORM) y proporciona una forma fácil y consistente de interactuar con una base de datos relacional utilizando objetos Java. Spring Data JPA proporciona una capa de abstracción adicional en la parte superior de la API JPA, lo que hace que sea aún más fácil trabajar con bases de datos relacionales que usando Hibernate solamente.

Entre las principales ventajas de Spring Data JPA se encuentran:

- Reduce el código boilerplate: Spring Data JPA elimina gran parte del código repetitivo que normalmente se necesita para interactuar con una base de datos relacional, lo que hace que el código sea más limpio y fácil de mantener.

- Abstracción de bases de datos: Spring Data JPA proporciona una capa de abstracción sobre la base de datos, lo que permite a los desarrolladores escribir código que sea independiente de la base de datos subyacente. Esto significa que puede cambiar fácilmente de una base de datos a otra sin tener que cambiar su código.

- Soporte para repositorios específicos, paginación y ordenamiento: Spring Data JPA proporciona soportes con repositorios específicos, soporte para paginación y ordenamiento de resultados de consultas de forma fácil y sencilla, lo que facilita la implementación de características avanzadas en una aplicación.

- Integración con otras tecnologías de Spring: Spring Data JPA se integra perfectamente con otras tecnologías de Spring, como Spring MVC y Spring Boot, lo que facilita la construcción de aplicaciones web completas.

En resumen, Spring Data JPA proporciona una implementación de la API JPA estándar para la persistencia de datos en bases de datos relacionales y proporciona una capa de abstracción adicional que simplifica el trabajo con bases de datos relacionales en aplicaciones Java.

Para configurar Spring Data JPA en un proyecto, debemos agregar las siguientes dependencias en el archivo pom.xml:

```xml
 <!-- Starter de Spring Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

## Repositorios en Spring Data JPA
En Spring Data JPA, un repositorio (anotado con @Repository) es una interfaz que define una colección de métodos para acceder y manipular datos en una base de datos relacional. Un repositorio permite abstraer la capa de acceso a datos y facilita la implementación de operaciones CRUD (crear, leer, actualizar, eliminar) en la aplicación.

Los métodos definidos en un repositorio son anotados con la anotación `@Query` que permite definir consultas personalizadas y personalizar las consultas generadas automáticamente. Además, Spring Data JPA proporciona una implementación predeterminada para los métodos CRUD básicos, lo que significa que no es necesario escribir código adicional para interactuar con la base de datos.

Existen diferentes tipos de repositorios en Spring Data JPA, algunos de los más comunes son:

- JpaRepository: es una interfaz que extiende la interfaz CrudRepository y agrega funcionalidades específicas para JPA. Proporciona operaciones como guardar, eliminar, actualizar y buscar, además de soportar paginación y ordenamiento de los resultados.

- PagingAndSortingRepository: es una interfaz que extiende la interfaz CrudRepository y agrega soporte para paginación y ordenamiento de los resultados.

- CrudRepository: es la interfaz más básica y proporciona las operaciones CRUD básicas, como guardar, eliminar, actualizar y buscar.

- QueryDslPredicateExecutor: es una interfaz que permite utilizar Querydsl para construir consultas dinámicas, lo que permite personalizar las consultas en tiempo de ejecución.

En resumen, un repositorio en Spring Data JPA es una interfaz que define una colección de métodos para acceder y manipular datos en una base de datos relacional. Los repositorios proporcionan una abstracción de la capa de acceso a datos y facilitan la implementación de operaciones CRUD en la aplicación. Existen diferentes tipos de repositorios que ofrecen funcionalidades específicas para JPA y permiten personalizar las consultas en tiempo de ejecución.

### Creando consultas para nuestros repositorios
A la hora de crear consultas para nuestros repositorios, Spring Data JPA nos ofrece diferentes opciones para personalizar las consultas generadas automáticamente y crear consultas personalizadas.

- Consultas personalizadas: Spring Data JPA nos permite crear consultas personalizadas utilizando la anotación @Query. Esta anotación nos permite definir consultas personalizadas utilizando JPQL (Java Persistence Query Language) o SQL nativo. Además, podemos utilizar la anotación @Param para definir parámetros en la consulta.
- Consultas generadas automáticamente: Spring Data JPA [genera automáticamente consultas](https://www.baeldung.com/spring-data-derived-queries) para los métodos definidos en un repositorio. Por defecto, [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repository-query-keywords) genera consultas utilizando el nombre del método y los parámetros definidos en el método. Sin embargo, podemos personalizar las consultas generadas utilizando la anotación @Query.

```java
public interface UserRepository extends JpaRepository<User, Long> {

    // Consulta personalizada utilizando JPQL
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);

    // Consulta automática utilizando el nombre del método
    Optional<User> findByUsername(String username);
}

```

## Entidades y Modelos
En Spring Data JPA, una entidad es una clase Java que se mapea a una tabla en una base de datos relacional. Para definir una entidad en JPA, se deben seguir los siguientes pasos:

1. Anotar la clase Java con la anotación `@Entity`: Esto indica que la clase Java es una entidad que se debe mapear a una tabla en la base de datos.

2. Especificar el nombre de la tabla en la base de datos: Puedes especificar el nombre de la tabla usando la anotación `@Table`. Si la clase Java se llama "Person", por ejemplo, y quieres que se mapee a una tabla llamada "personas" en la base de datos, deberías usar la siguiente anotación:

```java
@Entity
@Table(name = "personas")
public class Person {
    //...
}
```

3. Especificar el identificador de la entidad: Cada entidad en JPA debe tener un identificador único que se utiliza para acceder y manipular la entidad. El identificador se puede especificar usando la anotación `@Id`. Además, es posible que necesites especificar el tipo de identificador usando otras anotaciones, como `@GeneratedValue` si quieres que el identificador se genere automáticamente.

```java
@Entity
@Table(name = "personas")
public class Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //...
}
```

4. Definir los atributos de la entidad: Los atributos de la entidad corresponden a las columnas en la tabla de la base de datos. Puedes especificar el nombre de la columna utilizando la anotación `@Column`. 

```java
@Entity
@Table(name = "personas")
public class Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "nombre")
    private String name;
    
    @Column(name = "edad")
    private int age;

    //...
}
```

En resumen, para definir una entidad en JPA Spring Data, debes anotar la clase Java con la anotación `@Entity`, especificar el nombre de la tabla en la base de datos utilizando la anotación `@Table`, definir el identificador de la entidad utilizando la anotación `@Id`, y definir los atributos de la entidad utilizando la anotación `@Column`.

## Relaciones entre entidades
En Spring Data JPA, una relación es una asociación entre dos o más entidades. Hay varios tipos de relaciones que se pueden establecer entre entidades, entre los cuales se encuentran:

1. Relación Uno a Uno (OneToOne): Esta relación se establece cuando una entidad se asocia con exactamente otra entidad. Por ejemplo, una entidad "Persona" podría tener una relación Uno a Uno con otra entidad "Dirección".

```java
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @OneToOne(mappedBy = "person")
    private Address address;

    // getters and setters
}

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String street;
    
    private String city;
    
    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    // getters and setters
}
```

2. Relación Uno a Muchos (OneToMany): Esta relación se establece cuando una entidad se asocia con varias instancias de otra entidad. Por ejemplo, una entidad "Equipo" podría tener una relación Uno a Muchos con una entidad "Jugador".

```java
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy = "team")
    private List<Player> players;

    // getters and setters
}

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private int number;
    
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    // getters and setters
}
```

3. Relación Muchos a Muchos (ManyToMany): Esta relación se establece cuando una entidad se asocia con varias instancias de otra entidad y viceversa. Por ejemplo, una entidad "Estudiante" podría tener una relación Muchos a Muchos con una entidad "Curso".

```java
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @ManyToMany(mappedBy = "students")
    private List<Course> courses;

    // getters and setters
}

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @ManyToMany
    @JoinTable(
        name = "course_student",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> students;

    // getters and setters
}
```

En resumen, en Spring Data JPA, las relaciones se establecen mediante anotaciones como `@OneToOne`, `@OneToMany` y `@ManyToMany`. Estas anotaciones indican cómo se asocian las entidades entre sí y pueden proporcionar información sobre la clave externa, la tabla de unión, entre otros detalles necesarios para definir correctamente la relación.

Además, podemos usar las anotaciones `@Embedded` y `@Embeddable` para definir una relación de composición entre entidades. Por ejemplo, una entidad "Persona" podría tener una relación de composición con una entidad "Dirección". Para ello, en la entidad "Persona" podemos agregar la anotación `@Embedded`  de tipo Dirección y en la entidad "Dirección" la anotación `@Embeddable` (se va a embeber en otra entidad y no necesita una tabla propia ni un identificador propio)

```java
@Embeddable
public class Address {
    private String street;
    
    private String city;

    // getters and setters
}

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @Embedded
    private Address address;

    // getters and setters
}
```

### Opciones de cascada
En Spring Data JPA, la cascada es una opción que te permite propagar una acción desde una entidad a otra relacionada. Por ejemplo, si tienes una entidad "Libro" y una entidad "Autor" y estableces una relación Uno a Muchos, si configuras la cascada en la relación, cuando elimines un libro, también se eliminarán todos los autores asociados.

Para configurar la cascada en una relación, debes utilizar la anotación `CascadeType`. Por defecto, la cascada no se habilita, pero puedes agregar opciones según tus necesidades. Algunas de las opciones de cascada disponibles son:

- `CascadeType.ALL`: Propaga todas las acciones (persistir, actualizar, eliminar, refrescar, fusionar y eliminar todo) a las entidades relacionadas.
- `CascadeType.PERSIST`: Propaga la acción de persistencia a las entidades relacionadas.
- `CascadeType.MERGE`: Propaga la acción de fusión a las entidades relacionadas.
- `CascadeType.REMOVE`: Propaga la acción de eliminación a las entidades relacionadas.
- `CascadeType.REFRESH`: Propaga la acción de refresco a las entidades relacionadas.
- `CascadeType.DETACH`: Propaga la acción de desconexión a las entidades relacionadas.

Por ejemplo, si queremos configurar la cascada en una relación Uno a Muchos entre las entidades "Equipo" y "Jugador", podemos hacerlo de la siguiente manera:

```java
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Player> players;

    // getters and setters
}

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private int number;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private Team team;

    // getters and setters
}
```

En este ejemplo, se ha establecido la opción de cascada `CascadeType.ALL` en ambas direcciones de la relación Uno a Muchos, lo que significa que cualquier acción realizada en una entidad se propagará automáticamente a la entidad relacionada.

### La opción OrphanRemoval
La opción `orphanRemoval` en JPA es una característica que permite eliminar automáticamente las entidades relacionadas que ya no están asociadas con la entidad padre. Esto se utiliza cuando la entidad principal es la única que debe tener la referencia a la entidad secundaria, y cuando se elimina la referencia a la entidad secundaria desde la entidad principal, la entidad secundaria ya no tiene ninguna relación con ninguna otra entidad, por lo que se puede eliminar automáticamente.

Por ejemplo, si tienes una entidad `Empleado` que tiene una relación `@OneToMany` con la entidad `Proyecto`, y configuras la opción `orphanRemoval = true` en la relación, cuando eliminas un proyecto de la lista de proyectos de un empleado, también se eliminará automáticamente el proyecto de la base de datos si no hay otra relación existente entre ese proyecto y otro empleado.

Para habilitar la opción `orphanRemoval` en JPA, debes establecerla en `true` en la anotación `@OneToMany` o `@OneToOne`. Por ejemplo:

```java
@Entity
public class Empleado {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String nombre;
    
    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Proyecto> proyectos;
    
    // getters y setters
}

@Entity
public class Proyecto {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String nombre;
    
    @ManyToOne
    private Empleado empleado;
    
    // getters y setters
}
```

En este ejemplo, se ha habilitado la opción `orphanRemoval = true` en la relación `@OneToMany` entre la entidad `Empleado` y `Proyecto`. Esto significa que si un proyecto es eliminado de la lista de proyectos de un empleado, y ese proyecto ya no está asociado con ningún otro empleado, entonces ese proyecto también se eliminará automáticamente de la base de datos.

## Excepciones Personalizadas
En Spring Data JPA, las excepciones personalizadas se utilizan para manejar errores específicos de la aplicación. Por ejemplo, si intentas guardar una entidad con un nombre que ya existe en la base de datos, se lanzará una excepción de tipo `DataIntegrityViolationException`. Sin embargo, en lugar de lanzar esta excepción, puedes crear tu propia excepción personalizada y lanzarla en su lugar. O por ejemplo, si vas a buscar una entidad por su id y no existe, se lanzará una excepción de tipo `EmptyResultDataAccessException`. En lugar de lanzar esta excepción, puedes crear tu propia excepción personalizada y lanzarla en su lugar.

De esta manera podemos manejar los errores de una manera más específica y personalizada. Para crear una excepción personalizada, debes crear una clase que extienda de `RuntimeException` y agregar un constructor que reciba un mensaje y una causa.

Además podemos añadir la anotación `@ResponseStatus` para indicar el estado que queremos devolver cuando salte la excepción. Por ejemplo, si queremos devolver un estado 404, podemos agregar la anotación `@ResponseStatus(HttpStatus.NOT_FOUND)`. Por ejemplo:

```java
// Nos permite devolver un estado cuando salta la excepción
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RaquetaNotFoundException extends RaquetaException {
    // Por si debemos serializar
    @Serial
    private static final long serialVersionUID = 43876691117560211L;

    public RaquetaNotFoundException(String mensaje) {
        super(mensaje);
    }
}
```

## Cargando Datos de Prueba
Se pueden cargar datos de prueba o definir cualquier script sql para ejecutarlo antes de que se inicie la aplicación. Para ello, debemos crear un archivo llamado `data.sql` en la carpeta `resources` y escribir el script sql que queremos ejecutar. Por ejemplo:

```sql
INSERT INTO raqueta (id, marca, modelo, precio) VALUES (1, 'Babolat', 'Pure Drive', 200);
INSERT INTO raqueta (id, marca, modelo, precio) VALUES (2, 'Wilson', 'Blade', 180);
INSERT INTO raqueta (id, marca, modelo, precio) VALUES (3, 'Head', 'Speed', 190);
```

## Testeando el Repositorio
Para testear el repositorio, podemos utilizar la anotación `@DataJpaTest` que nos permite cargar un contexto de Spring Data JPA para testear el repositorio. Esta anotación nos permite testear el repositorio sin tener que cargar todo el contexto de Spring o tener una copia de la Base de Datos limpia. Para ello tendremos una instancia de `TestEntityManager` que nos permite hacer las pruebas.

 Por ejemplo:

```java
@DataJpaTest
class RaquetaRepositoryTest {

    @Autowired
    private RaquetasRepository repository;
    @Autowired
    private TestEntityManager entityManager; // EntityManager para hacer las pruebas

    @Test
    void findAll() {
        // Que queremos
        entityManager.merge(raqueta);
        entityManager.flush();

        var raquetas = repository.findAll();


        assertAll(
                () -> assertNotNull(raquetas),
                () -> assertTrue(raquetas.size() > 0)
        );
    }

    // Test que busca un raqueta de tenis por el id

@Test
    void findById() {
        // que queremos
        var res = entityManager.merge(raqueta);
        entityManager.flush();

        var raqueta = repository.findById(res.getId());

        assertAll(
                () -> assertNotNull(raqueta),
                () -> assertEquals("Babolat", raqueta.get().getMarca()),
                () -> assertEquals("Pure Aero", raqueta.get().getModelo()),
                () -> assertEquals(199.95, raqueta.get().getPrecio())
        );
    }

    @Test
    void saveUpdate() {
        // Que queremos
        var res = entityManager.merge(raqueta);
        entityManager.flush();

        var raqueta = repository.findById(res.getId()).get();
        raqueta.setMarca("Test Update");
        raqueta.setModelo("Test Update");
        raqueta.setPrecio(999.99);

        var raquetaSaved = repository.save(raqueta);

        assertAll(
                () -> assertNotNull(raquetaSaved),
                () -> assertEquals(1L, raquetaSaved.getId()),
                () -> assertEquals("Test Update", raquetaSaved.getMarca()),
                () -> assertEquals("Test Update", raquetaSaved.getModelo()),
                () -> assertEquals(999.99, raquetaSaved.getPrecio())
        );
    }
}
```

El resto de elementos se pueden testear de la misma manera que hemos hecho en el temas anteriores.

## Subida de Ficheros
Como extra vamos a ver cómo subir ficheros a nuestra aplicación, siguiendo las estas [indicaciones](https://spring.io/guides/gs/uploading-files/). Para ello vamos a implementar la interfaz StorageService que nos permitirá guardar los ficheros en el sistema de archivos. Para ello, debemos crear una carpeta llamada `upload-dir` en la raíz del proyecto. Esta carpeta será donde se guarden los ficheros que subamos a nuestra aplicación. 

Para el almacenamiento en disco implementaremos la interfaz `StorageService` como FyleSystemStorageService. Para ello, debemos crear la clase `FileSystemStorageService` que implemente la interfaz `StorageService`.

Es importante destacar los métodos:

```java
/**
 * Método que es capaz de cargar un fichero a partir de su nombre
 * Devuelve un objeto de tipo Resource
 */
@Override
public Resource loadAsResource(String filename) {
    try {
        Path file = load(filename);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new StorageNotFoundException("No se puede leer fichero: " + filename);
        }
    } catch (MalformedURLException e) {
        throw new StorageNotFoundException("No se puede leer fichero: " + filename + " " + e);
    }
}

/**
 * Método que devuelve la URL de un fichero a partir de su nombre
 * Devuelve un objeto de tipo String
 */
@Override
public String getUrl(String filename) {
    return MvcUriComponentsBuilder
            // El segundo argumento es necesario solo cuando queremos obtener la imagen
            // En este caso tan solo necesitamos obtener la URL
            .fromMethodName(FilesController.class, "serveFile", filename, null)
            .build().toUriString();
}
```

Por otro lado, debemos hacer un controlador que nos permita gestionar las peticiones al Servicio de Almacenamiento.

```java
@RestController
@RequestMapping("/api/files")
public class FilesController {
    private StorageService storageService;

    // También podemos inyectar dependencias por el setter
    @Autowired
    public void setStorageService(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping(value = "{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename, HttpServletRequest request) {
        Resource file = storageService.loadAsResource(filename);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(file.getFile().getAbsolutePath());
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede determinar el tipo de fichero");
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(file);
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    // Aunque no es obligatorio, podemos indicar que se consume multipart/form-data
    // Para ficheros usamos, Resuqest part, porque lo tenemos dividido en partes
    public ResponseEntity<Map<String, Object>> uploadFile(
            @RequestPart("file") MultipartFile file) {

        // Almacenamos el fichero y obtenemos su URL
        String urlImagen = null;

        if (!file.isEmpty()) {
            String imagen = storageService.store(file);
            urlImagen = storageService.getUrl(imagen);
            Map<String, Object> response = Map.of("url", urlImagen);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede subir un fichero vacío");
        }
    }

    // Implementar el resto de metodos del servicio que nos interesen...
    // Delete file, listar ficheros, etc....
}
```

### Añadiendo subida de ficheros a nuestros endpoints
Ahora que tenemos la subida de ficheros implementada, es importante que en nuestros modelos tengamos un campo para almacenar la url del fichero asociado.

Lo primero es que en la anotación le vamos a indicar que consume `MediaType.MULTIPART_FORM_DATA_VALUE` para que sea capaz de recibir un fichero.

Además en nuestro controlador, donde queramos subir un fichero, debemos añadir el parámetro `@RequestPart("file") MultipartFile file` para que Spring sea capaz de inyectar el fichero que queremos subir.

Por ejemplo, en nuestro modelo de Raquetas, añadimos el campo `imagen`:

```java
// PATCH: /api/raquetas//imagen/{id}
// consumes = MediaType.MULTIPART_FORM_DATA_VALUE: Indica que el parámetro de la función es un parámetro del cuerpo de la petición HTTP
// @PathVariable: Indica que el parámetro de la función es un parámetro de la URL en este caso {id}
// @RequestPart: Indica que el parámetro de la función es un parámetro del cuerpo de la petición HTTP
// En este caso es un fichero, por lo que se indica con @RequestPart y mMltipartFile
@PatchMapping(value = "/imagen/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<RaquetaResponseDto> nuevoProducto(
        @PathVariable Long id,
        @RequestPart("file") MultipartFile file) {

    log.info("patchRaqueta");

    // Buscamos la raqueta
    if (!file.isEmpty()) {
        String imagen = storageService.store(file);
        String urlImagen = storageService.getUrl(imagen);

        var raqueta = raquetasService.findById(id);
        raqueta.setImagen(urlImagen);

        // Devolvemos el OK
        return ResponseEntity.ok(
                raquetaMapper.toResponse(raquetasService.update(id, raqueta))
        );
    } else {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se ha enviado la imagen");
    }
}
```







