package es.joseluisgs.dam.Avanzados;

import es.joseluisgs.dam.Avanzados.DAOVsRepository.DAOTest;
import es.joseluisgs.dam.Avanzados.DTO.DTOTest;
import es.joseluisgs.dam.Avanzados.MVC.MVCTest;

public class Avanzados {
    public static void main(String[] args) {
        System.out.println("MVC");
        new MVCTest().test();
        System.out.println();

        System.out.println("DAOVsRepository");
        new DAOTest().test();
        System.out.println();

        System.out.println("DTOTest");
        new DTOTest().test();
        System.out.println();

        System.out.println("ServiceLocatorTest");
        new DTOTest().test();
        System.out.println();
    }
}
