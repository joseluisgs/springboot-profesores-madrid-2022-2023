package es.joseluisgs.dam.LSP.SI;

public class Square implements Parallelogram {
    private double side;

    public Square(double side) {
        this.side = side;
    }

    public Square() {
    }

    public void setSide(double side) {
        this.side = side;
    }

    @Override
    public double getArea() {
        return side * side;
    }

    @Override
    public double getPerimeter() {
        return 4 * side;
    }
}
