package dev.joseluisgs.tenistasprofesores.controllers.raquetas;

import dev.joseluisgs.tenistasprofesores.dto.raquetas.RaquetaRequestDto;
import dev.joseluisgs.tenistasprofesores.dto.raquetas.RaquetaResponseDto;
import dev.joseluisgs.tenistasprofesores.dto.raquetas.RaquetaResponsePageDto;
import dev.joseluisgs.tenistasprofesores.mapper.raquetas.RaquetaMapper;
import dev.joseluisgs.tenistasprofesores.services.raquetas.RaquetasService;
import dev.joseluisgs.tenistasprofesores.services.storage.StorageService;
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

/**
 * Controlador de Raquetas
 * Aquí se implementan los métodos de la API REST
 * Es un controlador REST, por lo que se le indica con la anotación @RestController
 * El path base de la API REST es /api/raquetas y se le indica con la anotación @RequestMapping
 */
@RestController
@RequestMapping("/api/raquetas")
@Slf4j // Para el log
public class RaquetasController {
    // Mis dependecias
    private final RaquetasService raquetasService;
    private final RaquetaMapper raquetaMapper;
    private final StorageService storageService;

    // Inyectamos el repositorio de raquetas con la anotación @Autowired
    @Autowired
    public RaquetasController(RaquetasService raquetasService, RaquetaMapper raquetaMapper, StorageService storageService) {
        this.raquetasService = raquetasService;
        this.raquetaMapper = raquetaMapper;
        this.storageService = storageService;
    }

    // Aquí se implementan los métodos de la API REST

    // GET: /api/raquetas
    @GetMapping("")
    public ResponseEntity<List<RaquetaResponseDto>> getAllRaquetas(
            @RequestParam(required = false) String marca
    ) {
        log.info("getAllRaquetas");

        // Jugamos con query params / api/raquetas?marca=Wilson
        if (marca != null && !marca.isEmpty()) {
            return ResponseEntity.ok(
                    raquetaMapper.toResponse(raquetasService.findAllByMarca(marca))
            );
        }
        return ResponseEntity.ok(
                raquetaMapper.toResponse(raquetasService.findAll())
        );
    }

    // GET: /api/raquetas/{id}
    // @PathVariable: Indica que el parámetro de la función es un parámetro de la URL en este caso {id}
    @GetMapping("/{id}")
    public ResponseEntity<RaquetaResponseDto> getRaquetaById(
            @PathVariable Long id
    ) {
        log.info("getRaquetaById");
        return ResponseEntity.ok(
                raquetaMapper.toResponse(raquetasService.findById(id))
        );
    }

    @GetMapping("/find/{uuid}")
    public ResponseEntity<RaquetaResponseDto> getRaquetaByUuid(
            @PathVariable UUID uuid
    ) {
        log.info("getRaquetaByUuid");
        // Existe la raqueta?
        return ResponseEntity.ok(
                raquetaMapper.toResponse(raquetasService.findByUuid(uuid))
        );
    }

    // POST: /api/raquetas
    // @RequestBody: Indica que el parámetro de la función es un parámetro del cuerpo de la petición HTTP
    @PostMapping
    public ResponseEntity<RaquetaResponseDto> postRaqueta(
            @Valid @RequestBody RaquetaRequestDto raqueta
    ) {
        log.info("addRaqueta");
        // Devolvemos created
        return ResponseEntity.created(null).body(
                raquetaMapper.toResponse(
                        raquetasService.save(raquetaMapper.toModel(raqueta)))
        );
    }

    // PUT: /api/raquetas/{id}
    // @PathVariable: Indica que el parámetro de la función es un parámetro de la URL en este caso {id}
    // @RequestBody: Indica que el parámetro de la función es un parámetro del cuerpo de la petición HTTP
    @PutMapping("/{id}")
    public ResponseEntity<RaquetaResponseDto> putRaqueta(
            @PathVariable Long id,
            @Valid @RequestBody RaquetaRequestDto raqueta
    ) {
        log.info("putRaqueta");
        // Devolvemos el OK
        return ResponseEntity.ok(
                raquetaMapper.toResponse(raquetasService.update(id, raquetaMapper.toModel(raqueta)))
        );
    }

    // DELETE: /api/raquetas/{id}
    // @PathVariable: Indica que el parámetro de la función es un parámetro de la URL en este caso {id}
    @DeleteMapping("/{id}")
    public ResponseEntity<RaquetaResponseDto> deleteRaqueta(
            @PathVariable Long id
    ) {
        log.info("deleteRaqueta");
        raquetasService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<RaquetaResponsePageDto> listado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        try {
            // Consulto en base a las páginas
            Pageable paging = PageRequest.of(page, size, Sort.Direction.ASC, sort);
            var result = raquetasService.findAllUsingPage(paging);
            RaquetaResponsePageDto raquetaResponsePageDto = new RaquetaResponsePageDto(
                    raquetaMapper.toResponse(result.getContent()),
                    result.getTotalPages(),
                    result.getTotalElements(),
                    result.getNumber(),
                    result.getSort().toString()
            );

            return ResponseEntity.ok(raquetaResponsePageDto);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // PATCH: /api/raquetas//imagen/{id}
    // consumes = MediaType.MULTIPART_FORM_DATA_VALUE: Indica que el parámetro de la función es un parámetro del cuerpo de la petición HTTP
    // @PathVariable: Indica que el parámetro de la función es un parámetro de la URL en este caso {id}
    // @RequestPart: Indica que el parámetro de la función es un parámetro del cuerpo de la petición HTTP
    // En este caso es un fichero, por lo que se indica con @RequestPart y mMltipartFile
    @PatchMapping(value = "/imagen/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RaquetaResponseDto> nuevoProducto(
            @PathVariable Long id,
            @RequestPart("fichero") MultipartFile file) {

        log.info("patchRaqueta");

        // Buscamos la raqueta
        if (!file.isEmpty()) {
            String imagen = storageService.store(file);
            String urlImagen = storageService.getUrl(imagen);

            var raqueta = raquetasService.findById(id);
            raqueta.setImagen(urlImagen);

            // Devolvemos el OK
            return ResponseEntity.ok(
                    raquetaMapper.toResponse(raquetasService.update(id, raqueta))
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

    @PostMapping(value = "/nuevaconimagen", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RaquetaResponseDto> postNuevaConImagen(
            @RequestPart("file") MultipartFile file,
            @Valid @RequestPart("raqueta") RaquetaRequestDto raqueta
    ) {
        log.info("addRaqueta");
        // Devolvemos created

        String imagen = storageService.store(file);
        String urlImagen = storageService.getUrl(imagen);

        var raquetaModel = raquetaMapper.toModel(raqueta);
        raquetaModel.setImagen(urlImagen);

        return ResponseEntity.created(null).body(
                raquetaMapper.toResponse(raquetasService.save(raquetaModel)
                )
        );
    }


}
