package dev.joseluisgs.tenistasprofesores.config;

import dev.joseluisgs.tenistasprofesores.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Configuración de la aplicación
// Aquí guardamos los beans que necesitamos para la aplicación a nivel General
// Por ejemplo, el UserDetailsService, el AuthenticationProvider, el AuthenticationManager, etc...
@Configuration
public class ApplicationConfig {

    private final UserRepository repository;

    @Autowired
    public ApplicationConfig(UserRepository repository) {
        this.repository = repository;
    }

    // Cada vez que se necesite un UserDetailsService, se devolverá el siguiente
    // con el repositorio de usuarios, de esa forma, Spring Security podrá
    // autenticar a los usuarios y me ahorro implementarlo por mi cuenta
    // y me ahorro implementar el UserDetailsService, ya tengo el repositorio
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // Cada vez que se necesite un AuthenticationProvider, se devolverá el siguiente
    @Bean
    public AuthenticationProvider authenticationProvider() {
        // Usamos el anterior UserDetailsService y el PasswordEncoder
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // Cada vez que se necesite un AuthenticationManager, se devolverá el siguiente
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Cada vez que se necesite un PasswordEncoder, se devolverá el siguiente
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
