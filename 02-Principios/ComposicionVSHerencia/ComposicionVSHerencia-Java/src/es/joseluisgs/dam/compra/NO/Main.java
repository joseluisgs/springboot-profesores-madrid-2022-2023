package es.joseluisgs.dam.compra.NO;

public class Main {
    public static void main(String[] args) {
        Compra c= new Compra("1A","ordenador",200);
        System.out.println(c.getImporteConIVA());

        Compra c2= new CompraSinIva("2A","ordenador",200);
        System.out.println(c2.getImporteConIVA());

        Compra c3= new CompraIvaReducido("3A","ordenador",200);
        System.out.println(c3.getImporteConIVA());
    }
}
