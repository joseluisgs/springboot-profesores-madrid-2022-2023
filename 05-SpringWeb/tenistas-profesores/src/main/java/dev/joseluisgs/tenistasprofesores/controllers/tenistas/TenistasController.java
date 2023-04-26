package dev.joseluisgs.tenistasprofesores.controllers.tenistas;


import dev.joseluisgs.tenistasprofesores.dto.Tenistas.TenistaRequestDto;
import dev.joseluisgs.tenistasprofesores.dto.Tenistas.TenistaResponseDto;
import dev.joseluisgs.tenistasprofesores.mapper.TenistaMapper;
import dev.joseluisgs.tenistasprofesores.models.Tenista;
import dev.joseluisgs.tenistasprofesores.services.Raquetas.RaquetasService;
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
    private final RaquetasService raquetasService;

    private final TenistaMapper tenistaMapper;

    @Autowired
    public TenistasController(TenistasService tenistasService, RaquetasService raquetasService, TenistaMapper tenistaMapper) {
        this.tenistasService = tenistasService;
        this.raquetasService = raquetasService;
        this.tenistaMapper = tenistaMapper;
    }

    @GetMapping("")
    public ResponseEntity<List<TenistaResponseDto>> getAllTenistas(
            @RequestParam @Nullable String nombre,
            @RequestParam @Nullable String pais
    ) {
        log.info("getAllTenistas");

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
        var tenista = tenistasService.findById(id);
        // Si existe lo devolvemos
        if (tenista.isPresent()) {
            return ResponseEntity.ok(
                    tenistaMapper.toResponse(tenista.get())
            );
        } else {
            // Si no existe devolvemos un 404
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/find/{uuid}")
    public ResponseEntity<TenistaResponseDto> getTenistaByUuid(
            @PathVariable UUID uuid
    ) {
        log.info("getTenistaByUuid");
        // Existe el tenista?
        var tenista = tenistasService.findByUuid(uuid);
        // Si existe lo devolvemos
        if (tenista.isPresent()) {
            return ResponseEntity.ok(
                    tenistaMapper.toResponse(tenista.get())
            );
        } else {
            // Si no existe devolvemos un 404
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<TenistaResponseDto> postTenista(
            @RequestBody TenistaRequestDto tenista
    ) {
        log.info("addTenista");
        // Comprobamos si hay raqueta comprobamos que exista antes
        if (tenista.getRaquetaId() != null) {
            // existe la raqueta
            var raqueta = raquetasService.findById(tenista.getRaquetaId());
            if (raqueta.isEmpty()) {
                // Si no existe devolvemos un 400 no es valida la peticion
                return ResponseEntity.badRequest().build();
            }
        }
        // Pase lo que pase guardamos el tenista
        var tenistaSaved = tenistasService.save(tenistaMapper.toModel(tenista));
        return ResponseEntity.created(null).body(
                tenistaMapper.toResponse(tenistaSaved)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TenistaResponseDto> putTenista(
            @PathVariable Long id,
            @RequestBody Tenista tenista
    ) {
        log.info("putTenista");
        // Comprobamos si hay raqueta comprobamos que exista antes
        if (tenista.getRaquetaId() != null) {
            // existe la raqueta
            var raqueta = raquetasService.findById(tenista.getRaquetaId());
            if (raqueta.isEmpty()) {
                // Si no existe devolvemos un 400 no es valida la peticion
                return ResponseEntity.badRequest().build();
            }
        }
        // Existe el tenista?
        var tenistaDB = tenistasService.findById(id);
        // Si existe lo devolvemos
        if (tenistaDB.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // Actualizamos los datos
        tenistaDB.get().setNombre(tenista.getNombre());
        tenistaDB.get().setPais(tenista.getPais());
        tenistaDB.get().setPais(tenista.getPais());
        tenistaDB.get().setImagen(tenista.getImagen());
        tenistaDB.get().setRanking(tenista.getRanking());
        tenistaDB.get().setRaquetaId(tenista.getRaquetaId());
        // Guardamos los cambios
        var tenistaSaved = tenistasService.save(tenistaDB.get());
        return ResponseEntity.ok(
                tenistaMapper.toResponse(tenistaSaved)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TenistaResponseDto> deleteTenista(
            @PathVariable Long id
    ) {
        log.info("deleteTenista");
        // Existe el tenista?
        var tenistaDB = tenistasService.findById(id);
        // Si existe lo devolvemos
        if (tenistaDB.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // Borramos el tenista
        tenistasService.deleteById(tenistaDB.get().getId());
        return ResponseEntity.noContent().build();
    }
}
