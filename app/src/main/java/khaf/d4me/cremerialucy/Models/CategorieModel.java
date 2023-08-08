package khaf.d4me.cremerialucy.Models;

public class CategorieModel {
    private String category, descripcion;
    private int IdCategoria;
    private int IdSeleccionBD;
    public String getCategoria() {
        return category;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public int getIdCategoria() {
        return IdCategoria;
    }
    public int getIdSeleccionBD() {
        return IdSeleccionBD;
    }
    public void setCategoria(String cat) {
        this.category = cat;
    }
    public void setDescripcion(String desc) {
        this.descripcion = desc;
    }
    public void setIdCategoria(int idCategoria) {
        this.IdCategoria = idCategoria;
    }
    public void setIdSeleccionBD(int idSelBd) {
        this.IdSeleccionBD = idSelBd;
    }

    public CategorieModel(String cat,String desc, int IdCat, int IdSel) {
        this.category = cat;
        this.descripcion = desc;
        this.IdCategoria = IdCat;
        this.IdSeleccionBD = IdSel;
    }
}
