package dev.joseluisgs.tenistasprofesores.data.raquetas;

import dev.joseluisgs.tenistasprofesores.models.raquetas.Raqueta;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class RaquetasFactory {

    public static Map<Long, Raqueta> getRaquetasDemoData() {
        log.info("Cargando datos de raquetas de demo");
        var map = new LinkedHashMap<Long, Raqueta>();
        map.put(1L,
                new Raqueta(1L, UUID.fromString("f503a9ae-56b4-448e-b0fe-9bb0a263075c"), "Babolat", "Pure Aero", 199.95, "https://img.tenniswarehouse-europe.com/watermark/rs.php?path=BPAR22-1.jpg", LocalDateTime.now(), LocalDateTime.now(), false));
        map.put(2L,
                new Raqueta(2L, UUID.fromString("ff46f1da-eaf1-4ffa-a8af-f867aac3e1c2"), "Head", "Speed", 225.50, "https://www.m1tennis.com/24397-large_default/raqueta-head-speed-power-pwr-lite-2022.jpg", LocalDateTime.now(), LocalDateTime.now(), false));

        map.put(3L,
                new Raqueta(3L, UUID.fromString("6eb07f7e-e3fa-4673-817b-dd5102a71a3e"), "Wilson", "Blade 98", 250.00, "https://www.onlytenis.com/21761-large_default/raqueta-wilson-blade-98-v8-305-gr-16x19-2022.jpg", LocalDateTime.now(), LocalDateTime.now(), false));

        return map;
    }
}
