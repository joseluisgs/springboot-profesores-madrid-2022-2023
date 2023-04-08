package es.joseluisgs.dam.DIP.SI;

public class MailService implements INotifier {
    public void notification(String message) {
        System.out.println("Sending email with message " + message);
    }
}
