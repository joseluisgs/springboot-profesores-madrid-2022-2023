package es.joseluisgs.dam.SRP.SI;

public class TaxController {
    Repository repository;
    NotificationService notifier;

    // Inyección de dependencias por constructor, otro patrón a tener en cuenta :)
    public TaxController(Repository repository, NotificationService notifier) {
        this.repository = repository;
        this.notifier = notifier;
    }

    // Trabajo con las tasas de impuestos
    public void doWithTaxes() {
        System.out.println("Do actions with taxes...");
    }

    // Cedo responsabilidad la base de datos a un repositorio
    public void saveChanges() {
        this.repository.save();
    }

    // Cedo responsabilidad de mandar correos a un servicio de notificaciones
    public void sendEmail() {
        this.notifier.notify("Taxes changed");
    }

    public static void main(String[] args) {
        final Repository repository = new Repository();
        final NotificationService notifier = new NotificationService();
        TaxController tc = new TaxController(repository, notifier);
        tc.doWithTaxes();
        tc.saveChanges();
        tc.sendEmail();
    }
}
