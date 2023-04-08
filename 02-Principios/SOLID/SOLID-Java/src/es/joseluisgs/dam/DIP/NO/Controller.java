package es.joseluisgs.dam.DIP.NO;

public class Controller {
    // Fuertemente acoplado!!!!
    private MailService notifier = new MailService();

    public void doNotify() {
        notifier.notification("Hi there!");
    }

    public static void main(String[] args) {
        Controller c = new Controller();
        c.doNotify();
    }
}
