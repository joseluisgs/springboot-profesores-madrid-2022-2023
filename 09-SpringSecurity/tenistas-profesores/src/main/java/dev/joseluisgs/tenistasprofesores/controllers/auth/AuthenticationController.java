package dev.joseluisgs.tenistasprofesores.controllers.auth;

import dev.joseluisgs.tenistasprofesores.dto.auth.LoginRequestDto;
import dev.joseluisgs.tenistasprofesores.dto.auth.LoginResponseDto;
import dev.joseluisgs.tenistasprofesores.dto.auth.RegisterRequestDto;
import dev.joseluisgs.tenistasprofesores.services.auth.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService service;

    @Autowired
    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    // Registra a un usuario
    @PostMapping("/register")
    public ResponseEntity<LoginResponseDto> register(
            @Valid @RequestBody RegisterRequestDto request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    // Autentica a un usuario --> Login
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(
            @Valid @RequestBody LoginRequestDto request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    // Refresca el token
    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }

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


}
