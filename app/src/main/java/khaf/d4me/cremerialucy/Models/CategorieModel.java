package khaf.d4me.cremerialucy.Models;

public class CategorieModel {
    private String category;
    private int IdCategoria, IdProveedor;
    public String getTitle() {
        return category;
    }
    public int getIdCategoria() {
        return IdCategoria;
    }
    public int getIdProveedor() {
        return IdProveedor;
    }
    public void setTitle(String cat) {
        this.category = cat;
    }
    public void setIdCategoria(int idCategoria) {
        this.IdCategoria = idCategoria;
    }
    public void setIdProveedor(int idProveedor) {
        this.IdProveedor = idProveedor;
    }

    public CategorieModel(String cat, int IdCat) {
        this.category = cat;
        this.IdCategoria = IdCat;
    }
}
