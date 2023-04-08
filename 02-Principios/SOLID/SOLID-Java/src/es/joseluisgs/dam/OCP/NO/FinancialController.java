package es.joseluisgs.dam.OCP.NO;

public class FinancialController {
    private TypeReport typeReport;
    private Report reporter;

    public FinancialController(TypeReport typeReport) {
        this.typeReport = typeReport;
    }


    public void report() {
        switch (this.typeReport) {
            case WEB:
                reporter = new WebReport();
                break;
            case XML:
                reporter = new XMLReport();
                break;
        }

        if (this.typeReport == TypeReport.WEB) {
            ((WebReport) reporter).render();
        } else {
            ((XMLReport) reporter).print();
        }
    }

    public static void main(String[] args) {
        FinancialController controller = new FinancialController(TypeReport.WEB);
        controller.report();
        controller = new FinancialController(TypeReport.XML);
        controller.report();
    }
}
