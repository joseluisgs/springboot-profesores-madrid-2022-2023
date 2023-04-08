package es.joseluisgs.dam.Comportamiento.Lazy;

public class Point {

    private final int x, y;
    private final Lazy<String> lazyToString;



    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        lazyToString = new Lazy<>();
    }

    @Override
    public String toString() {
        return lazyToString.get( () -> "(" + x + ", " + y + ")");
    }

}