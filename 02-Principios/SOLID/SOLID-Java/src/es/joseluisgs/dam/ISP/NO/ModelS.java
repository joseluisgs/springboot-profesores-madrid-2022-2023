package es.joseluisgs.dam.ISP.NO;

public class ModelS implements Car{
    @Override
    public void accelerate() {
        System.out.println("Model S is accelerating");
    }

    @Override
    public void brake() {
        System.out.println("Model S is braking");
    }

    @Override
    public void startEngine() {
        System.out.println("Model S is starting engine");
    }

    @Override
    public void autoPilot() {
        System.out.println("Model S is using auto pilot");
    }

    @Override
    public void ludicrous() {
        System.out.println("Model S is driving in ludicrous mode");
    }
}
