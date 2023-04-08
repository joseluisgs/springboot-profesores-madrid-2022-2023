package es.joseluisgs.dam.Comportamiento;

import es.joseluisgs.dam.Comportamiento.ChainResponsability.ChainResponsabilityTest;
import es.joseluisgs.dam.Comportamiento.Lazy.Point;
import es.joseluisgs.dam.Comportamiento.Mediator.MediatorTest;
import es.joseluisgs.dam.Comportamiento.Observer.ObserverTest;
import es.joseluisgs.dam.Estructurales.Adapter.AdapterTest;

public class Comportamiento {
    public static void main(String[] args) {
        System.out.println("Chain of Responsability");
        new ChainResponsabilityTest().test();
        System.out.println();

        System.out.println("Mediator");
        new MediatorTest().test();
        System.out.println();

        System.out.println("Observer");
        new ObserverTest().test();
        System.out.println();

        System.out.println("Lazy");
        Point p = new Point(1, 2);
        System.out.println(p);
        System.out.println(p);
    }
}
