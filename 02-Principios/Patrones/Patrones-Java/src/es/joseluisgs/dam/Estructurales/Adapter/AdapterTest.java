package es.joseluisgs.dam.Estructurales.Adapter;

/**
 * Adapter es un patrón de diseño estructural que permite
 * la colaboración entre objetos con interfaces incompatibles.
 * El patrón Adapter es muy común en el código Java. Se utiliza muy a menudo en sistemas basados en algún código heredado (legacy).
 * En estos casos, los adaptadores crean código heredado con clases modernas.
 */
public class AdapterTest {
    public void test() {
        AudioPlayer audioPlayer = new AudioPlayer();

        audioPlayer.play("mp3", "beyond the horizon.mp3");
        audioPlayer.play("mp4", "alone.mp4");
        audioPlayer.play("vlc", "far far away.vlc");
        audioPlayer.play("avi", "mind me.avi");
    }
}
