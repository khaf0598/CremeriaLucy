package khaf.d4me.cremerialucy.Models;

public class StoreModel {
    private int idProveedor;
    private String proveedor;

    public String getTitle() {
        return proveedor;
    }
    public int getIdProveedor() {
        return idProveedor;
    }

    public void setTitle(String prov) {
        this.proveedor = prov;
    }
    public void setIdProveedor(int idprov) {
        this.idProveedor = idprov;
    }

    public StoreModel(String prov, int idProv) {
        this.proveedor = prov;
        this.idProveedor = idProv;
    }
}
