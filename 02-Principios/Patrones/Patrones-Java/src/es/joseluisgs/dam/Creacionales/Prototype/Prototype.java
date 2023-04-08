package es.joseluisgs.dam.Creacionales.Prototype;

/**
 * Prototype es un patrón de diseño creacional
 * que nos permite copiar objetos existentes sin que el código dependa de sus clases.
 */
public class Prototype {
    public void test() {
        Circle circle = new Circle();
        circle.x = 10;
        circle.y = 20;
        circle.radius = 15;
        circle.color = "red";
        Circle anotherCircle = (Circle) circle.clone();


        Rectangle rectangle = new Rectangle();
        rectangle.width = 10;
        rectangle.height = 20;
        rectangle.color = "blue";

        Rectangle anotherRectangle = (Rectangle) rectangle.clone();

        if (circle != anotherCircle) {
            System.out.println("Circle and anotherCircle are different objects :)");
        } else {
            System.out.println("Circle and another are the same object :(");
        }

        if (circle.equals(anotherCircle)) {
            System.out.println("Circle and anotherCircle are identical objects :)");
        } else {
            System.out.println("Circle and another are not identical object :(");
        }
    }
}
