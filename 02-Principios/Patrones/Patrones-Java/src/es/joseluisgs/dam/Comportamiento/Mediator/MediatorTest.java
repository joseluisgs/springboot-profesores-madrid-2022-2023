package es.joseluisgs.dam.Comportamiento.Mediator;

/**
 * Mediator es un patrón de diseño de comportamiento que te permite reducir
 * las dependencias caóticas entre objetos. El patrón restringe las comunicaciones directas
 * entre los objetos, forzándolos a colaborar únicamente a través de un objeto mediador.
 */
public class MediatorTest {
    public void test() {
        User robert = new User("Robert");
        User john = new User("John");

        robert.sendMessage("Hi! John!");
        john.sendMessage("Hello! Robert!");
    }
}
