package es.joseluisgs.dam.DIP.SI;

public class WhatsAppService implements INotifier {
    public void notification(String message) {
        System.out.println("Sending whatsapp with message " + message);
    }
}
