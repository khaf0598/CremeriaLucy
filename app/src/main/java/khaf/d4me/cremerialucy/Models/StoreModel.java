package khaf.d4me.cremerialucy.Models;

public class StoreModel {
    private int idProveedor;
    private String proveedor;
    private String descripcion;

    public String getProveedor() {
        return proveedor;
    }
    public int getIdProveedor() {
        return idProveedor;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setProveedor(String prov) {
        this.proveedor = prov;
    }
    public void setIdProveedor(int idprov) {
        this.idProveedor = idprov;
    }
    public void setDescripcion(String descri) {
        this.descripcion = descri;
    }

    public StoreModel(String prov,String desc, int idProv) {
        this.proveedor = prov;
        this.idProveedor = idProv;
        this.descripcion = desc;
    }
}
