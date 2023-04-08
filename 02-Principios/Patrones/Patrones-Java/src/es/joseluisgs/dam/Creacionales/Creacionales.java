package es.joseluisgs.dam.Creacionales;

import es.joseluisgs.dam.Creacionales.AsbtractFactory.AbstractFactoryTest;
import es.joseluisgs.dam.Creacionales.Builder.User;
import es.joseluisgs.dam.Creacionales.Factory.Factory;
import es.joseluisgs.dam.Creacionales.Fluida.Persona;
import es.joseluisgs.dam.Creacionales.Prototype.Prototype;
import es.joseluisgs.dam.Creacionales.Singleton.Singleton;

public class Creacionales {
    public static void main(String[] args) {

        System.out.println("Singleton");
        Singleton singleton = Singleton.getInstance();
        Singleton singleton2 = Singleton.getInstance();
        Singleton singleton3 = Singleton.getInstance();
        System.out.println(singleton);
        System.out.println(singleton2);
        System.out.println(singleton3);
        System.out.println();

        System.out.println("Builder");
        User user1 = new User.UserBuilder("Lokesh", "Gupta")
                .age(30)
                .phone("1234567")
                .address("Fake address 1234")
                .build();

        System.out.println(user1);

        User user2 = new User.UserBuilder("Jack", "Reacher")
                .age(40)
                .phone("5655")
                //no address
                .build();

        System.out.println(user2);

        User user3 = new User.UserBuilder("Super", "Man")
                //No age
                //No phone
                //no address
                .build();

        System.out.println(user3);
        System.out.println();

        System.out.println("Fluida");
        Persona persona1 = Persona.of("Juan", "Perez")
                .age(30)
                .phone("1234567")
                .address("Fake address 1234");
        System.out.println(persona1);
        Persona persona2 = Persona.of("Pepe", "Palotes")
                .age(40)
                .phone("5655");
        System.out.println(persona2);
        Persona persona3 = Persona.of("Super", "Man");
        System.out.println(persona3);
        System.out.println();

        System.out.println("Prototype");
        new Prototype().test();
        System.out.println();

        System.out.println("Factory");
        new Factory().test();
        System.out.println();

        System.out.println("Abstract Factory");
        new AbstractFactoryTest().test();
        System.out.println();

    }
}
