package khaf.d4me.cremerialucy.Models;

public class InventoryModel {
    private String Producto;
    private String Codigo;
    private int IdProducto, Minimo, Maximo, PuntoReorden;

    public String getProducto() {
        return Producto;
    }
    public void setProducto(String prod) {
        this.Producto = prod;
    }

    public int getIdProducto() {
        return IdProducto;
    }
    public void setIdProducto(int idProd) {
        this.IdProducto = idProd;
    }

    public String getCodigo() {
        return Codigo;
    }
    public void setCodigo(String cod) {
        this.Codigo = cod;
    }

    public int getMinimo() {
        return Minimo;
    }
    public void setMinimo(int min) {
        this.Minimo = min;
    }

    public int getMaximo() {
        return Maximo;
    }
    public void setMaximo(int max) {
        this.Maximo = max;
    }

    public int getPuntoReorden() {
        return PuntoReorden;
    }
    public void sePuntoReorden(int punreo) {
        this.PuntoReorden = punreo;
    }

    public InventoryModel(String produ, int idprod, String codi, int min, int max, int punreo) {
        this.Producto = produ;
        this.IdProducto = idprod;
        this.Codigo = codi;
        this.Minimo = min;
        this.Maximo = max;
        this.PuntoReorden = punreo;
    }
}