package es.joseluisgs.dam.ISP.NO;

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

    // Nos las uso, pero estoy obligado a implementarlos
    @Override
    public void autoPilot() {
        throw new Error("Not supported.");
    }

    @Override
    public void ludicrous() {
        throw new Error("Not supported.");
    }
}
