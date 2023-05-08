package dev.joseluisgs.tenistasprofesores.controllers.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Como se puede ver aquí podemos dar los permisos individualmente por cada método y rol
 * Y globalmente en el controlador
 * Es otra forma de hacerlo y no definirlo en el SecurityConfig
 */

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')") // Solo los administradores pueden acceder a este recurso
public class AdminController {

    // Podemos dar los permisos individualmente por cada método y rol
    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("GET:: admin controller");
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<String> post() {
        return ResponseEntity.ok("POST:: admin controller");
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<String> put() {
        return ResponseEntity.ok("PUT:: admin controller");
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<String> delete() {
        return ResponseEntity.ok("DELETE:: admin controller");
    }
}
