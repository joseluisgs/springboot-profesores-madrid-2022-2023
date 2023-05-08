package dev.joseluisgs.tenistasprofesores.controllers.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Otra de las cosas que podemos hacer es dar el acceso a los endpoints por roles y permisos
 * de forma global, es decir, en el controlador, o de forma individual en cada método.
 */

@RestController
@RequestMapping("/api/demo")
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
    // Solo los administradores pueden acceder a este recurso
    public ResponseEntity<String> auth() {
        return ResponseEntity.ok("Hola a todos desde un endpoint seguro solo administradores, managers y usuarios");
    }

}
