package es.joseluisgs.dam.compra.NO;

public class CompraIvaReducido extends Compra {

    public CompraIvaReducido(String id, String concepto, double importe) {
        super(id, concepto, importe);
    }

    @Override
    public double getImporteConIVA() {
        return Math.round(super.getImporteConIVA()*1.10);
    }

}