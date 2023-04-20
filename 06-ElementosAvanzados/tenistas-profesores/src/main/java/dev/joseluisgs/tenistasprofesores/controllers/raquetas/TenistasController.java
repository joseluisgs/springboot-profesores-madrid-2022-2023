package dev.joseluisgs.tenistasprofesores.controllers.raquetas;


import dev.joseluisgs.tenistasprofesores.dto.Tenistas.TenistaRequestDto;
import dev.joseluisgs.tenistasprofesores.dto.Tenistas.TenistaResponseDto;
import dev.joseluisgs.tenistasprofesores.mapper.TenistaMapper;
import dev.joseluisgs.tenistasprofesores.services.Tenistas.TenistasService;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
            @RequestParam @Nullable String nombre,
            @RequestParam @Nullable String pais
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
            @RequestBody TenistaRequestDto tenista
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
            @RequestBody TenistaRequestDto tenista
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
}
