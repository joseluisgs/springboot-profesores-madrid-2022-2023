package es.joseluisgs.dam.compra.NO;

public class CompraSinIva extends Compra {
    public CompraSinIva(String id, String concepto, double importe) {
        super(id, concepto, importe);
    }

    @Override
    public double getImporteConIVA() {
        return this.getImporte();
    }
}
