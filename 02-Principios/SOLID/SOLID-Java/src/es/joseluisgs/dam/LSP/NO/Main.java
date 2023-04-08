package es.joseluisgs.dam.LSP.NO;

public class Main {
    public static void main(String[] args) {
        Rectangle r = new Rectangle();
        r.setHeight(5);
        r.setWidth(4);
        System.out.println(r.getArea());
        System.out.println(r.getPerimeter());

        Rectangle s = new Square();
        s.setHeight(5);
        s.setWidth(4);
        System.out.println(s.getArea());
        System.out.println(s.getPerimeter());

        Persona p = new Persona("123", "Jose Luis", "Garcia", "123456789");
        p.pagar();
        p = new Niño("123", "Jose Luis");
        p.pagar();
    }
}