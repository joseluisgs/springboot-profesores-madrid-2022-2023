package es.joseluisgs.dam.Estructurales;

import es.joseluisgs.dam.Estructurales.Adapter.AdapterTest;
import es.joseluisgs.dam.Estructurales.Bridge.BridgeTest;
import es.joseluisgs.dam.Estructurales.Decorator.DecoratorTest;
import es.joseluisgs.dam.Estructurales.Facade.FacadeTest;
import es.joseluisgs.dam.Estructurales.Proxy.ProxyTest;

public class Estructurales {
    public static void main(String[] args) {
        System.out.println("Adaptador");
        new AdapterTest().test();
        System.out.println();

        System.out.println("Brdige");
        new BridgeTest().test();
        System.out.println();

        System.out.println("Decorator");
        new DecoratorTest().test();
        System.out.println();

        System.out.println("Facade");
        new FacadeTest().test();
        System.out.println();

        System.out.println("Proxy");
        new ProxyTest().test();
        System.out.println();
    }
}
