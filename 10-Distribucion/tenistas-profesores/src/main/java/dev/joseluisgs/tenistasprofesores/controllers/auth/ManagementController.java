package dev.joseluisgs.tenistasprofesores.controllers.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/managment")
// @PreAuthorize("hasRole('MANAGER')") // Solo los administradores pueden acceder a este recurso
public class ManagementController {
    @GetMapping
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("GET:: management controller");
    }

    @PostMapping
    public ResponseEntity<String> post() {
        return ResponseEntity.ok("POST:: management controller");
    }

    @PutMapping
    public ResponseEntity<String> put() {
        return ResponseEntity.ok("PUT:: management controller");
    }

    @DeleteMapping
    public ResponseEntity<String> delete() {
        return ResponseEntity.ok("DELETE:: management controller");
    }
}
