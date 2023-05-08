package dev.joseluisgs.tenistasprofesores.controllers.tenistas;


import dev.joseluisgs.tenistasprofesores.dto.tenistas.TenistaRequestDto;
import dev.joseluisgs.tenistasprofesores.dto.tenistas.TenistaResponseDto;
import dev.joseluisgs.tenistasprofesores.dto.tenistas.TenistaResponsePageDto;
import dev.joseluisgs.tenistasprofesores.mapper.tenistas.TenistaMapper;
import dev.joseluisgs.tenistasprofesores.services.storage.StorageService;
import dev.joseluisgs.tenistasprofesores.services.tenistas.TenistasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Get all Tenistas", description = "Obtiene una lista de Tenistas", tags = {"tenistas}"})
    @Parameter(name = "nombre", description = "nombre del tenista a buscar", required = false, example = "Rafa")
    @Parameter(name = "pais", description = "pais del tenista a buscar", required = false, example = "España")
    @ApiResponse(responseCode = "200", description = "Lista de Tenistas")
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

    @Operation(summary = "Get Tenista dado un id", description = "Obtiene un Tenista dado su id", tags = {"tenistas}"})
    @Parameter(name = "id", description = "ID del Tenista", required = true, example = "1")
    @ApiResponse(responseCode = "200", description = "Tenista")
    @ApiResponse(responseCode = "404", description = "Tenista no encontrado")
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

    @Operation(summary = "Get Tenista dado un uuid", description = "Obtiene un Tenista dado su uuid", tags = {"tenistas}"})
    @Parameter(name = "uuid", description = "UUID del Tenista", required = true, example = "1")
    @ApiResponse(responseCode = "200", description = "Tenista")
    @ApiResponse(responseCode = "404", description = "Tenista no encontrado")
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

    @Operation(summary = "Crea un Tenista", description = "Agrega un Tenista a nuestra colección", tags = {"tenistas}"})
    @ApiResponse(responseCode = "201", description = "Tenista creado")
    @ApiResponse(responseCode = "400", description = "Tenista no válido")
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

    @Operation(summary = "Actualiza un Tenista", description = "Actualiza un Tenista dado su id", tags = {"tenistas}"})
    @Parameter(name = "id", description = "ID del Tenista", required = true, example = "1")
    @ApiResponse(responseCode = "200", description = "Tenista actualizado")
    @ApiResponse(responseCode = "404", description = "Tenista no encontrado")
    @ApiResponse(responseCode = "400", description = "Tenista no válido")
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

    @Operation(summary = "Elimina un Tenista", description = "Elimina un Tenista dado su id", tags = {"tenistas}"})
    @Parameter(name = "id", description = "ID del Tenista", required = true, example = "1")
    @ApiResponse(responseCode = "204", description = "Tenista eliminado")
    @ApiResponse(responseCode = "404", description = "Tenista no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<TenistaResponseDto> deleteTenista(
            @PathVariable Long id
    ) {
        log.info("deleteTenista");
        tenistasService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtiene una lista de tenistas paginada", description = "Lista de tenistas paginada", tags = {"tenistas}"})
    @Parameter(name = "page", description = "Página a consultar", required = false, example = "0")
    @Parameter(name = "size", description = "Tamaño de la página", required = false, example = "10")
    @Parameter(name = "sort", description = "Campo por el que ordenar", required = false, example = "id")
    @ApiResponse(responseCode = "200", description = "Lista de tenistas paginada")
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

    @Operation(summary = "Actualiza la imagen de un Tenista", description = "Actualiza la imagen de un Tenista dado su id", tags = {"tenistas}"})
    @Parameter(name = "id", description = "ID del Tenista", required = true, example = "1")
    @ApiResponse(responseCode = "200", description = "Tenista actualizado")
    @ApiResponse(responseCode = "404", description = "Tenista no encontrado")
    @ApiResponse(responseCode = "400", description = "Tenista no válido")
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
