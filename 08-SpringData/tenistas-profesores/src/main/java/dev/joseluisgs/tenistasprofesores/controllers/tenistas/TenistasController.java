package dev.joseluisgs.tenistasprofesores.controllers.tenistas;


import dev.joseluisgs.tenistasprofesores.dto.tenistas.TenistaRequestDto;
import dev.joseluisgs.tenistasprofesores.dto.tenistas.TenistaResponseDto;
import dev.joseluisgs.tenistasprofesores.mapper.tenistas.TenistaMapper;
import dev.joseluisgs.tenistasprofesores.services.tenistas.TenistasService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/tenistas")
@Slf4j // Para el log
public class TenistasController {

    private final TenistasService tenistasService;

    private final TenistaMapper tenistaMapper;

    @Autowired
    public TenistasController(TenistasService tenistasService, TenistaMapper tenistaMapper) {
        this.tenistasService = tenistasService;
        this.tenistaMapper = tenistaMapper;
    }

    @GetMapping("")
    public ResponseEntity<List<TenistaResponseDto>> getAllTenistas(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String pais
    ) {
        log.info("getAllTenistas");

        // Jugamos con query params / api/tenistas?nombre=Jose
        if (nombre != null && !nombre.isEmpty()) {
            return ResponseEntity.ok(
                    tenistaMapper.toResponse(tenistasService.findAllByNombre(nombre))
            );
        }

        if (pais != null && !pais.isEmpty()) {
            return ResponseEntity.ok(
                    tenistaMapper.toResponse(tenistasService.findAllByPais(pais))
            );
        }

        return ResponseEntity.ok(
                tenistaMapper.toResponse(tenistasService.findAll())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenistaResponseDto> getTenistaById(
            @PathVariable Long id
    ) {
        log.info("getTenistaById");
        // Existe el tenista?
        return ResponseEntity.ok(
                tenistaMapper.toResponse(tenistasService.findById(id))
        );
    }

    @GetMapping("/find/{uuid}")
    public ResponseEntity<TenistaResponseDto> getTenistaByUuid(
            @PathVariable UUID uuid
    ) {
        log.info("getTenistaByUuid");
        // Existe el tenista?
        return ResponseEntity.ok(
                tenistaMapper.toResponse(tenistasService.findByUuid(uuid))
        );
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<TenistaResponseDto> putTenista(
            @PathVariable Long id,
            @Valid @RequestBody TenistaRequestDto tenista
    ) {
        log.info("putTenista");
        return ResponseEntity.ok(
                tenistaMapper.toResponse(tenistasService.update(id, tenistaMapper.toModel(tenista)))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TenistaResponseDto> deleteTenista(
            @PathVariable Long id
    ) {
        log.info("deleteTenista");
        tenistasService.deleteById(id);
        return ResponseEntity.noContent().build();
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
