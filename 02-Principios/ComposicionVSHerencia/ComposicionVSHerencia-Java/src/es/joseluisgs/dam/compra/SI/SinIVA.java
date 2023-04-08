package es.joseluisgs.dam.compra.SI;


public class SinIVA extends IVA{
    @Override
    public double getImporteConIVA(double importe) {
        return importe;
    }
}