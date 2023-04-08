package es.joseluisgs.dam.Estructurales.Proxy;

/**
 * Proxy es un patrón de diseño estructural que te permite proporcionar un sustituto
 * o marcador de posición para otro objeto. Un proxy controla el acceso al objeto original,
 * permitiéndote hacer algo antes o después de que la solicitud llegue al objeto original.
 */
public class ProxyTest {
    public void test() {
        Image image = new ProxyImage("test_10mb.jpg");

        //image will be loaded from disk
        image.display();
        System.out.println("");

        //image will not be loaded from disk
        image.display();
    }
}
