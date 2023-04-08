package es.joseluisgs.dam.SRP.NO;

public class TaxController {
    // Trabajo con las tasas de impuestos
    public void doWithTaxes() {
        System.out.println("Do actions with taxes...");
    }

    // manejo la base de datos... smell
    public void saveChanges() {
        System.out.println("Save changes...");
    }

    // Mando emials ... smell
    public void sendEmail() {
        System.out.println("Send email...");
    }

    public static void main(String[] args) {
        TaxController tc = new TaxController();
        tc.doWithTaxes();
        tc.saveChanges();
        tc.sendEmail();
    }
}
