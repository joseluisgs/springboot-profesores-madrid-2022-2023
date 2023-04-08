package es.joseluisgs.dam.Estructurales.Decorator;

/**
 * Decorator es un patrón de diseño estructural que te permite añadir funcionalidades
 * a objetos colocando estos objetos dentro de objetos encapsuladores especiales que
 * contienen estas funcionalidades.
 */
public class DecoratorTest {
    public void test() {
        Shape circle = new Circle();

        Shape redCircle = new RedShapeDecorator(new Circle());

        Shape redRectangle = new RedShapeDecorator(new Rectangle());
        System.out.println("Circle with normal border");
        circle.draw();

        System.out.println("\nCircle of red border");
        redCircle.draw();

        System.out.println("\nRectangle of red border");
        redRectangle.draw();
    }
}
