package dev.joseluisgs.tenistasprofesores;

import dev.joseluisgs.tenistasprofesores.dto.auth.RegisterRequestDto;
import dev.joseluisgs.tenistasprofesores.services.auth.AuthenticationService;
import dev.joseluisgs.tenistasprofesores.services.storage.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static dev.joseluisgs.tenistasprofesores.models.user.Role.*;

/**
 * Si quieres hacer un proyecto con Spring Boot, lo primero que tienes que hacer es crear una clase
 * con la anotación @SpringBootApplication, que es la que indica que es una aplicación Spring Boot.
 * Si quieres que tu aplicación haga algo despues de arrancar todo el sistema de Spring, puedes implementar la interfaz
 * CommandLineRunner y sobreescribir el método run.
 */

@SpringBootApplication
@EnableCaching // Habilitamos el caché
@EnableJpaAuditing // Habilitamos la auditoría, idual para el tiempo
public class TenistasProfesoresApplication implements CommandLineRunner {
    // Para leer los datos de application.properties
    @Value("${upload.delete}")
    private String deleteAll = "false";

    // Por si quiero cargar los datos, aunque prefiero por script!!
    // @Autowired
    // RaquetasRepository raquetasRepository;

    // El método main es el punto de entrada de la aplicación, no es necesario que lo modifiques
    public static void main(String[] args) {
        SpringApplication.run(TenistasProfesoresApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Aquí puedes poner el código que quieras que se ejecute al arrancar la aplicación
        System.out.println("Hola mundo desde Spring Boot! 👋");
        // Podriamos cargar los datos desde aquí!! pero no uses el script!!!
        // raquetasRepository.saveAll(RaquetasFactory.getRaquetasDemoData().values());

    }

    @Bean
    // Este bean se ejecuta al arrancar la aplicación, CommandLinerRunner
    public CommandLineRunner init(StorageService storageService) {
        return args -> {
            // Inicializamos el servicio de ficheros
            // Leemos de application.properties si necesitamos borra todo o no

            if (deleteAll.equals("true")) {
                System.out.println("Borrando todo el contenido de la carpeta de ficheros");
                storageService.deleteAll();
            }

            storageService.init(); // inicializamos
        };
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service
    ) {
        return args -> {
            var admin = RegisterRequestDto.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("admin@mail.com")
                    .password("password")
                    .role(ADMIN)
                    .build();
            System.out.println("Admin token: " + service.register(admin).getAccessToken());

            var manager = RegisterRequestDto.builder()
                    .firstname("Manager")
                    .lastname("Manager")
                    .email("manager@mail.com")
                    .password("password")
                    .role(MANAGER)
                    .build();
            System.out.println("Manager token: " + service.register(manager).getAccessToken());

            var user = RegisterRequestDto.builder()
                    .firstname("User")
                    .lastname("User")
                    .email("user@mail.com")
                    .password("password")
                    .role(USER)
                    .build();
            System.out.println("User token: " + service.register(user).getAccessToken());

        };
    }
}
