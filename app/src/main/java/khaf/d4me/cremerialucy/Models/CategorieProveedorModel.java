package khaf.d4me.cremerialucy.Models;

public class CategorieProveedorModel {
    private String category;
    private int IdCategoriaInternet, IdProveedor, IdCategoria;
    public String getCategoria() {
        return category;
    }
    public int getIdCategoria() {
        return IdCategoria;
    }
    public int getIdCategoriaInternet() {
        return IdCategoriaInternet;
    }
    public int getIdProveedor() {
        return IdProveedor;
    }
    public void setCategoria(String cat) {
        this.category = cat;
    }
    public void setIdCategoria(int idCategoria) {
        this.IdCategoria = idCategoria;
    }
    public void setIdCategoriaInternet(int idCategoriaInt) {
        this.IdCategoriaInternet = idCategoriaInt;
    }
    public void setIdProveedor(int idProveedor) {
        this.IdProveedor = idProveedor;
    }

    public CategorieProveedorModel(int IdCatI, String cat, int IdCat, int IdProv) {
        this.category = cat;
        this.IdCategoria = IdCat;
        this.IdProveedor = IdProv;
        this.IdCategoriaInternet = IdCatI;
    }
}
