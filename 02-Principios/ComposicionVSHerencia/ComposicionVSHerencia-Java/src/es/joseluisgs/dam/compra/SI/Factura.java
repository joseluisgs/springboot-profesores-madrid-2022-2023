package es.joseluisgs.dam.compra.SI;

public class Factura {
    private int numero;
    private String concepto;
    private double importe;
    private String cliente;
    private IVA tipo;

    public Factura(int numero, String concepto, double importe, String cliente, IVA tipo) {
        super();
        this.numero = numero;
        this.concepto = concepto;
        this.importe = importe;
        this.cliente = cliente;
        this.tipo=tipo;
    }

    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
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
    public String getCliente() {
        return cliente;
    }
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public double getImporteConIVA() {
        return tipo.getImporteConIVA(this.getImporte());
    }
}