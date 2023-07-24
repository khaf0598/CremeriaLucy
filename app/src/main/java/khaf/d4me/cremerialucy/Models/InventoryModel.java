package khaf.d4me.cremerialucy.Models;

public class InventoryModel {
    private String Producto;
    private Integer Piezas;
    private int IdProducto;
    public String getProducto() {
        return Producto;
    }

    public void setProducto(String prod) {
        this.Producto = prod;
    }

    public int getIdProducto() {
        return IdProducto;
    }
    public int getPiezas() {
        return Piezas;
    }

    public void setIdProducto(int idProd) {
        this.IdProducto = idProd;
    }
    public void setPiezas(int pie) {
        this.Piezas = pie;
    }
    public InventoryModel(String produ, int idprod, Integer pi) {
        this.Producto = produ;
        this.IdProducto = idprod;
        this.Piezas = pi;
    }
}