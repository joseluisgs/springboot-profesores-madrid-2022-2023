# Introducción al desarrollo de servicios web con Spring Boot y Spring Web

![logo](https://rubensa.files.wordpress.com/2021/05/spring-boot-logo.png)

- [Introducción al desarrollo de servicios web con Spring Boot y Spring Web](#introducción-al-desarrollo-de-servicios-web-con-spring-boot-y-spring-web)
  - [Spring Boot](#spring-boot)
    - [Creando un proyecto](#creando-un-proyecto)
    - [Punto de Entrada](#punto-de-entrada)
    - [Parametrizando la aplicación](#parametrizando-la-aplicación)
  - [Spring MVC y Spring Web](#spring-mvc-y-spring-web)
  - [Componentes de Spring Boot](#componentes-de-spring-boot)
    - [IoC y DI en SpringBoot](#ioc-y-di-en-springboot)
    - [Creando rutas](#creando-rutas)
    - [Responses](#responses)
    - [Requests](#requests)
      - [Parámetros de ruta](#parámetros-de-ruta)
      - [Parámetros de consulta](#parámetros-de-consulta)
      - [Peticiones con datos serializados](#peticiones-con-datos-serializados)
  - [Postman](#postman)
  - [Práctica](#práctica)



## Spring Boot

[Spring](https://spring.io/) es un framework de Java VM que nos permite crear aplicaciones web de forma rápida y
sencilla. En este caso, usaremos [Spring Boot](https://spring.io/projects/spring-boot), que es una versión simplificada
de Spring que nos ayuda en la configuración de sus elementos. Es decir, nos ofrece unos starter que nos permiten 
configurar los elementos que necesitamos de forma rápida y sencilla y reutilizarlos.

Gracias a estos starter podemos tener los "esqueletos", librerías, configuraciones tipo para hacer api rest, tener acceso a base de datos, distribuir en contenedores, etc.

Spring Boot se caracteriza por implementar el Contenedor
de [inversión de control](https://es.wikipedia.org/wiki/Inversi%C3%B3n_de_control): permite la configuración de los
componentes de aplicación y la administración del ciclo de vida de los objetos Java, se lleva a cabo principalmente a
través de la inyección de dependencias
y [programación orientada a aspectos](https://es.wikipedia.org/wiki/Programaci%C3%B3n_orientada_a_aspectos): habilita la
implementación de rutinas transversales.

![img_3.png](../images/springboot.jpeg)

### Creando un proyecto

Podemos crear un proyecto Spring Boot usando el plugin IntelliJ, desde su web. Con
estos [asistentes](https://start.spring.io/) podemos crear un proyecto Ktor con las opciones que queramos (plugins),
destacamos el routing, el uso de json, etc.

Para nuestro proyecto deberemos usar las siguientes dependencias:
- Lombok: nos permite usar anotaciones para generar código de forma automática.
- Spring Web: nos permite crear aplicaciones web de forma rápida y sencilla.

Posteriormente podemos añadir las dependencias que necesitemos, por ejemplo para usar una base de datos, seguridad, testing, etc.


Si se nos olvida alguna dependencia, podemos añadirla posteriormente desde el fichero pom.xml sin problemas

### Punto de Entrada

El servidor tiene su entrada y configuración en la clase Application. Esta lee la configuración en base
al fichero de configuración(./src/main/resources/application.properties) y a partir de aquí se crea una instancia de
la
clase principal etiquetada con @SpringBootApplication

### Parametrizando la aplicación

La aplicación está parametrizada en el fichero de
configuración application.properties(./src/main/resources/application.properties) que se encuentra en el directorio
resources. En este fichero podemos configurar el puerto, el modo de ejecución, etc.

Podemos tener distintos ficheros por ejemplo para desarrollo y producción.

Propiedades globales: src/main/resources/application.properties
Propiedades de producción: src/main/resources/application-prod.properties
Propiedades de desarrollo: src/main/resources/application-dev.properties
Y luego desde la línea de comandos podemos cargar un perfil concreto de la siguiente manera:

```bash
java -jar -Dspring.profiles.active=prod demo-0.0.1-SNAPSHOT.jar
```

```properties
server.port=${PORT:6963}
# Compresion de datos
server.compression.enabled=${COMPRESS_ENABLED:true}
server.compression.mime-types=text/html,text/xml,text/plain,text/css,application/json,application/javascript
server.compression.min-response-size=1024
# Configuramos el locale en España
spring.web.locale=es_ES
spring.web.locale-resolver=fixed
# directorio de almacenamiento
upload.root-location=uploads
#Indicamos el perfil por defecto (Base de datos y otros)
# dev: developmet. application-dev.properties
# prod: production. application-prod.properties
spring.profiles.active=dev
```

## Spring MVC y Spring Web
Spring MVC es el conjunto de librerías que nos permite crear aplicaciones web de forma rápida y sencilla. Spring Web es nos permite mediante su starter crear un proyecto con las librerías necesarias para crear por ejemplo una API REST obteniendo todas las librerías y configuración básica para ello, por ejemplo tener nuestro propio servidor web para gestionar las peticiones.

## Componentes de Spring Boot

Spring Boot nos ofrece una serie de componentes que nos ayudan a crear aplicaciones web de forma rápida y sencilla.
Nuestros componentes principales se etiquetarán con @ para que el framework Spring lo reconozca (módulo de inversión de
control y posterior inyección de dependencias). Cada uno tiene una misión en nuestra arquitectura:

![img_4.png](../images/components.png)

- Controladores: Se etiquetan como *@Controller* o en nuestro caso al ser una API REST como @RestController. Estos son
  los controladores que se encargan de recibir las peticiones de los usuarios y devolver respuestas.

- Servicios: Se etiquetan como *@Service*. Se encargan de implementar la parte de negocio o infraestructura. En nuestro
  caso puede ser el sistema de almacenamiento o parte de la seguridad y perfiles de usuario.

- Repositorios: Se etiquetan como *@Repository* e implementan la interfaz y operaciones de persistencia de la
  información. En nuestro caso, puede ser una base de datos o una API externa. Podemos extender de repositorios pre
  establecidos o diseñar el nuestro propio.

- Configuración: Se etiquetan como *@Configuration*. Se encargan de configurar los componentes de la aplicación. Se se
  suelen iniciar al comienzo de nuestra aplicación.

- Bean: La anotación *@Bean*, nos sirve para indicar que este bean será administrado por Spring Boot (Spring Container).
  La administración de estos beans se realiza mediante a anotaciones como @Configuration. De esta manera cuando se pida un objeto y esté anotado como  @Bean, Spring Boot se encargará de crearlo y devolverlo.

### IoC y DI en SpringBoot

La Inversión de control (Inversion of Control en inglés, IoC) es un principio de diseño de software en el que el flujo
de ejecución de un programa se invierte respecto a los métodos de programación tradicionales. En su lugar, en la
inversión de control se especifican respuestas deseadas a sucesos o solicitudes de datos concretas, dejando que algún
tipo de entidad o arquitectura externa lleve a cabo las acciones de control que se requieran en el orden necesario y
para el conjunto de sucesos que tengan que ocurrir.

La inyección de dependencias (en inglés Dependency Injection, DI) es un patrón de diseño orientado a objetos, en el que
se suministran objetos a una clase en lugar de ser la propia clase la que cree dichos objetos. Esos objetos cumplen
contratos que necesitan nuestras clases para poder funcionar (de ahí el concepto de dependencia). Nuestras clases no
crean los objetos que necesitan, sino que se los suministra otra clase 'contenedora' que inyectará la implementación
deseada a nuestro contrato.

El contenedor Spring IoC lee el elemento de configuración durante el tiempo de ejecución y luego ensambla el Bean a
través de la configuración. La inyección de dependencia de Spring se puede lograr a través del constructor, el método
Setter y el dominio de entidad. Podemos hacer uso de la anotación **@Autowired** para inyectar la dependencia en el
contexto requerido.

El contenedor llamará al constructor con parámetros al instanciar el bean, y cada parámetro representa la dependencia
que queremos establecer. Spring analizará cada parámetro, primero lo analizará por tipo, pero cuando sea incierto, luego
lo analizará de acuerdo con el nombre del parámetro (obtenga el nombre del parámetro a través de
ParameterNameDiscoverer, implementado por ASM).

```java
public class ProductosRestController {
    private final ProductosRepository productosRepository;

    @Autowired
    public ProductosRestController(ProductosRepository productosRepository) {
        this.productosRepository = productosRepository;
    }
}
```

A nivel de setter
Spring primero instancia el Bean y luego llama al método Setter que debe inyectarse para lograr la inyección de
dependencia. No recomendado

```java
public class ProductosRestController {
    private ProductosRepository productosRepository;

    @Autowired
    public void setProductosRepository(ProductosRepository productosRepository) {
        this.productosRepository = productosRepository;
    }
}
```

### Creando rutas

Para crear las rutas vamos a usar on controlador de tipo **RestController**. Este controlador se encargará de recibir las
peticiones y devolver las respuestas. Para ello vamos a usar las anotaciones de Spring Web.

Las peticiones que vamos a recibir seguiran los verbos HTTP que conocemos: tipo GET (GetMapping), POST (PostMapping), PUT (PutMapping), PATCH (
PatchMapping) y/o DELETE (DeleteMapping). De esta manera podremos hacer las peticiones CRUD que necesitemos.

Además, podemos usar **ResponseEntity** para devolver el código de estado de la respuesta, así como el cuerpo de la misma.

```java
@RestController
public class ProductosRestController {
    private final ProductosRepository productosRepository;

    @Autowired
    public ProductosRestController(ProductosRepository productosRepository) {
        this.productosRepository = productosRepository;
    }

    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> getProducts() {
        return ResponseEntity.ok(productosRepository.findAll());
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productosRepository.findById(id).get());
    }

    @PostMapping("/productos")
    public ResponseEntity<Producto> createProduct(@RequestBody Producto producto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productosRepository.save(producto));
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> updateProduct(@PathVariable Long id, @RequestBody Producto producto) {
        return ResponseEntity.ok(productosRepository.save(producto));
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productosRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
```

### Responses

Para devolver las respuestas vamos a usar la clase **ResponseEntity**. Esta clase nos permite devolver el código de estado HTTP
de la respuesta, así como el cuerpo de la misma.

```java
@GetMapping("/productos")
public ResponseEntity<List<Producto>> getProducts() {
    return ResponseEntity.ok(productosRepository.findAll());
}

@GetMapping("/productos/{id}")
public ResponseEntity<Producto> getProduct(@PathVariable Long id) {
    return ResponseEntity.ok(productosRepository.findById(id).get());
}

@PostMapping("/productos")
public ResponseEntity<Producto> createProduct(@RequestBody Producto producto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(productosRepository.save(producto));
}

@PutMapping("/productos/{id}")
public ResponseEntity<Producto> updateProduct(@PathVariable Long id, @RequestBody Producto producto) {
    return ResponseEntity.ok(productosRepository.save(producto));
}

@DeleteMapping("/productos/{id}")
public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    productosRepository.deleteById(id);
    return ResponseEntity.noContent().build();
}
```

### Requests

Las peticiones podemos hacerlas con usando los verbos http, y las anotaciones de Spring Web: GetMapping, PostMapping,
PutMapping, PatchMapping y DeleteMapping...

#### Parámetros de ruta

Podemos usar los parámetros de ruta para obtener información de la petición. Para ello debemos usar la anotación
@PathVariable

```java
@GetMapping("/productos/{id}")
public ResponseEntity<Producto> getProduct(@PathVariable Long id) {
    return ResponseEntity.ok(productosRepository.findById(id).get());
}
```

#### Parámetros de consulta

Podemos usar los parámetros de consulta para obtener información de la petición. Para ello debemos usar la anotación
**@RequestParam**, si la tipamos como nula, o indicamos que no es requerida, podremos usarla como opcional.

```java
@GetMapping("/productos")
public ResponseEntity<List<Producto>> getProducts(@RequestParam(required = false) nombre: String) {
    if (nombre != null) {
        return ResponseEntity.ok(productosRepository.findByNombre(nombre));
    }
    return ResponseEntity.ok(productosRepository.findAll());
}
```

#### Peticiones con datos serializados

Podemos enviar datos serializados en el cuerpo de la petición. Para ello debemos usar la anotación **@RequestBody**

```java
@PostMapping("/productos")
public ResponseEntity<Producto> createProduct(@RequestBody Producto producto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(productosRepository.save(producto));
}
```

## Postman

Para probar con un cliente nuestro servicio usaremos [Postman](https://www.postman.com/) que es una herramienta de
colaboración para el desarrollo de APIs. Permite a los usuarios crear y compartir colecciones de peticiones HTTP, así
como documentar y probar sus APIs.

El fichero para probar nuestra api lo tienes en la carpera [postman](./postman) y puedes importarlo en tu Postman para
probar el resultado.

![postman](../images/postman.png)


## Práctica

1. Crea un proyecto Spring Boot con las dependencias de Spring Web
2. Crea el modelo Raquetas con los siguientes atributos: id, uuid, marca, modelo, precio, imagen, fecha de creación y
   fecha de actualización
3. Crea el repositorio de Raquetas en base a la colección que quieras
4. Crea el controlador de Raquetas con las siguientes rutas:
   - GET /raquetas: Devuelve todas las raquetas. 200 OK
   - GET /raquetas/{id}: Devuelve la raqueta con el id indicado. 200 OK, 404 NOT FOUND
   - POST /raquetas: Crea una nueva raqueta. 201 CREATED
   - PUT /raquetas/{id}: Actualiza la raqueta con el id indicado: 200 OK, 404 NOT FOUND
   - DELETE /raquetas/{id}: Elimina la raqueta con el id indicado. 204 NO CONTENT, 404 NOT FOUND
5. Prueba las rutas con Postman