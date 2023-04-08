package es.joseluisgs.dam.compra.SI;


public class IVAStandard extends IVA{
    @Override
    public double getImporteConIVA(double importe) {
        return importe * 1.21;
    }
}