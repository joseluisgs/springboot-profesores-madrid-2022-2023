package es.joseluisgs.dam.Estructurales.Facade;

/**
 * Facade es un patrón de diseño estructural que proporciona una interfaz simplificada a una biblioteca,
 * un framework o cualquier otro grupo complejo de clases.
 */
public class FacadeTest {
    public void test() {
        ShapeMaker shapeMaker = new ShapeMaker();

        shapeMaker.drawCircle();
        shapeMaker.drawRectangle();
        shapeMaker.drawSquare();
    }
}
