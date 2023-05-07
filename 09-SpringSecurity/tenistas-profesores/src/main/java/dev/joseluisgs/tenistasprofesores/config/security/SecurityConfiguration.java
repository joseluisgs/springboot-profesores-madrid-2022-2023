package dev.joseluisgs.tenistasprofesores.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static dev.joseluisgs.tenistasprofesores.models.user.Permission.*;
import static dev.joseluisgs.tenistasprofesores.models.user.Role.ADMIN;
import static dev.joseluisgs.tenistasprofesores.models.user.Role.MANAGER;
import static org.springframework.http.HttpMethod.*;

// Configuración del Contexto de Seguridad
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider, LogoutHandler logoutHandler) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
        this.logoutHandler = logoutHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                // autorizamos las peticiones
                .authorizeHttpRequests()

                // Permiso para errores y mostrarlos, si no no los veremos, sobre todo los de validación
                // y los de excepciones personalizadas
                .requestMatchers("/error/**").permitAll()

                // Permitimos el acceso a los endpoints de swagger
                // .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()

                .requestMatchers("/api/**")
                .permitAll()

                // Vamos a jugar con los permisos de los endpoints y sus permisos
                // Podemos dar los permisos individualmente por cada método y rol
                .requestMatchers("/api/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())

                // podemos ir más allá y dar permisos por cada método y rol y tipo de permiso
                .requestMatchers(GET, "/api/management/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                .requestMatchers(POST, "/api/management/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                .requestMatchers(PUT, "/api/management/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                .requestMatchers(DELETE, "/api/management/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())


                // Esto lo quitamos para hacerlo en el controlador!!!
                /* .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())

                 .requestMatchers(GET, "/api/v1/admin/**").hasAuthority(ADMIN_READ.name())
                 .requestMatchers(POST, "/api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
                 .requestMatchers(PUT, "/api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
                 .requestMatchers(DELETE, "/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())*/


                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No hay estado de sesión
                .and()
                .authenticationProvider(authenticationProvider) // Nuestro proveedor de autenticación
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // Nuestro filtro de autenticación
                .logout()
                .logoutUrl("/api/auth/logout") // URL de logout
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
        ;

        return http.build();
    }
}
