package dev.joseluisgs.tenistasprofesores.controllers.tenistas;


import dev.joseluisgs.tenistasprofesores.dto.tenistas.TenistaRequestDto;
import dev.joseluisgs.tenistasprofesores.dto.tenistas.TenistaResponseDto;
import dev.joseluisgs.tenistasprofesores.dto.tenistas.TenistaResponsePageDto;
import dev.joseluisgs.tenistasprofesores.mapper.tenistas.TenistaMapper;
import dev.joseluisgs.tenistasprofesores.services.storage.StorageService;
import dev.joseluisgs.tenistasprofesores.services.tenistas.TenistasService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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
    private final StorageService storageService;

    @Autowired
    public TenistasController(TenistasService tenistasService, TenistaMapper tenistaMapper, StorageService storageService) {
        this.tenistasService = tenistasService;
        this.tenistaMapper = tenistaMapper;
        this.storageService = storageService;
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

    @GetMapping("/all")
    public ResponseEntity<TenistaResponsePageDto> listado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        try {
            // Consulto en base a las páginas
            Pageable paging = PageRequest.of(page, size, Sort.Direction.ASC, sort);
            var result = tenistasService.findAllUsingPage(paging);
            TenistaResponsePageDto tenistaResponsePageDto = new TenistaResponsePageDto(
                    tenistaMapper.toResponse(result.getContent()),
                    result.getTotalPages(),
                    result.getTotalElements(),
                    result.getNumber(),
                    result.getSort().toString()
            );

            return ResponseEntity.ok(tenistaResponsePageDto);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PatchMapping(value = "/imagen/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TenistaResponseDto> nuevoProducto(
            @PathVariable Long id,
            @RequestPart("file") MultipartFile file) {

        log.info("patchTenista");

        // Buscamos la raqueta
        if (!file.isEmpty()) {
            String imagen = storageService.store(file);
            String urlImagen = storageService.getUrl(imagen);

            var tenista = tenistasService.findById(id);
            tenista.setImagen(urlImagen);

            // Devolvemos el OK
            return ResponseEntity.ok(
                    tenistaMapper.toResponse(tenistasService.update(id, tenista))
            );
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se ha enviado la imagen");
        }
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
