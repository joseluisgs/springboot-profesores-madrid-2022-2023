package dev.joseluisgs.tenistasprofesores.data.tenistas;

import dev.joseluisgs.tenistasprofesores.data.raquetas.RaquetasFactory;
import dev.joseluisgs.tenistasprofesores.models.tenistas.Tenista;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class TenistasFactory {
    public static Map<Long, Tenista> getTenistasDemoData() {
        log.info("Cargando datos de raquetas de demo");

        var raquetas = RaquetasFactory.getRaquetasDemoData();

        var map = new LinkedHashMap<Long, Tenista>();
        map.put(1L,
                new Tenista(1L, UUID.fromString("db4e3a1e-b0b9-4a22-8959-862264b0bc57"), "Rafael Nadal", 1, "España", "https://upload.wikimedia.org/wikipedia/commons/9/9b/Rafael_Nadal_10%2C_Aegon_Championships%2C_London%2C_UK_-_Diliff_%28cropped%29.jpg", raquetas.get(1L), LocalDateTime.now(), LocalDateTime.now(), false));
        map.put(2L,
                new Tenista(2L, UUID.fromString("feab014d-caa8-44d7-b0dd-41f1ea2b95f6"), "Novak Djokovic", 2, "Serbia", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/48/Novak_Djokovi%C4%87_Trophy_Wimbledon_2019-croped_and_edited.jpg/245px-Novak_Djokovi%C4%87_Trophy_Wimbledon_2019-croped_and_edited.jpg", raquetas.get(2L), LocalDateTime.now(), LocalDateTime.now(), false));
        map.put(3L,
                new Tenista(3L, UUID.fromString("893b326c-dde2-4639-bce5-8c3364d2117e"), "Roger Federer", 3, "Suiza", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/40/R_federer.jpg/245px-R_federer.jpg", raquetas.get(3L), LocalDateTime.now(), LocalDateTime.now(), false));
        map.put(4L,
                new Tenista(4L, UUID.fromString("d52aa228-4513-4e97-b72c-cab977a4f4d9"), "Carlos Alcaraz", 4, "España", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/97/Carlos_Alcaraz_%28ESP%29_2022.jpg/1200px-Carlos_Alcaraz_%28ESP%29_2022.jpg", raquetas.get(1L), LocalDateTime.now(), LocalDateTime.now(), false));

        return map;
    }
}
