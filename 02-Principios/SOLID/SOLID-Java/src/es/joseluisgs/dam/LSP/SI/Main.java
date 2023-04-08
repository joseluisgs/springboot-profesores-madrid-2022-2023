package es.joseluisgs.dam.LSP.SI;


public class Main {
    public static void main(String[] args) {
        Rectangle r = new Rectangle();
        r.setHeight(5);
        r.setWidth(4);
        System.out.println(r.getArea());
        System.out.println(r.getPerimeter());

        Square s = new Square();
        s.setSide(5);
        System.out.println(s.getArea());
        System.out.println(s.getPerimeter());

        Persona p = new Adulto("Jose Luis", "Garcia", "12345678A", "12345678");
        p.pagar();
        Persona n = new Niño("Pepe", "Niño", (Adulto)p);
        n.pagar();
    }
}