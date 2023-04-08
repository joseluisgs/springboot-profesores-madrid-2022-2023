package es.joseluisgs.dam.Creacionales.AsbtractFactory;

/**
 * Abstract Factory es un patrón de diseño creacional que nos permite producir
 * familias de objetos relacionados sin especificar sus clases concretas.
 * Es una factoria de factorias de familia de objetos
 */
public class AbstractFactoryTest {
    public void test() {

        AbstractFactory shapeFactory = FactoryProducer.getFactory(false);

        Shape shape1 = shapeFactory.getShape("RECTANGLE");
        shape1.draw();

        Shape shape2 = shapeFactory.getShape("SQUARE");
        shape2.draw();


        AbstractFactory shapeFactory1 = FactoryProducer.getFactory(true);

        Shape shape3 = shapeFactory1.getShape("RECTANGLE");
        shape3.draw();

        Shape shape4 = shapeFactory1.getShape("SQUARE");
        shape4.draw();
    }
}
