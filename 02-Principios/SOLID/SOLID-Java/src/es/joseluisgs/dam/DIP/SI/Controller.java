package es.joseluisgs.dam.DIP.SI;

public class Controller {
    // funciona con cualquier tipo de comportamiento de notificación
    private INotifier notifier;

    // Dependencia en constructor
    public Controller(INotifier notifier) {
        this.notifier = notifier;
    }

    public void doNotify() {
        notifier.notification("Hi there!");
    }

    public static void main(String[] args) {
        Controller c = new Controller(new MailService());
        c.doNotify();
        c = new Controller(new WhatsAppService());
        c.doNotify();
    }
}
