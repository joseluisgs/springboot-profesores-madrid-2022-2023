package es.joseluisgs.dam.compra.NO;

public class Compra {

    private String id;
    private String concepto;
    private double importe;

    public Compra(String id, String concepto, double importe) {
        super();
        this.id = id;
        this.concepto = concepto;
        this.importe = importe;
    }


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getConcepto() {
        return concepto;
    }
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
    public double getImporte() {
        return importe;
    }
    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getImporteConIVA() {
        return this.getImporte()*1.21;
    }
}