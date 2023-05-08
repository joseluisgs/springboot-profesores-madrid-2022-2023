# SpringSecurity: Autenticación y Autorización

![logo](https://rubensa.files.wordpress.com/2021/05/spring-boot-logo.png)

- [SpringSecurity: Autenticación y Autorización](#springsecurity-autenticación-y-autorización)
  - [Spring Security](#spring-security)
    - [Usuarios y permisos](#usuarios-y-permisos)
    - [UserDetailsService](#userdetailsservice)
    - [PasswordEncoder](#passwordencoder)
    - [AuthenticationProvider](#authenticationprovider)
    - [AuthenticationManager](#authenticationmanager)
    - [JWT Tokens](#jwt-tokens)
    - [AuthenticantionFilter](#authenticantionfilter)
    - [Security Config](#security-config)
      - [Permisos de acceso a los endpoints](#permisos-de-acceso-a-los-endpoints)
      - [AuthenticationPrincipal](#authenticationprincipal)
  - [SSL](#ssl)

## Spring Security
Spring Security es un marco de seguridad de nivel empresarial para aplicaciones web basadas en Java. Proporciona una amplia gama de características de seguridad para proteger aplicaciones web contra vulnerabilidades como la autenticación y la autorización, la gestión de roles de usuario, la protección contra ataques CSRF, la prevención de inyecciones SQL, protección de rutas y muchas otras.

En Spring Boot, la integración con Spring Security es fácil y sencilla, pero su uso no tanto. Para empezar, simplemente necesitas agregar la dependencia de Spring Security en el archivo pom.xml de tu proyecto. Luego, puedes configurar la seguridad de tu aplicación mediante la configuración de seguridad Java o mediante la configuración XML.
```xml
<!-- Spring Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>
```

La configuración de seguridad de Spring Security se basa en el concepto de filtros. Estos filtros se agregan a la cadena de filtros de la aplicación web y proporcionan características de seguridad. Por ejemplo, el filtro de autenticación se utiliza para autenticar a los usuarios y el filtro de autorización se utiliza para proteger los recursos web. De esta manera nosotros podemos proteger los recursos en base a estos filtros y asocialos a los roles de usuario o permisos.

En Spring Security, la autenticación se logra mediante la configuración de proveedores de autenticación. Un proveedor de autenticación es responsable de autenticar a un usuario y proporcionar un objeto UserDetails que contiene los detalles del usuario autenticado. Los proveedores de autenticación pueden ser configurados para autenticar a los usuarios mediante la base de datos, LDAP, OAuth, JWT, entre otros.

La autorización en Spring Security se logra mediante la configuración de reglas de autorización. Las reglas de autorización se utilizan para proteger los recursos web y se pueden configurar para permitir o denegar el acceso a los usuarios basándose en sus roles de usuario en y con ello desencadenar los filtros descritos.

En resumen, Spring Security es una herramienta poderosa para proporcionar seguridad a las aplicaciones web basadas en Spring Boot. Con su amplia gama de características de seguridad y su fácil integración con Spring Boot, puedes asegurarte de que tu aplicación web esté protegida contra vulnerabilidades comunes y garantizar la protección de los datos de tus usuarios.

Iremos desgranando los pasos necesarios para hacerlo

![security](../images/spring-security2.png)

### Usuarios y permisos
Los usuarios y los permisos son conceptos fundamentales en la gestión de la seguridad de una aplicación. En Spring Security, los usuarios y los permisos se definen mediante la configuración de la autenticación y la autorización respectivamente. Para ello usaremos la interfaz UserDetails, que nos permite definir los usuarios y sus permisos. 

Hay varias razones por las que es importante utilizar UserDetails en Spring Security:

1. Representación de los detalles de usuario: UserDetails proporciona una estructura de datos estandarizada para representar los detalles de un usuario autenticado. Esto incluye su nombre de usuario, contraseña, roles, autorizaciones y cualquier otro detalle que necesites.

2. Configuración personalizada de la autenticación: Puedes crear una implementación personalizada de UserDetails para almacenar detalles de usuario adicionales, como información de perfil o datos de contacto. Esto permite personalizar la autenticación y la autorización de la aplicación según las necesidades específicas de tu proyecto.

3. Integración con proveedores de autenticación: UserDetails se integra con los proveedores de autenticación en Spring Security, lo que permite que los detalles de usuario se almacenen y recuperen de forma segura y eficiente. Esto simplifica el proceso de autenticación y autorización y permite la integración con diferentes sistemas de autenticación, como LDAP, base de datos, JWT, OAuth, entre otros.

4. Control de acceso granular: UserDetails proporciona la información necesaria para que los sistemas de autorización puedan tomar decisiones de acceso granulares. Por ejemplo, puedes utilizar los roles y las autorizaciones definidos en UserDetails para permitir o denegar el acceso a recursos específicos de la aplicación en función de los permisos de usuario.

En resumen, UserDetails es un componente crítico en la arquitectura de autenticación y autorización en Spring Security. Proporciona una estructura de datos estandarizada para representar los detalles de usuario, permite la personalización de la autenticación y la integración con diferentes proveedores de autenticación, y permite un control de acceso granular en la aplicación.

En nuestro caso definiremos User y Rol y estos roles tendrán una serie de permisos de usuario. De esta manera podremos definir los permisos de acceso a los recursos de nuestra aplicación con total libertad y respondiendo a las pliticas de acceso que tengamos en nuestro problema.

```java
@Entity
@Table(name = "usuarios")
// Lo importante es que implemente UserDetails
// Esta interfaz nos obliga a implementar los métodos y propiedades para que Spring Security
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER; // Por defecto

    // Perfiles de seguridad en base a sus roles
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    // ....
}
```

```java
public enum Role {

    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    // Podemos añadir permisos de otros roles
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE
            )
    ),
    MANAGER(
            Set.of(
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE
            )
    );

    @Getter
    private final Set<Permission> permissions;

    // Obtenemos los permisos del role en formato SimpleGrantedAuthority de Spring
    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
```

```java
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    MANAGER_READ("management:read"),
    MANAGER_UPDATE("management:update"),
    MANAGER_CREATE("management:create"),
    MANAGER_DELETE("management:delete"),

    USER_READ("user:read");

    @Getter
    private final String permission;
}
```

### UserDetailsService
UserDetailsService es una interfaz en Spring Security que se utiliza para cargar los detalles de un usuario en la aplicación a partir de un origen de datos, como una base de datos o un servicio web. Es un componente esencial en el proceso de autenticación y autorización en Spring Security, ya que proporciona los detalles necesarios del usuario para la autenticación y la autorización.

En concreto, UserDetailsService es responsable de cargar los detalles del usuario a partir de una fuente de datos y devolverlos como un objeto UserDetails, que contiene la información del usuario, como su nombre de usuario, contraseña, roles y autorizaciones. Estos detalles son utilizados por el AuthenticationManager para autenticar al usuario y por el sistema de autorización para determinar los permisos del usuario.

Es importante destacar que UserDetailsService no se utiliza para autenticar al usuario en sí mismo, sino para cargar sus detalles de usuario para que el AuthenticationManager pueda autenticar al usuario. La autenticación en sí se realiza en el AuthenticationManager, que utiliza los detalles del usuario devueltos por UserDetailsService para autenticar al usuario.

En resumen, UserDetailsService es una interfaz en Spring Security que se utiliza para cargar los detalles de un usuario en la aplicación a partir de un origen de datos. Proporciona los detalles necesarios del usuario para la autenticación y la autorización, y es esencial en el proceso de autenticación y autorización en Spring Security.

En nuestro caso, haremos uso de nuestro repositorio y crearemos un Bean de Configuración que cuando nos pida el método de buscar por username nos devuelva un objeto en base a lo que nos devuelva el repositorio de usuario que implementa esta interfaz. En nuestro caso el username es el mail, que ademas es único. De esta manera vamos a tenerlo disponible inyectándolo en toda la aplicación

```java
@Bean
public UserDetailsService userDetailsService() {
    return username -> repository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
}
```

### PasswordEncoder
PasswordEncoder es una interfaz en Spring Security que se utiliza para codificar y descifrar contraseñas. La codificación de contraseñas es una práctica importante en seguridad de aplicaciones web, ya que las contraseñas se almacenan generalmente en bases de datos y, por lo tanto, es importante protegerlas de posibles amenazas externas, como ataques de hackers.

En concreto, PasswordEncoder se utiliza para codificar la contraseña proporcionada por el usuario antes de almacenarla en la base de datos. La codificación de contraseñas se realiza utilizando algoritmos de cifrado hash, como BCrypt, que toman la contraseña en texto claro y la convierten en una cadena de texto cifrada.

Spring Security proporciona varias implementaciones de PasswordEncoder. En resumen, PasswordEncoder es una interfaz en Spring Security que se utiliza para codificar y descifrar contraseñas. Se utiliza para proteger las contraseñas de posibles amenazas externas y es esencial en la seguridad de las aplicaciones web.

En nuestro caso usaremos BCrypt y usaremos un Bean de configuración para crear un PasswordEncoder y tenerlo disponible inyectándolo en toda la aplicación.

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

### AuthenticationProvider
AuthenticationProvider es una interfaz en Spring Security que se utiliza para autenticar a un usuario en la aplicación. Es una parte clave del proceso de autenticación en Spring Security y permite a los desarrolladores personalizar el proceso de autenticación para satisfacer las necesidades específicas de su aplicación.

En concreto, AuthenticationProvider se utiliza para procesar y autenticar una solicitud de autenticación, que incluye el nombre de usuario y la contraseña proporcionados por el usuario. AuthenticationProvider toma esta información y la compara con la información de autenticación almacenada en la base de datos o en otro origen de datos. Si la información es correcta, el usuario se autentica y se le otorga acceso a la aplicación. Si no, se deniega el acceso.

AuthenticationProvider se utiliza típicamente en conjunto con UserDetailsService y PasswordEncoder en Spring Security. UserDetailsService se utiliza para cargar los detalles del usuario desde la base de datos, incluyendo su nombre de usuario y contraseña cifrada. PasswordEncoder se utiliza para cifrar la contraseña proporcionada por el usuario durante el proceso de autenticación. AuthenticationProvider utiliza esta información para autenticar al usuario.

En resumen, AuthenticationProvider es una interfaz en Spring Security que se utiliza para autenticar a un usuario en la aplicación. Es una parte clave del proceso de autenticación en Spring Security y permite a los desarrolladores personalizar el proceso de autenticación para satisfacer las necesidades específicas de su aplicación. Se utiliza típicamente en conjunto con UserDetailsService y PasswordEncoder.

En nuestro caso, crearemos un Bean de configuración que nos devuelva un objeto de tipo DaoAuthenticationProvider, que es una implementación de AuthenticationProvider. Este objeto tendrá como atributos el UserDetailsService y el PasswordEncoder que hemos creado anteriormente.

DaoAuthenticationProvider es una implementación de la interfaz AuthenticationProvider en Spring Security que utiliza una instancia de UserDetailsService y una implementación de PasswordEncoder para autenticar a un usuario en la aplicación tal y como hemos dicho.

En concreto, DaoAuthenticationProvider utiliza UserDetailsService para cargar los detalles del usuario desde la base de datos, incluyendo su nombre de usuario y contraseña cifrada. PasswordEncoder se utiliza para cifrar la contraseña proporcionada por el usuario durante el proceso de autenticación. DaoAuthenticationProvider utiliza esta información para autenticar al usuario.

DaoAuthenticationProvider es una implementación popular y ampliamente utilizada de AuthenticationProvider en Spring Security debido a su facilidad de uso y flexibilidad. Es fácil de configurar y se integra bien con otras funcionalidades de Spring Security, como el manejo de roles y permisos.

```java
@Bean
public AuthenticationProvider authenticationProvider() {
    // Usamos el anterior UserDetailsService y el PasswordEncoder
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
}
```

### AuthenticationManager
El Authentication Manager es una interfaz en Spring Security que proporciona un mecanismo para autenticar a un usuario en la aplicación. Es responsable de tomar las credenciales del usuario, como el nombre de usuario y la contraseña, y validarlas para determinar si el usuario está autorizado para acceder a los recursos protegidos por la aplicación.

El Authentication Manager se utiliza típicamente en conjunto con los proveedores de autenticación en Spring Security, que son responsables de almacenar y recuperar los detalles de usuario necesarios para autenticar al usuario. Por ejemplo, un proveedor de autenticación puede almacenar detalles de usuario en una base de datos y el Authentication Manager puede utilizar estos detalles para autenticar al usuario.

El Authentication Manager se utiliza en el proceso de autenticación de Spring Security. Cuando un usuario intenta acceder a un recurso protegido, Spring Security intercepta la solicitud y comprueba si el usuario está autenticado. Si el usuario no está autenticado, se le redirige al proceso de autenticación, donde el Authentication Manager intenta autenticar al usuario utilizando las credenciales proporcionadas.

Si la autenticación es exitosa, se crea un objeto de autenticación que contiene los detalles del usuario autenticado y se almacena en el contexto de seguridad de la aplicación. Este objeto de autenticación se utiliza posteriormente en el proceso de autorización para permitir o denegar el acceso a los recursos protegidos por la aplicación en función de los permisos del usuario.

En nuestro caso para crearlo, en base al AutenticationProvider usado, usaremos un Bean de configuración para crear un AuthenticationManager y tenerlo disponible inyectándolo en toda la aplicación.

```java
@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
}
```

### JWT Tokens
En nuestro caso nos basaremos en mecanismos de autenticación basados en JWT (Json Web Token). Para ello lo primero ue deberemos es cargar la librería necesaria.

```xml
<!-- Java JWT -->
<dependency>
    <groupId>com.auth0</groupId>
    <artifactId>java-jwt</artifactId>
    <version>4.4.0</version>
</dependency>
```

JWT (JSON Web Token) es un estándar abierto (RFC 7519) que define un formato compacto y seguro para transmitir información entre partes como un objeto JSON. Los tokens JWT se utilizan comúnmente como un mecanismo de autenticación en aplicaciones web y móviles.

Un token JWT consta de tres partes separadas por un punto (.): el encabezado (header), el cuerpo (payload) y la firma (signature). El encabezado contiene información sobre el tipo de token y el algoritmo de cifrado utilizado para firmar el token. El cuerpo contiene los datos del usuario o cualquier otra información relevante que se desee transmitir. La firma se utiliza para verificar la integridad del token y garantizar que no ha sido manipulado durante la transmisión.

Para utilizar JWT en una aplicación, primero se debe generar un token y enviarlo al cliente como respuesta a una solicitud de inicio de sesión válida. El cliente debe almacenar el token, por ejemplo en la memoria local del navegador o en una cookie segura, y enviarlo en cada solicitud subsiguiente como una cabecera de autorización.

Cuando el servidor recibe una solicitud con un token JWT, primero verifica la firma del token para asegurarse de que no ha sido manipulado durante la transmisión. Si la firma es válida, el servidor decodifica el token y extrae la información necesaria del cuerpo del token para autenticar al usuario y autorizar la solicitud.

JWT ofrece varias ventajas sobre otros mecanismos de autenticación, como las cookies de sesión o los tokens de autenticación basados en servidores. Algunas de las ventajas incluyen:

Portabilidad: Los tokens JWT se pueden transmitir en cualquier medio, como URL, POST, encabezados HTTP, etc., lo que hace que sean ideales para aplicaciones web y móviles.
Seguridad: Los tokens JWT están firmados y cifrados, lo que garantiza que la información transmitida no haya sido manipulada durante la transmisión.
Escalabilidad: Los tokens JWT se pueden validar en cualquier servidor que tenga la clave secreta para verificar la firma, lo que los hace ideales para aplicaciones distribuidas y escalables.
En resumen, JWT es un estándar de autenticación basado en tokens que se utiliza para transmitir información entre partes de forma segura y eficiente. Los tokens JWT constan de un encabezado, un cuerpo y una firma, y se utilizan para autenticar y autorizar a los usuarios en aplicaciones web y móviles.

El uso y manejo de tokens lo tendremos en nuestra JwtService.

Además, para almacenar los tokens y asociarlos a los usuarios crearemos las relaciones pertinentes en el modelo de Usuario y Token (para almacenar los tokens válidos y activos de cada usuario).

### AuthenticantionFilter
Un authentication filter en Spring Boot es un tipo de filtro de seguridad que se utiliza para autenticar las solicitudes de los usuarios en una aplicación web. Los filtros de autenticación en Spring Boot se ejecutan antes de que se procese la solicitud del usuario y se utilizan para validar la identidad del usuario y determinar si se le permite acceder a los recursos protegidos por la aplicación. En definitiva es un middleware que actúa por cada request para decir si debe o no ser atendida la petición.

Un authentication filter en Spring Boot puede ser personalizado para incluir lógica de autenticación específica de la aplicación, como la verificación de credenciales, el control de acceso y la autorización basada en roles. Algunos ejemplos de filtros de autenticación comunes en Spring Boot incluyen:

- OncePerRequestFilter: un filtro que se ejecuta una vez por cada solicitud. Podemos programar su comportamiento. Será el que usemos por ser el más genérico, ya que lo podemos llevar a cabo en cualquier petición y castearlo a los desmás.
- UsernamePasswordAuthenticationFilter: un filtro que autentica a los usuarios utilizando un nombre de usuario y una contraseña.
- JwtAuthenticationFilter: un filtro que autentica a los usuarios utilizando tokens JWT.
- OAuth2AuthenticationFilter: un filtro que autentica a los usuarios utilizando el protocolo OAuth 2.0.

De esta manera si las condiciones son correctas, por ejemplo que el token sea correcto, esté validado y exista el usuario, podemos pasar a la etapa siguiente gracias al método doFilterInternal: `filterChain.doFilter(request, response);`

### Security Config
En esta clase cargamos toda la lógica de Seguridad definiendo SecurityFilterChain donde combinamos los filtros de seguridad que hemos definido anteriormente. Además, podemos definir prevención de ataques, que podemos acceder a determinadas rutas mediante autenticación, o autorizar la entrada a determinados recursos en base a roles y permisos definidos.

Por otro lado, además le cargaremos nuestro filtro de autenticación personalizado, que será el que se encargue de validar el token y autenticar al usuario.

De esta manera tendremos nuestro middleware de seguridad configurado y listo para ser usado.

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf()
        .disable()
        // autorizamos las peticiones
        .authorizeHttpRequests()

        // Permiso para errores y mostrarlos, si no no los veremos, sobre todo los de validación
        // y los de excepciones personalizadas
        .requestMatchers("/error/**")
        .permitAll()

        // Permitimos el acceso a los endpoints de swagger
        // .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()

        .requestMatchers("/api/**")
        .permitAll()

        // Vamos a jugar con los permisos de los endpoints y sus permisos
        // Podemos dar los permisos individualmente por cada método y rol
        // en este caso solo los administradores y managers pueden acceder a los endpoints de gestión
        .requestMatchers("/api/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())

        // podemos ir más allá y dar permisos por cada método y rol y tipo de permiso
        // Para ello podemos hacer uso de los permisos que hemos creado o del rol!
        .requestMatchers(GET, "/api/management/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
        .requestMatchers(POST, "/api/management/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
        .requestMatchers(PUT, "/api/management/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
        .requestMatchers(DELETE, "/api/management/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())

        // Cualquier otra petición requiere autenticación
        .anyRequest()
        .authenticated()
        .and()

        // Configuración de sesión
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No hay estado de sesión
        .and()

        // Configuración de autenticación
        .authenticationProvider(authenticationProvider) // Nuestro proveedor de autenticación

        // Configuración de filtros
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // Nuestro filtro de autenticación

        // Configuración de logout
        .logout()
        .logoutUrl("/api/auth/logout") // URL de logout
        .addLogoutHandler(logoutHandler)
        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());

    return http.build();
}
```

#### Permisos de acceso a los endpoints
Podemos otorgar permisos de dos maneras para acceder a un recurso o endpoint de varias maneras:
- En el SecurityFilterChain: De esta manera centralizamos en un solo lugar, del middleware como si fuese nuestro "firewall".
  - Por rol: `hasRole(ADMIN.name())` o `hasAnyRole(ADMIN.name(), MANAGER.name())` .
  - Por permisos: `hasAuthority(ADMIN_READ.name())` o `hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())`

```java
    .requestMatchers("/api/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())
    // podemos ir más allá y dar permisos por cada método y rol y tipo de permiso
    // Para ello podemos hacer uso de los permisos que hemos creado o del rol!
    .requestMatchers(GET, "/api/management/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
    .requestMatchers(POST, "/api/management/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
    .requestMatchers(PUT, "/api/management/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
    .requestMatchers(DELETE, "/api/management/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())

```
- En los controladores o métodos del controlador, pudiendo hacer uso de los permisos o roles que hemos creado en base a etiquetas con @.
  - Por rol: `@PreAuthorize("hasRole('ROLE_ADMIN')")` o `@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")`
  - Por permisos: `@PreAuthorize("hasAuthority('ADMIN_READ')")` o `@PreAuthorize("hasAnyAuthority('ADMIN_READ', 'MANAGER_READ')")`

```java
@RestController
@RequestMapping("/api/demo")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')") // Solo los usuarios autenticados pueden acceder a este controlador
public class DemoController {

    @GetMapping("/todos")
    public ResponseEntity<String> todos() {
        return ResponseEntity.ok("Hola a todos desde un endpoint abierto");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')") // Solo los administradores pueden acceder a este recurso
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("Hola a todos desde un endpoint seguro solo para administradores");
    }

    @GetMapping("/manager")
    @PreAuthorize("hasRole('MANAGER')") // Solo los managers pueden acceder a este recurso
    public ResponseEntity<String> manager() {
        return ResponseEntity.ok("Hola a todos desde un endpoint seguro solo para managers");
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')") // Solo los users pueden acceder a este recurso
    public ResponseEntity<String> user() {
        return ResponseEntity.ok("Hola a todos desde un endpoint seguro solo para usuarios");
    }

    @GetMapping("/auth")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<String> auth() {
        return ResponseEntity.ok("Hola a todos desde un endpoint seguro solo administradores, managers y usuarios");
    }

    // Una de las cosas que podemos hacer es obtener el usuario que está en el contexto de seguridad
    // Es decir el usuario que está logueado en ese momento en el sistema
    // en base a su token
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<String> me(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
                "Hola a todos desde un endpoint seguro. Soy: "
                        + user.getUsername()
                        + " con email: "
                        + user.getEmail()
                        + " y roles: "
                        + user.getAuthorities()
        );
    }
}
```

#### AuthenticationPrincipal
Una de las cosas que podemos hacer es obtener el usuario que está en el contexto de seguridad. Para ello vamos a usar la anotación `@AuthenticationPrincipal` en el método del controlador que queramos obtener el usuario que está logueado en ese momento en el sistema en base a su token.

```java
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<String> me(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
                "Hola a todos desde un endpoint seguro. Soy: "
                        + user.getUsername()
                        + " con email: "
                        + user.getEmail()
                        + " y roles: "
                        + user.getAuthorities()
        );
    }
```

## SSL






