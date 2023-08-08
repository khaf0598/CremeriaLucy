package khaf.d4me.cremerialucy.Models;

public class InventoryProveedorModel {
    private String Descripcion, UltimoIngreso, Producto, Codigo;
    private Integer Piezas;
    private int IdProducto, IdProveedor, IdCategoria, IdProductoProveedor, PosicionTemp;
    private double PrecioCompra, PrecioCompraA,PrecioVenta;

    public String getProducto() {
        return Producto;
    }
    public void setProducto(String pro) {
        this.Producto = pro;
    }
    public String getDescripcion() {
        return Descripcion;
    }
    public void setDescripcion(String desc) {
        this.Descripcion = desc;
    }
    public String getCodigo() {
        return Codigo;
    }
    public void setPosicionTemp(int posTem) {
        this.PosicionTemp = posTem;
    }
    public int getPosicionTemp() {
        return PosicionTemp;
    }

    public int getIdProductoProveedor() {
        return IdProductoProveedor;
    }
    public void setIdProductoProveedor(int idProdProv) {
        this.IdProductoProveedor = idProdProv;
    }
    public int getIdProducto() {
        return IdProducto;
    }
    public void setIdProducto(int idProd) {
        this.IdProducto = idProd;
    }
    public int getIdProveedor() {
        return IdProveedor;
    }
    public void setIdProveedor(int idProv) {
        this.IdProveedor = idProv;
    }
    public int getIdCategoria() {
        return IdCategoria;
    }
    public void setIdCategoria(int idCat) {
        this.IdCategoria = idCat;
    }

    public String getUltimoIngreso() {
        return UltimoIngreso;
    }
    public void setUltimoIngreso(String ulting) {
        this.UltimoIngreso = ulting;
    }

    public double getPrecioCompra() {
        return PrecioCompra;
    }
    public void setPrecioCompra(float precom) {
        this.PrecioCompra = precom;
    }
    public double getPrecioCompraA() {
        return PrecioCompraA;
    }
    public void setPrecioCompraA(float precoma) {
        this.PrecioCompraA = precoma;
    }
    public double getPrecioVenta() {
        return PrecioVenta;
    }
    public void setPrecioVenta(float preven) {
        this.PrecioVenta = preven;
    }

    public int getPiezas() {
        return Piezas;
    }
    public void setPiezas(int pie) {
        this.Piezas = pie;
    }

    public InventoryProveedorModel(int idprod, Integer idprov, int idcat, double precom, double precoma, double preven,
                                   String desc, String ulting, int pie, int idproducto, String prod,
                                   String codi, int positemp) {
        this.IdCategoria = idcat;
        this.IdProducto = idprod;
        this.IdProveedor = idprov;
        this.Piezas = pie;
        this.PrecioCompra = precom;
        this.PrecioCompraA = precoma;
        this.PrecioVenta = preven;
        this.Descripcion = desc;
        this.UltimoIngreso = ulting;
        this.IdProductoProveedor = idproducto;
        this.Producto = prod;
        this.Codigo = codi;
        this.PosicionTemp = positemp;
    }
}