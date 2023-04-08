package es.joseluisgs.dam.Creacionales.Factory;

/**
 * Factory method es un patrón de diseño creacional que resuelve el problema de crear objetos de
 * producto sin especificar sus clases concretas.
 *
 * El patrón Factory Method define un método que debe utilizarse para crear objetos,
 * en lugar de una llamada directa al constructor (operador new). Las subclases pueden sobrescribir este método para cambiar las clases de los objetos que se crearán.
 */
public class Factory {
    public void test() {
        ShapeFactory shapeFactory = new ShapeFactory();

        Shape shape1 = shapeFactory.getShape("CIRCLE");
        shape1.draw();

        Shape shape2 = shapeFactory.getShape("RECTANGLE");
        shape2.draw();

        Shape shape3 = shapeFactory.getShape("SQUARE");
        shape3.draw();
    }
}
