package es.joseluisgs.dam.compra.SI;


public class IVAReducido  extends IVA{

    @Override
    public double getImporteConIVA(double importe) {
        return importe * 1.10;
    }
}