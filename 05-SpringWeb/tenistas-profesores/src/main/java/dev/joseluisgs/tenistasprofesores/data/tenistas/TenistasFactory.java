package dev.joseluisgs.tenistasprofesores.data.tenistas;

import dev.joseluisgs.tenistasprofesores.models.Tenista;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class TenistasFactory {
    public static Map<Long, Tenista> getTenistasDemoData() {
        var map = new LinkedHashMap<Long, Tenista>();
        map.put(1L,
                new Tenista(1L, UUID.randomUUID(), "Rafael Nadal", 1, "España", "https://upload.wikimedia.org/wikipedia/commons/9/9b/Rafael_Nadal_10%2C_Aegon_Championships%2C_London%2C_UK_-_Diliff_%28cropped%29.jpg", 1L, LocalDateTime.now(), LocalDateTime.now(), false));
        map.put(2L,
                new Tenista(2L, UUID.randomUUID(), "Novak Djokovic", 2, "Serbia", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/48/Novak_Djokovi%C4%87_Trophy_Wimbledon_2019-croped_and_edited.jpg/245px-Novak_Djokovi%C4%87_Trophy_Wimbledon_2019-croped_and_edited.jpg", 2L, LocalDateTime.now(), LocalDateTime.now(), false));
        map.put(3L,
                new Tenista(3L, UUID.randomUUID(), "Roger Federer", 3, "Suiza", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/40/R_federer.jpg/245px-R_federer.jpg", 3L, LocalDateTime.now(), LocalDateTime.now(), false));
        map.put(4L,
                new Tenista(4L, UUID.randomUUID(), "Carlos Alcaraz", 4, "España", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/97/Carlos_Alcaraz_%28ESP%29_2022.jpg/1200px-Carlos_Alcaraz_%28ESP%29_2022.jpg", 1L, LocalDateTime.now(), LocalDateTime.now(), false));

        return map;
    }
}
