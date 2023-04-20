# Elementos avanzados con Spring Boot y Spring Web

![logo](https://rubensa.files.wordpress.com/2021/05/spring-boot-logo.png)

- [Elementos avanzados con Spring Boot y Spring Web](#elementos-avanzados-con-spring-boot-y-spring-web)
  - [Patrón DTO](#patrón-dto)
  - [Mapeadores](#mapeadores)
  - [Validadores](#validadores)
  - [Manejo de excepciones y errores](#manejo-de-excepciones-y-errores)
  - [Cache](#cache)
  - [Práctica](#práctica)



## Patrón DTO
Los [dto](https://www.oscarblancarteblog.com/2018/11/30/data-transfer-object-dto-patron-diseno/) son objetos que se usan para transportar datos entre capas. Se usan para evitar que se expongan las entidades de la base de datos o los modelos de nuestra aplicación, asi como se usan para ensamblar distintos objetos, eliminar campos que no queremos que se vean o pasar de un tipo de dato a otro. 

```java
public class RaquetaResponseDto {
    private final Long id;
    private final UUID uuid;
    private final String marca;
    private final String modelo;
    private final Double precio;
    private final String imagen;
}

```

## Mapeadores
Los mapeadores son clases que se encargan de convertir de un tipo de objeto a otro. En este caso, de un dto a un modelo de nuestra aplicación y viceversa. 

```java
public class RaquetaMapper {
    // Aquí iran los metodos para mapear los DTOs a los modelos y viceversa
    // Mapeamos de modelo a DTO
    public RaquetaResponseDto toResponse(Raqueta raqueta) {
        return new RaquetaResponseDto(
                raqueta.getId(),
                raqueta.getUuid(),
                raqueta.getMarca(),
                raqueta.getModelo(),
                raqueta.getPrecio(),
                raqueta.getImagen()

        );
    }

    // Mapeamos de DTO a modelo
    public List<RaquetaResponseDto> toResponse(List<Raqueta> raquetas) {
        return raquetas.stream()
                .map(this::toResponse)
                .toList();
    }
```

## Validadores
Los [validadores](https://www.baeldung.com/spring-boot-bean-validation) son clases que se encargan de validar los datos que nos llegan y lanzar excepciones en caso de que no sean correctos. 

Podemos crear una clase que se encargue de validar los datos de una raqueta:

```java
public class RaquetaValidator {

    public void validate(Raqueta raqueta) {
        if (raqueta.getMarca() == null || raqueta.getMarca().isBlank()) {
            throw new InvalidRaquetaException("La marca no puede ser nula o estar en blanco");
        }
        if (raqueta.getModelo() == null || raqueta.getModelo().isBlank()) {
            throw new InvalidRaquetaException("El modelo no puede ser nulo o estar en blanco");
        }
        if (raqueta.getPrecio() == null || raqueta.getPrecio() < 0) {
            throw new InvalidRaquetaException("El precio no puede ser nulo o negativo");
        }
    }
}
```

O podemos usar el sistema de validación de Spring, que nos permite validar los datos de una forma más sencilla. Para ello, debemos añadir la dependencia de Spring Validation:

```xml
<!-- Starter de Spring Validation -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

De esta manera podemos usar las anotaciones de validación de Spring:

```java
public class TenistaRequestDto {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    @Min(value = 0, message = "El ranking no puede ser negativo")
    private Integer ranking;
    @NotBlank(message = "El país no puede estar vacío")
    private String pais;
    private String imagen;
    @Min(value = 0, message = "El id de la raqueta no puede ser negativo")
    private Long raquetaId; // Id de la raqueta, puede ser null
}
```

Ahora si queremos validar los datos de un dto, podemos usar @Valid en el parámetro del método:

```java
@PostMapping("")
public ResponseEntity<TenistaResponseDto> postTenista(
        @Valid @RequestBody TenistaRequestDto tenista
) {
    log.info("addTenista");
    return ResponseEntity.created(null).body(
            tenistaMapper.toResponse(
                    tenistasService.save(tenistaMapper.toModel(tenista)))
    );
}
```

No debes olvidar añadir un [handler](https://www.baeldung.com/spring-boot-bean-validation#the-exceptionhandler-annotation) anotado como @ExceptionHandler y el código de error para el error @ResponseStatus(HttpStatus.BAD_REQUEST) para capturar estas excepciones en tu controlador:

```java
 // Para capturar los errores de validación
@ResponseStatus(HttpStatus.BAD_REQUEST)
@ExceptionHandler(MethodArgumentNotValidException.class)
public Map<String, String> handleValidationExceptions(
        MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
        String fieldName = ((FieldError) error).getField();
        String errorMessage = error.getDefaultMessage();
        errors.put(fieldName, errorMessage);
    });
    return errors;
}
```


## Manejo de excepciones y errores
Los errores se pueden manejar de dos formas: con excepciones. Desde la versión 5 de Spring, se puede usar [ResponseStatusException](https://www.baeldung.com/spring-response-status-exception). De esta forma, podemos lanzar una excepción y Spring se encarga de convertirla en un error HTTP en base al contenido que se le indica, dando la respuesta adecuada. 

```java
public class RaquetaValidator {

    public void validate(Raqueta raqueta) {
        // las distintas condiciones
        if (raqueta.getMarca() == null || raqueta.getMarca().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "La marca no puede estar vacía");
        }
        if (raqueta.getModelo() == null || raqueta.getModelo().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El modelo no puede estar vacío");
        }
        if (raqueta.getPrecio() == null || raqueta.getPrecio() < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El precio no puede ser negativo");
        }
    }

}
```

```java
 @Override
public Raqueta findById(Long id) {
    log.info("findById");
    return raquetasRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No se ha encontrado la raqueta con id: " + id)
    );
}
```

Para mostrar los errores de forma correcta debemos añadir en nuestro fichero de propiedades la siguiente configuración:

```properties
# Para que muestre el mensaje de error de excepciones
server.error.include-message=always
```

## Cache
Para usar [cache](https://www.baeldung.com/spring-cache-tutorial) en Spring Boot, debemos añadir la dependencia de Spring Cache:

```xml
 <!-- Starter de Spring Cache -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```

Y añadir la anotación @EnableCaching en la clase principal de nuestra aplicación y en los servicios que queramos cachear añadimos la anotación @CacheConfig con el nombre de la cache que queramos usar. Aunque esto no es obligatorio, es recomendable usarlo para no tener que repetir el nombre de la cache en cada método. 
```java
@CacheConfig(cacheNames = {"raquetas"})
```

- @Cacheable: Se usa para indicar que un método es cachable. Si el método ya ha sido ejecutado, se devuelve el resultado de la cache. Si no, se ejecuta el método y se guarda el resultado en la cache. Se le puede indicar el nombre de la cache, el key y el tiempo de expiración.
- @CachePut: Se usa para indicar que un método es cachable. Si el método ya ha sido ejecutado, se ejecuta de nuevo y se guarda el resultado en la cache. Se le puede indicar el nombre de la cache, el key y el tiempo de expiración.
- @CacheEvict: Se usa para indicar que un método es cachable. Si el método ya ha sido ejecutado, se elimina el resultado de la cache. Se le puede indicar el nombre de la cache, el key y el tiempo de expiración.

```java
@CacheConfig(cacheNames = {"raquetas"})
public RaquetasCacheado() {

    // ....

    public getRaquetas() {
        return raquetasRepository.findAll();
    }

    @Cacheable
    public Raqueta findById(Long id) {
        //...
    }

    @CachePut(key = "#raqueta.id") // El key es opcional, si no se indica, se usa el valor por defecto
    public Raqueta save(Raqueta raqueta) {
        //...
    }

    @CacheEvict(key = "#id") // El key es opcional, si no se indica, se usa el valor por defecto
    public void deleteById(Long id) {
        //...
    }

}
```

## Práctica

1. Sobre el proyecto anterior.
2. Crea el enpoint de tenistas, sabiendo que un tenista puede usar o no una raqueta (raqueta puede ser nulo).
3. Usa dto para tenistas y raquetas para procesar los request y responses. Embebe en dto de raqueta el id de tenista para la respuestas. No olvides los mappers.
5. Crea los validadores para tenistas y raquetas.
6. Deriva la lógica del controlador a los servicios. El controlador solo contempla el Happy Path (todo ok) y delega en los servicios la lógica de negocio. Son los servicios los que lanzan las excepciones y siendo las respuestas en los casos de error.
7. Cachea los tenistas y raquetas en memoria.