package es.joseluisgs.dam.ISP.NO;

public class Main {
    public static void main(String[] args) {
        Car car = new ModelS();
        car.accelerate();
        car.brake();
        car.autoPilot();
        car.ludicrous();
        car = new Mustang();
        car.accelerate();
        car.brake();
        car.autoPilot();
        car.ludicrous();
    }
}
