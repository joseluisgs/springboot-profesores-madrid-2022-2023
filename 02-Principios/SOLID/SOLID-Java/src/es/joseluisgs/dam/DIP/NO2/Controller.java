package es.joseluisgs.dam.DIP.NO2;

public class Controller {
    private MailService notifier;

    // Acoplado por constructor, pero añadiendo la dependencia
    public Controller(MailService notifier) {
        this.notifier = notifier;
    }

    public void doNotify() {
        notifier.notification("Hi there!");
    }

    public static void main(String[] args) {
        // Solo lo podemos usar con mail service
        Controller c = new Controller(new MailService());
        c.doNotify();
    }
}
