package es.joseluisgs.dam.OCP.SI;

public class FinancialController {
    private Report reporter;

    // Inyección de dependencias por constructor
    public FinancialController(Report reporter) {
        this.reporter = reporter;
    }


    public void report() {
        reporter.render();
    }

    public static void main(String[] args) {
        FinancialController controller = new FinancialController(new WebReport());
        controller.report();
        controller = new FinancialController(new XMLReport());
        controller.report();
    }
}
