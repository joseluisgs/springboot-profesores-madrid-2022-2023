package dev.joseluisgs.tenistasprofesores.controllers.raquetas;


import dev.joseluisgs.tenistasprofesores.models.Tenista;
import dev.joseluisgs.tenistasprofesores.services.Raquetas.RaquetasService;
import dev.joseluisgs.tenistasprofesores.services.Tenistas.TenistasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tenistas")
@Slf4j // Para el log
public class TenistasController {

    private final TenistasService tenistasService;
    private final RaquetasService raquetasService;

    @Autowired
    public TenistasController(TenistasService tenistasService, RaquetasService raquetasService) {
        this.tenistasService = tenistasService;
        this.raquetasService = raquetasService;
    }

    @GetMapping("")
    public ResponseEntity<Iterable<Tenista>> getAllTenistas() {
        log.info("getAllTenistas");
        var tenistas = tenistasService.findAll();
        return ResponseEntity.ok(tenistas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tenista> getTenistaById(@PathVariable Long id) {
        log.info("getTenistaById");
        // Existe el tenista?
        var tenista = tenistasService.findById(id);
        // Si existe lo devolvemos
        if (tenista.isPresent()) {
            return ResponseEntity.ok(tenista.get());
        } else {
            // Si no existe devolvemos un 404
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/find/{uuid}")
    public ResponseEntity<Tenista> getTenistaByUuid(@PathVariable UUID uuid) {
        log.info("getTenistaByUuid");
        // Existe el tenista?
        var tenista = tenistasService.findByUuid(uuid);
        // Si existe lo devolvemos
        if (tenista.isPresent()) {
            return ResponseEntity.ok(tenista.get());
        } else {
            // Si no existe devolvemos un 404
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<Tenista> postTenista(@RequestBody Tenista tenista) {
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
        var tenistaSaved = tenistasService.save(tenista);
        return ResponseEntity.ok(tenistaSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tenista> putTenista(@PathVariable Long id, @RequestBody Tenista tenista) {
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
        return ResponseEntity.ok(tenistaSaved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Tenista> deleteTenista(@PathVariable Long id) {
        log.info("deleteTenista");
        // Existe el tenista?
        var tenistaDB = tenistasService.findById(id);
        // Si existe lo devolvemos
        if (tenistaDB.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // Borramos el tenista
        tenistasService.deleteById(tenistaDB.get().getId());
        return ResponseEntity.ok().build();
    }
}
