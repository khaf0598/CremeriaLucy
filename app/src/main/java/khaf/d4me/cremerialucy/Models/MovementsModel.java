package khaf.d4me.cremerialucy.Models;

public class MovementsModel {
    private String Producto,Fecha,Usuario;
    private int IdMovimientoLocal,IdProducto,IdUsuario,Piezas,Estado, Tipo;


    public String getProducto() {
        return Producto;
    }
    public String getFecha() {
        return Fecha;
    }
    public String getUsuario() {
        return Usuario;
    }
    public int getIdMovimientoLocal() {
        return IdMovimientoLocal;
    }
    public int getIdProducto() {
        return IdProducto;
    }
    public int getIdUsuario() {
        return IdUsuario;
    }
    public int getPiezas() {
        return Piezas;
    }
    public int getEstado() {
        return Estado;
    }
    public int getTipo() {
        return Tipo;
    }

    public void setProducto(String pro) {
        this.Producto = pro;
    }
    public void setFecha(String fec) {
        this.Fecha = fec;
    }
    public void setUsuario(String usu) {
        this.Usuario = usu;
    }
    public void setIdMovimientoLocal(int idmov) {
        this.IdMovimientoLocal = idmov;
    }
    public void setIdProducto(int idpro) {
        this.IdProducto = idpro;
    }
    public void setIdUsuario(int idusu) {
        this.IdUsuario = idusu;
    }
    public void setPiezas(int pie) {
        this.Piezas = pie;
    }
    public void setEstado(int est) {
        this.Estado = est;
    }
    public void setTipo(int tip) {
        this.Tipo = tip;
    }

    public MovementsModel(String prod, String fec, String usu, int IdMovLoc, int IdPro,int IdUsu, int Pie,int Est, int tip) {
        this.Producto = prod;
        this.Fecha = fec;
        this.Usuario = usu;
        this.IdMovimientoLocal = IdMovLoc;
        this.IdProducto = IdPro;
        this.IdUsuario = IdUsu;
        this.Piezas = Pie;
        this.Estado = Est;
        this.Tipo = tip;
    }
}
