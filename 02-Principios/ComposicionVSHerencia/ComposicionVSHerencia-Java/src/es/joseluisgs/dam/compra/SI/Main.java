package es.joseluisgs.dam.compra.SI;

public class Main {
    public static void main(String[] args) {
        Compra c= new Compra("1A","ordenador",200, new IVAStandard());
        System.out.println(c.getImporteConIVA());

        Compra c2= new Compra("1A","ordenador",200, new IVAReducido());
        System.out.println(c2.getImporteConIVA());

        Compra c3= new Compra("1A","ordenador",200, new SinIVA());
        System.out.println(c2.getImporteConIVA());

        Factura f= new Factura(1,"ordenador",200,"pedro", new IVAStandard());
        System.out.println(f.getImporteConIVA());

        Factura f2= new Factura(1,"ordenador",200,"pedro", new IVAReducido());
        System.out.println(f2.getImporteConIVA());

        Factura f3= new Factura(1,"ordenador",200,"pedro", new SinIVA());
        System.out.println(f3.getImporteConIVA());
    }
}
