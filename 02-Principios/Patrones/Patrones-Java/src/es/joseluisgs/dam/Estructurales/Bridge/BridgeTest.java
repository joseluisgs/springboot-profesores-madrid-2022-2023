package es.joseluisgs.dam.Estructurales.Bridge;

/**
 * Bridge es un patrón de diseño estructural que te permite dividir una clase grande,
 * o un grupo de clases estrechamente relacionadas, en dos jerarquías separadas
 * (abstracción e implementación) que pueden desarrollarse independientemente la una de la otra.
 */
public class BridgeTest {
    public void test() {
        Shape redCircle = new Circle(100,100, 10, new RedCircle());
        Shape greenCircle = new Circle(100,100, 10, new GreenCircle());

        redCircle.draw();
        greenCircle.draw();
    }
}
