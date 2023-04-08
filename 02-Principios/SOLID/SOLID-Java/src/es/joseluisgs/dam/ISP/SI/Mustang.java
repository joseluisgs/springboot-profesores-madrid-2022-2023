package es.joseluisgs.dam.ISP.SI;

public class Mustang implements Car {

    @Override
    public void accelerate() {
        System.out.println("Mustang is accelerating...");
    }

    @Override
    public void brake() {
        System.out.println("Mustang is braking...");
    }

    @Override
    public void startEngine() {
        System.out.println("Mustang is starting engine...");
    }

}
