package dev.joseluisgs.tenistasprofesores.controllers.raquetas;

import dev.joseluisgs.tenistasprofesores.models.Raqueta;
import dev.joseluisgs.tenistasprofesores.services.Raquetas.RaquetasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private final RaquetasService raquetasService;

    // Inyectamos el repositorio de raquetas con la anotación @Autowired
    @Autowired
    public RaquetasController(RaquetasService raquetasService) {
        this.raquetasService = raquetasService;
    }

    // Aquí se implementan los métodos de la API REST

    // GET: /api/raquetas
    @GetMapping("")
    public ResponseEntity<Iterable<Raqueta>> getAllRaquetas() {
        log.info("getAllRaquetas");
        var raquetas = raquetasService.findAll();
        return ResponseEntity.ok(raquetas);
    }

    // GET: /api/raquetas/{id}
    // @PathVariable: Indica que el parámetro de la función es un parámetro de la URL en este caso {id}
    @GetMapping("/{id}")
    public ResponseEntity<Raqueta> getRaquetaById(@PathVariable Long id) {
        log.info("getRaquetaById");
        // Existe la raqueta?
        var raqueta = raquetasService.findById(id);
        // Si existe la devolvemos
        if (raqueta.isPresent()) {
            return ResponseEntity.ok(raqueta.get());
        } else {
            // Si no existe devolvemos un 404
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/find/{uuid}")
    public ResponseEntity<Raqueta> getRaquetaByUuid(@PathVariable UUID uuid) {
        log.info("getRaquetaByUuid");
        // Existe la raqueta?
        var raqueta = raquetasService.findByUuid(uuid);
        // Si existe la devolvemos
        if (raqueta.isPresent()) {
            return ResponseEntity.ok(raqueta.get());
        } else {
            // Si no existe devolvemos un 404
            return ResponseEntity.notFound().build();
        }
    }

    // POST: /api/raquetas
    // @RequestBody: Indica que el parámetro de la función es un parámetro del cuerpo de la petición HTTP
    @PostMapping
    public ResponseEntity<Raqueta> postRaqueta(@RequestBody Raqueta raqueta) {
        log.info("addRaqueta");
        // Añadimos la raqueta
        var raquetaSaved = raquetasService.save(raqueta);
        // Devolvemos created
        return ResponseEntity.created(null).body(raquetaSaved);
    }

    // PUT: /api/raquetas/{id}
    // @PathVariable: Indica que el parámetro de la función es un parámetro de la URL en este caso {id}
    // @RequestBody: Indica que el parámetro de la función es un parámetro del cuerpo de la petición HTTP
    @PutMapping("/{id}")
    public ResponseEntity<Raqueta> putRaqueta(
            @PathVariable Long id,
            @RequestBody Raqueta raqueta
    ) {
        log.info("putRaqueta");
        // Existe la raqueta?
        var raquetaDB = raquetasService.findById(id);
        // Si existe la actualizamos
        if (raquetaDB.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // Actualizamos los datos
        raquetaDB.get().setModelo(raqueta.getModelo());
        raquetaDB.get().setPrecio(raqueta.getPrecio());
        raquetaDB.get().setImagen(raqueta.getImagen());
        // Guardamos los cambios
        raquetasService.save(raquetaDB.get());
        // Devolvemos el OK
        return ResponseEntity.ok(raquetaDB.get());
    }

    // DELETE: /api/raquetas/{id}
    // @PathVariable: Indica que el parámetro de la función es un parámetro de la URL en este caso {id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Raqueta> deleteRaqueta(@PathVariable Long id) {
        log.info("deleteRaqueta");
        // Existe la raqueta?
        var raquetaDB = raquetasService.findById(id);
        // Si existe la borramos
        if (raquetaDB.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        raquetasService.deleteById(raquetaDB.get().getId());
        // Devolvemos el OK o No Content
        // return ResponseEntity.ok(raquetaDB.get());
        return ResponseEntity.noContent().build();

    }

}
