package dev.joseluisgs.tenistasprofesores.data.raquetas;

import dev.joseluisgs.tenistasprofesores.models.Raqueta;
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
                new Raqueta(1L, UUID.randomUUID(), "Babolat", "Pure Aero", 199.95, "https://img.tenniswarehouse-europe.com/watermark/rs.php?path=BPAR22-1.jpg", LocalDateTime.now(), LocalDateTime.now(), false));
        map.put(2L,
                new Raqueta(2L, UUID.randomUUID(), "Head", "Speed", 225.50, "https://www.m1tennis.com/24397-large_default/raqueta-head-speed-power-pwr-lite-2022.jpg", LocalDateTime.now(), LocalDateTime.now(), false));

        map.put(3L,
                new Raqueta(3L, UUID.randomUUID(), "Wilson", "Blade 98", 250.00, "https://www.onlytenis.com/21761-large_default/raqueta-wilson-blade-98-v8-305-gr-16x19-2022.jpg", LocalDateTime.now(), LocalDateTime.now(), false));


        return map;
    }
}
