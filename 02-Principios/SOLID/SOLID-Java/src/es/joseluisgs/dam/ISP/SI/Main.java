package es.joseluisgs.dam.ISP.SI;

public class Main {
    public static void main(String[] args) {
        ModelS tesla = new ModelS();
        tesla.accelerate();
        tesla.brake();
        tesla.autoPilot();
        tesla.ludicrous();
        Mustang mustang = new Mustang();
        mustang.accelerate();
        mustang.brake();
    }
}
