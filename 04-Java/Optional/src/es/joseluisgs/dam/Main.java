package es.joseluisgs.dam;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello Optional!");
        sinOptional();
        conOptional();
    }

    private static void sinOptional() {
        // imaginamos que nos llega un null o no sabemos que es
        String nombre = null;
        // y se nos olvida el if...
        // System.out.println("Nombre: " + nombre.length());
        // ponemos el if, o try catch por todos lados
        if (nombre != null) {
            System.out.println("Nombre: " + nombre.length());
        }
        try {
            System.out.println("Nombre: " + nombre.length());
        } catch (NullPointerException e) {
            System.out.println("puffff");
            // throw new RuntimeException("No hay nombre");
        }
    }

    public static void conOptional() {
        String nombre = null;
        Optional<String> nombreOpt = Optional.ofNullable(nombre);
        // Podemos comrpbar si está presente o no
        if (nombreOpt.isPresent()) {
            System.out.println("Nombre: " + nombreOpt.get().length());
        }
        // o si está vacío
        if (!nombreOpt.isEmpty()) {
            System.out.println("Nombre: " + nombreOpt.get().length());
        }
        // Lo importante es que siempre que quiera usarlo necesito el get!! y si no
        // Puedo jugar con valores por defecto
        System.out.println("Nombre: " + nombreOpt.orElse("default").length());
        // O lanzar una excepción controlada...
        // System.out.println("Nombre: " + nombreOpt.orElseThrow(() -> new RuntimeException("No hay nombre")));
        Optional<String> opt = Optional.of("DAM");
        String name = opt.get();
        System.out.println("Nombre: " + name);
    }
}
