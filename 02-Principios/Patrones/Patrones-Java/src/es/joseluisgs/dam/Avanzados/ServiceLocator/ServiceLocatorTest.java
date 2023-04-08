package es.joseluisgs.dam.Avanzados.ServiceLocator;

/**
 * El patrón de localizador de servicios está diseñado para encapsular los
 * procesos involucrados en la obtención de un servicio con una fuerte capa de abstracción.
 * Es una de las soluciones para el problema de la inyección de dependencias.
 * El localizador de servicios tiene un registro central conocido como localizador de servicios,
 * que a petición devuelve los datos necesarios para realizar una tarea.
 * El localizador de servicios devuelve instancias de servicios cuando son
 * solicitados por los consumidores o clientes del servicio con sus dependencias listas.
 * El patrón del localizador de servicios no describe cómo crear instancias de servicios,
 * sino que describe una forma de registrar servicios y localizarlos.
 */
public class ServiceLocatorTest {
    public void test() {
        Service service = ServiceLocator.getService("Service1");
        service.execute();
        service = ServiceLocator.getService("Service2");
        service.execute();
        service = ServiceLocator.getService("Service1");
        service.execute();
        service = ServiceLocator.getService("Service2");
        service.execute();
    }
}
