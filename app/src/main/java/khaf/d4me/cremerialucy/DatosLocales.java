package khaf.d4me.cremerialucy;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

import java.util.ArrayList;

import khaf.d4me.cremerialucy.Models.CategorieModel;
import khaf.d4me.cremerialucy.Models.CategorieProveedorModel;
import khaf.d4me.cremerialucy.Models.InventoryModel;
import khaf.d4me.cremerialucy.Models.InventoryProveedorModel;
import khaf.d4me.cremerialucy.Models.MovementsModel;
import khaf.d4me.cremerialucy.Models.StartModel;
import khaf.d4me.cremerialucy.Models.StoreModel;

public class DatosLocales extends SQLiteOpenHelper {
    SharedPreferences Configuraciones;
    SharedPreferences.Editor EditarConfiguracion;

    private static final String PROVEEDORES_TABLE_CREATE = "CREATE TABLE Local_Proveedores(Id_Proveedor INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Proveedor TEXT, Descripcion TEXT,Editado INTEGER, Id_Proveedor_Internet INTEGER)"; //Si - Hecho

    private static final String CATEGORIAS_TABLE_CREATE = "CREATE TABLE Local_Categorias(Id_Categoria INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Categoria TEXT, Descripcion TEXT,Editado INTEGER, Id_Categoria_Internet INTEGER)"; //Si - Hecho

    private static final String CATEGORIAS_PROVEEDORES_TABLE_CREATE = "CREATE TABLE Local_Categorias_Proveedores(Id_Categoria_Proveedor INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Id_Categoria INTEGER, Id_Proveedor INTEGER, Id_Categoria_Proveedor_Internet INTEGER)";//Si - Hecho

    private static final String PRODUCTOS_TABLE_CREATE = "CREATE TABLE Local_Productos(Id_Producto INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Codigo TEXT, Producto TEXT, Minimo INTEGER, Maximo INTEGER, Punto_Reorden INTEGER, Editado INTEGER," +
            " Id_Producto_Internet INTEGER)";//Si - Hecho

    private static final String PRODUCTOS_CATEGORIAS_TABLE_CREATE = "CREATE TABLE Local_Productos_Proveedor(Id_Producto_Proveedor INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Id_Proveedor INTEGER, Id_Categoria INTEGER, Id_Producto INTEGER, Piezas INTEGER, Precio_C DECIMAL(18,2), Precio_C_Ant DECIMAL(18,2), " +
            "Precio_V DECIMAL(18,2), Ultimo_Ingreso TEXT, Descripcion TEXT, Id_Producto_Proveedor_Internet INTEGER)";//Si - Hecho

    private static final String PRODUCTOS_INICIO_TABLE_CREATE = "CREATE TABLE Local_Inicio(Id_Inicio_Tabla INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Nombre TEXT, Nombre_Imagen TEXT, Imagen_Tamano INTEGER, Texto_Tamano INTEGER, ClaseOFuncion TEXT," +
            "Tipo INTEGER, Id_Imagen_Inicio INTEGER)";

    private static final String MOVIMIENTOS_TABLE_CREATE = "CREATE TABLE Local_Movimientos(Id_Movimiento INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Id_Producto INTEGER, Id_Usuario INTEGER, Piezas INTEGER, Fecha TEXT, Estado INTEGER, Id_Movimiento_Internet " +
            "INTEGER, Tipo INTEGER)";//Si - hecho
    private static final String DB_NAME = "comments.sqlite";
    private static final int DB_VERSION = 10;
    SQLiteDatabase db;
    Context mcontext;
    public DatosLocales(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mcontext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Configuraciones = PreferenceManager.getDefaultSharedPreferences(mcontext);
        EditarConfiguracion = Configuraciones.edit();
        this.db = db;
        db.execSQL(PROVEEDORES_TABLE_CREATE);
        db.execSQL(CATEGORIAS_TABLE_CREATE);
        db.execSQL(CATEGORIAS_PROVEEDORES_TABLE_CREATE);
        db.execSQL(PRODUCTOS_TABLE_CREATE);
        db.execSQL(PRODUCTOS_CATEGORIAS_TABLE_CREATE);
        db.execSQL(PRODUCTOS_INICIO_TABLE_CREATE);
        db.execSQL(MOVIMIENTOS_TABLE_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Local_Proveedores");
        db.execSQL("DROP TABLE IF EXISTS Local_Categorias");
        db.execSQL("DROP TABLE IF EXISTS Local_Categorias_Proveedores");
        db.execSQL("DROP TABLE IF EXISTS Local_Productos");
        db.execSQL("DROP TABLE IF EXISTS Local_Productos_Proveedor");
        db.execSQL("DROP TABLE IF EXISTS Local_Inicio");
        db.execSQL("DROP TABLE IF EXISTS Local_Movimientos");
        onCreate(db);
    }

    public void UpdateProveedores(StoreModel proveedor){
        String[] args = new String[]{String.valueOf(proveedor.getIdProveedor())};
        ContentValues cv = new ContentValues();
        cv.put("Proveedor", proveedor.getProveedor());
        cv.put("Descripcion", proveedor.getDescripcion());
        cv.put("Editado", 1);
        getReadableDatabase().update("Local_Proveedores",  cv,"WHERE Id_Proveedor = ?", args);
    }
    public void UpdateCategorias(CategorieModel categoria){
        String[] args = new String[]{String.valueOf(categoria.getIdCategoria())};
        ContentValues cv = new ContentValues();
        cv.put("Categoria", categoria.getCategoria());
        cv.put("Descripcion", categoria.getDescripcion());
        cv.put("Editado", 1);
        getReadableDatabase().update("Local_Categorias",  cv,"WHERE Id_Categoria = ?", args);
    }

    public void UpdateProductos(InventoryModel producto){
        String[] args = new String[]{String.valueOf(producto.getIdProducto())};
        ContentValues cv = new ContentValues();
        cv.put("Codigo", producto.getCodigo());
        cv.put("Producto", producto.getProducto());
        cv.put("Minimo", producto.getMinimo());
        cv.put("Maximo", producto.getMaximo());
        cv.put("Punto_Reorden", producto.getPuntoReorden());
        cv.put("Editado", 1);
        getReadableDatabase().update("Local_Productos",  cv,"WHERE Id_Producto = ?", args);
    }

    //Insertar datos
    //Insertar un nuevo proveedor
    public void InsertProveedores(ArrayList<StoreModel> proveedores){
        ContentValues cv = new ContentValues();
        for(int i = 0; i<proveedores.size();i++) {
            cv.put("Proveedor", proveedores.get(i).getProveedor());
            cv.put("Descripcion", proveedores.get(i).getDescripcion());
            cv.put("Editado", 0);
            cv.put("Id_Proveedor_Internet", proveedores.get(i).getIdProveedor());
            getReadableDatabase().insert("Local_Proveedores", null, cv);
        }
    }
    //Insertar una nueva categoria
    public void InsertCategorias(ArrayList<CategorieModel> categorias){
        ContentValues cv = new ContentValues();
        for(int i = 0; i<categorias.size();i++) {
            cv.put("Categoria", categorias.get(i).getCategoria());
            cv.put("Descripcion", categorias.get(i).getDescripcion());
            cv.put("Editado", 0);
            cv.put("Id_Categoria_Internet", categorias.get(i).getIdCategoria());
            getReadableDatabase().insert("Local_Categorias", null, cv);
        }
    }
    //Insertar una nueva categoria
    public void InsertCategoriasProveedor(ArrayList<CategorieProveedorModel> categoriasproveedores){
        ContentValues cv = new ContentValues();
        for(int i = 0; i<categoriasproveedores.size();i++) {
            cv.put("Id_Categoria", categoriasproveedores.get(i).getIdCategoria());
            cv.put("Id_Proveedor", categoriasproveedores.get(i).getIdProveedor());
            cv.put("Id_Categoria_Proveedor_Internet", categoriasproveedores.get(i).getIdCategoriaInternet());
            getReadableDatabase().insert("Local_Categorias_Proveedores", null, cv);
        }
    }
    //Insertar nuevo producto
    public void InsertProductos(ArrayList<InventoryModel> productos){
        ContentValues cv = new ContentValues();
        for(int i = 0; i < productos.size();i++) {
            cv.put("Codigo", productos.get(i).getCodigo());
            cv.put("Producto", productos.get(i).getProducto());
            cv.put("Minimo", productos.get(i).getMinimo());
            cv.put("Maximo", productos.get(i).getMaximo());
            cv.put("Punto_Reorden", productos.get(i).getPuntoReorden());
            cv.put("Editado", 0);
            cv.put("Id_Producto_Internet", productos.get(i).getIdProducto());
            getReadableDatabase().insert("Local_Productos", null, cv);
        }
    }
    //Insertar nuevo producto proveedor
    public void InsertProductosProveedor(ArrayList<InventoryProveedorModel> productosproveedor){
        ContentValues cv = new ContentValues();
        for(int i = 0; i < productosproveedor.size();i++) {
            cv.put("Id_Producto", productosproveedor.get(i).getIdProductoProveedor());
            cv.put("Id_Proveedor", productosproveedor.get(i).getIdProveedor());
            cv.put("Id_Categoria", productosproveedor.get(i).getIdCategoria());
            cv.put("Piezas", productosproveedor.get(i).getPiezas());
            cv.put("Precio_C", productosproveedor.get(i).getPrecioCompra());
            cv.put("Precio_C_Ant", productosproveedor.get(i).getPrecioCompraA());
            cv.put("Precio_V", productosproveedor.get(i).getPrecioVenta());
            cv.put("Descripcion", productosproveedor.get(i).getDescripcion());
            cv.put("Ultimo_Ingreso", productosproveedor.get(i).getUltimoIngreso());
            cv.put("Id_Producto_Proveedor_Internet", productosproveedor.get(i).getIdProducto());
            getReadableDatabase().insert("Local_Productos_Proveedor", null, cv);
        }
    }
    //Insertar nuevo inicio
    public void InsertInicioTabla(ArrayList<StartModel> inicio){
        ContentValues cv = new ContentValues();
        for(int i = 0; i < inicio.size();i++) {
            cv.put("Nombre", inicio.get(i).getTitle());
            cv.put("Nombre_Imagen", inicio.get(i).getImagen());
            cv.put("Imagen_Tamano", inicio.get(i).getTamano());
            cv.put("Texto_Tamano", inicio.get(i).getTamanoTexto());
            cv.put("ClaseOFuncion", inicio.get(i).getPagina());
            cv.put("Tipo", inicio.get(i).getTipoAccion());
            cv.put("Id_Imagen_Inicio", inicio.get(i).getImgid());
            getReadableDatabase().insert("Local_Inicio", null, cv);
        }
    }
    //Insertar nuevo movimiento
    public void InsertMovimientos(ArrayList<MovementsModel> movimientos){
        ContentValues cv = new ContentValues();
        for(int i = 0; i<movimientos.size();i++) {
            cv.put("Id_Producto", movimientos.get(i).getIdProducto());
            cv.put("Id_Usuario", movimientos.get(i).getIdUsuario());
            cv.put("Piezas", movimientos.get(i).getPiezas());
            cv.put("Estado", movimientos.get(i).getEstado());
            cv.put("Fecha", movimientos.get(i).getFecha());
            cv.put("Tipo", movimientos.get(i).getTipo());
            cv.put("Id_Movimiento_Internet", movimientos.get(i).getIdMovimientoLocal());
            getReadableDatabase().insert("Local_Proveedores", null, cv);
        }
    }


    //Mostrar datos
    //Buscar los proveedores locales
    public ArrayList<StoreModel> ListarProveedores(){
        //Creamos el cursor
        ArrayList<StoreModel> proveedores = new ArrayList<StoreModel>();
        Cursor c= getReadableDatabase().rawQuery("SELECT Proveedor, Descripcion, Id_Proveedor_Internet FROM Local_Proveedores", null);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
                //Asignamos el valor en nuestras variables para crear un nuevo objeto Comentario
                String proveedor = c.getString(0);
                String descripcion = c.getString(1);
                int idInternet = c.getInt(2);
                StoreModel prov = new StoreModel(proveedor,descripcion,idInternet);
                //Añadimos el comentario a la lista
                proveedores.add(prov);
            } while (c.moveToNext());
        }
        //Cerramos el cursor
        assert c != null;
        c.close();
        return proveedores;
    }
    //Buscar las categorias locales
    public ArrayList<CategorieProveedorModel> ListarCategoriasProveedores(int IdProveedorFiltrar){
        String[] args = new String[]{String.valueOf(IdProveedorFiltrar)};
        //Creamos el cursor
        ArrayList<CategorieProveedorModel> categorias = new ArrayList<CategorieProveedorModel>();
        Cursor c= getReadableDatabase().rawQuery("SELECT c.Categoria, cp.Id_Categoria, cp.Id_Proveedor, cp.Id_Categoria_Proveedor_Internet" +
                " FROM Local_Categorias_Proveedores cp LEFT JOIN Local_Categorias c ON cp.Id_Categoria_Proveedor_Internet = " +
                "c.Id_Categoria_Internet WHERE cp.Id_Proveedor = ?", args);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
                //Asignamos el valor en nuestras variables para crear un nuevo objeto Comentario
                String categoria = c.getString(0);
                int IdCategoria = c.getInt(1);
                int IdProveedor = c.getInt(2);
                int idInternet = c.getInt(3);
                CategorieProveedorModel prov = new CategorieProveedorModel(idInternet,categoria,IdCategoria,IdProveedor);
                //Añadimos el comentario a la lista
                categorias.add(prov);
            } while (c.moveToNext());
        }
        //Cerramos el cursor
        assert c != null;
        c.close();
        return categorias;
    }
    //Buscar los productos locales
    public ArrayList<CategorieModel> ListarCategorias(){
        //Creamos el cursor
        ArrayList<CategorieModel> categorias = new ArrayList<CategorieModel>();
        //Cursor c= getReadableDatabase().rawQuery("SELECT Codigo, Producto, Minimo, Maximo, Punto_Reorden, Id_Producto_Internet FROM Local_Productos", null);
        Cursor c= getReadableDatabase().rawQuery("SELECT Categoria, Descripcion, Id_Categoria_Internet FROM Local_Categorias", null);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
                //Asignamos el valor en nuestras variables para crear un nuevo objeto Comentario
                String categoria = c.getString(0);
                String descripcion = c.getString(1);
                int idInternet = c.getInt(2);
                CategorieModel prov = new CategorieModel(categoria, descripcion, idInternet, 0);
                //Añadimos el comentario a la lista
                categorias.add(prov);
            } while (c.moveToNext());
        }
        //Cerramos el cursor
        assert c != null;
        c.close();
        return categorias;
    }
    public ArrayList<CategorieModel> ListarCategoriasSpinner(int idProv, int idCat){
        String[] args = new String[]{String.valueOf(idProv)};
        //Creamos el cursor
        ArrayList<CategorieModel> categorias = new ArrayList<CategorieModel>();
        //Cursor c= getReadableDatabase().rawQuery("SELECT Codigo, Producto, Minimo, Maximo, Punto_Reorden, Id_Producto_Internet FROM Local_Productos", null);
        Cursor c= getReadableDatabase().rawQuery("SELECT c.Categoria,c.Descripcion, cp.Id_Categoria, cp.Id_Categoria_Proveedor_Internet " +
                "FROM Local_Categorias_Proveedores cp LEFT JOIN Local_Categorias c ON cp.Id_Categoria_Proveedor_Internet = " +
                "c.Id_Categoria_Internet WHERE cp.Id_Proveedor = ?", args);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
                //Asignamos el valor en nuestras variables para crear un nuevo objeto Comentario
                String categoria = c.getString(0);
                String descripcion = c.getString(1);
                int idInternet = c.getInt(2);

                int Seleccion = 0;

                if(idCat == c.getInt(3))
                    Seleccion = 1;
                CategorieModel prov = new CategorieModel(categoria, descripcion, idInternet,Seleccion);
                //Añadimos el comentario a la lista
                categorias.add(prov);
            } while (c.moveToNext());
        }
        //Cerramos el cursor
        assert c != null;
        c.close();
        return categorias;
    }
    //Buscar los productos locales
    public ArrayList<InventoryModel> ListarProductos(){
        //Creamos el cursor
        ArrayList<InventoryModel> productos = new ArrayList<InventoryModel>();
        //Cursor c= getReadableDatabase().rawQuery("SELECT Codigo, Producto, Minimo, Maximo, Punto_Reorden, Id_Producto_Internet FROM Local_Productos", null);
        Cursor c= getReadableDatabase().rawQuery("SELECT Codigo, Producto, Minimo, Maximo, Punto_Reorden, Id_Producto_Internet FROM Local_Productos", null);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
                //Asignamos el valor en nuestras variables para crear un nuevo objeto Comentario
                String codi = c.getString(0);
                String producto = c.getString(1);
                int Min = c.getInt(2);
                int Max = c.getInt(3);
                int PuntoRe = c.getInt(4);
                int idInternet = c.getInt(5);
                InventoryModel prod = new InventoryModel(producto, idInternet,codi, Min, Max, PuntoRe);
                //Añadimos el comentario a la lista
                productos.add(prod);
            } while (c.moveToNext());
        }
        //Cerramos el cursor
        assert c != null;
        c.close();
        return productos;
    }
    //Buscar los productos de proveedores locales
    public ArrayList<InventoryProveedorModel> ListarProductosProveedores(int IdProveedorFiltrar, int IdCategoriaFiltrar){
        String[] args = new String[]{String.valueOf(IdCategoriaFiltrar), String.valueOf(IdProveedorFiltrar)};
        //Creamos el cursor
        ArrayList<InventoryProveedorModel> productosprovs = new ArrayList<InventoryProveedorModel>();
        Cursor c= getReadableDatabase().rawQuery("SELECT pp.Id_Proveedor, pp.Id_Categoria, pp.Id_Producto, pp.Piezas, " +
                "pp.Precio_C, pp.Precio_C_Ant,pp.Precio_V, pp.Ultimo_Ingreso, pp.Descripcion, pp.Id_Producto_Proveedor_Internet, " +
                "p.Producto,p.Codigo FROM Local_Productos_Proveedor pp INNER JOIN Local_Productos p ON pp.Id_Producto_Proveedor_Internet = " +
                "p.Id_Producto_Internet " +
                "WHERE Id_Categoria = ? AND Id_Proveedor = ?", args);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
                //Asignamos el valor en nuestras variables para crear un nuevo objeto Comentario
                int IdProveedor = c.getInt(0);
                int idCategoria = c.getInt(1);
                int idProducto = c.getInt(2);
                int piezas = c.getInt(3);
                float prec = c.getFloat(4);
                float preca = c.getFloat(5);
                float prev = c.getFloat(6);
                String ultin = c.getString(7);
                String desc = c.getString(8);
                int idInternet = c.getInt(9);
                String producto = c.getString(10);
                String codigo = c.getString(11);

                InventoryProveedorModel prov = new InventoryProveedorModel(idProducto,IdProveedor,idCategoria,prec,preca,
                        prev,desc,ultin,piezas,idInternet, producto, codigo, c.getCount());
                //Añadimos el comentario a la lista
                productosprovs.add(prov);
            } while (c.moveToNext());
        }
        //Cerramos el cursor
        assert c != null;
        c.close();
        return productosprovs;
    }
    public ArrayList<StartModel> ListarInicioTabla(){
        //Creamos el cursor
        ArrayList<StartModel> inicio = new ArrayList<StartModel>();
        //Cursor c= getReadableDatabase().rawQuery("SELECT Codigo, Producto, Minimo, Maximo, Punto_Reorden, Id_Producto_Internet FROM Local_Productos", null);
        Cursor c= getReadableDatabase().rawQuery("SELECT Nombre, Nombre_Imagen, Imagen_Tamano, Texto_Tamano, ClaseOFuncion," +
                "Tipo, Id_Imagen_Inicio FROM Local_Inicio", null);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
                //Asignamos el valor en nuestras variables para crear un nuevo objeto Comentario
                String nombre = c.getString(0);
                String nombreimg = c.getString(1);
                int imgtam = c.getInt(2);
                int txttam = c.getInt(3);
                String nombreClase = c.getString(4);
                int tip = c.getInt(5);
                int idimgini = c.getInt(6);

                StartModel prov = new StartModel(nombre,idimgini,imgtam,txttam,nombreClase,tip,nombreimg);
                //Añadimos el comentario a la lista
                inicio.add(prov);
            } while (c.moveToNext());
        }
        //Cerramos el cursor
        assert c != null;
        c.close();
        return inicio;
    }

    public ArrayList<MovementsModel> ListarMovimientos(int TipoM){
        String[] args = new String[]{String.valueOf(TipoM)};
        //Creamos el cursor
        ArrayList<MovementsModel> movs = new ArrayList<MovementsModel>();
        //Cursor c= getReadableDatabase().rawQuery("SELECT Codigo, Producto, Minimo, Maximo, Punto_Reorden, Id_Producto_Internet FROM Local_Productos", null);
        Cursor c= getReadableDatabase().rawQuery("SELECT p.Producto,p.Id_Producto, m.Id_Usuario, m.Piezas, m.Fecha, " +
                "m.Estado, m.Id_Movimiento_Internet, Tipo FROM Local_Movimientos m INNER JOIN Local_Productos p ON " +
                "m.Id_Producto = p.Id_Producto WHERE m.Tipo = ?", args);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
                //Asignamos el valor en nuestras variables para crear un nuevo objeto Comentario
                String prod = c.getString(0);
                int idProd = c.getInt(1);
                int idUsu = c.getInt(2);
                int pie = c.getInt(3);
                String fecha = c.getString(4);
                int est = c.getInt(5);
                int idMov = c.getInt(6);
                int tip = c.getInt(7);
                String usu = Configuraciones.getString("Usuario", "Sin");

                MovementsModel mov = new MovementsModel(prod,fecha,usu,idMov,idProd,idUsu,pie,est,tip);
                //Añadimos el comentario a la lista
                movs.add(mov);
            } while (c.moveToNext());
        }
        //Cerramos el cursor
        assert c != null;
        c.close();
        return movs;
    }


    //ContarPendientes
    public ArrayList<StoreModel> VerProveedoresSubirNuevos(){
        //Creamos el cursor
        ArrayList<StoreModel> proveedoresSubir = new ArrayList<StoreModel>();
        Cursor c = getReadableDatabase().rawQuery("SELECT Proveedor, Descripcion FROM Local_Proveedores Where " +
                "Id_Proveedor_Internet = 0", null);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
                //Asignamos el valor en nuestras variables para crear un nuevo objeto Comentario
                String proveedor = c.getString(0);
                String descripcion = c.getString(1);
                int idInternet = c.getInt(2);
                StoreModel prov = new StoreModel(proveedor,descripcion,idInternet);
                //Añadimos el comentario a la lista
                proveedoresSubir.add(prov);
            } while (c.moveToNext());
        }
        //Cerramos el cursor
        assert c != null;
        c.close();
        return proveedoresSubir;
    }

    public ArrayList<StoreModel> VerProveedoresSubirEditados(){
        //Creamos el cursor
        ArrayList<StoreModel> proveedoresSubir = new ArrayList<StoreModel>();
        Cursor c = getReadableDatabase().rawQuery("SELECT Proveedor, Descripcion FROM Local_Proveedores Where " +
                "Editado = 1", null);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
                //Asignamos el valor en nuestras variables para crear un nuevo objeto Comentario
                String proveedor = c.getString(0);
                String descripcion = c.getString(1);
                int idInternet = c.getInt(2);

                StoreModel prov = new StoreModel(proveedor,descripcion,idInternet);
                //Añadimos el comentario a la lista
                proveedoresSubir.add(prov);
            } while (c.moveToNext());
        }
        //Cerramos el cursor
        assert c != null;
        c.close();
        return proveedoresSubir;
    }

    public ArrayList<CategorieModel> VerCategoriasSubirNuevos(){
        //Creamos el cursor
        ArrayList<CategorieModel> categoriasSubir = new ArrayList<CategorieModel>();
        Cursor c = getReadableDatabase().rawQuery("SELECT Categoria, Descripcion, Id_Categoria_Internet FROM Local_Categorias Where " +
                "Id_Categoria_Internet = 0", null);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
                //Asignamos el valor en nuestras variables para crear un nuevo objeto Comentario
                String categoria = c.getString(0);
                String descripcion = c.getString(1);
                int idInternet = c.getInt(2);
                CategorieModel prov = new CategorieModel(categoria, descripcion, idInternet, 0);
                //Añadimos el comentario a la lista
                categoriasSubir.add(prov);
            } while (c.moveToNext());
        }
        //Cerramos el cursor
        assert c != null;
        c.close();
        return categoriasSubir;
    }

    public ArrayList<CategorieModel> VerCategoriasSubirEditados(){
        //Creamos el cursor
        ArrayList<CategorieModel> categoriasSubir = new ArrayList<CategorieModel>();
        Cursor c = getReadableDatabase().rawQuery("SELECT Categoria, Descripcion, Id_Categoria_Internet FROM Local_Categorias Where " +
                "Editado = 1", null);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
                //Asignamos el valor en nuestras variables para crear un nuevo objeto Comentario
                String categoria = c.getString(0);
                String descripcion = c.getString(1);
                int idInternet = c.getInt(2);
                CategorieModel prov = new CategorieModel(categoria, descripcion, idInternet, 0);
                //Añadimos el comentario a la lista
                categoriasSubir.add(prov);
            } while (c.moveToNext());
        }
        //Cerramos el cursor
        assert c != null;
        c.close();
        return categoriasSubir;
    }

    public ArrayList<InventoryModel> VerProductosSubirNuevos(){
        //Creamos el cursor
        ArrayList<InventoryModel> productosSubir = new ArrayList<InventoryModel>();
        Cursor c = getReadableDatabase().rawQuery("SELECT Codigo, Producto, Minimo, Maximo, Punto_Reorden, Id_Producto_Internet FROM Local_Productos Where " +
                "Id_Producto_Internet = 0", null);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
                //Asignamos el valor en nuestras variables para crear un nuevo objeto Comentario
                String codi = c.getString(0);
                String producto = c.getString(1);
                int Min = c.getInt(2);
                int Max = c.getInt(3);
                int PuntoRe = c.getInt(4);
                int idInternet = c.getInt(5);
                InventoryModel prod = new InventoryModel(producto, idInternet,codi, Min, Max, PuntoRe);
                //Añadimos el comentario a la lista
                productosSubir.add(prod);
            } while (c.moveToNext());
        }
        //Cerramos el cursor
        assert c != null;
        c.close();
        return productosSubir;
    }

    public ArrayList<InventoryModel> VerProductosSubirEditados(){
        //Creamos el cursor
        ArrayList<InventoryModel> productosSubir = new ArrayList<InventoryModel>();
        Cursor c = getReadableDatabase().rawQuery("SELECT Codigo, Producto, Minimo, Maximo, Punto_Reorden, Id_Producto_Internet FROM Local_Productos Where " +
                "Editado = 1", null);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
                String codi = c.getString(0);
                String producto = c.getString(1);
                int Min = c.getInt(2);
                int Max = c.getInt(3);
                int PuntoRe = c.getInt(4);
                int idInternet = c.getInt(5);
                InventoryModel prod = new InventoryModel(producto, idInternet,codi, Min, Max, PuntoRe);
                //Añadimos el comentario a la lista
                productosSubir.add(prod);
            } while (c.moveToNext());
        }
        //Cerramos el cursor
        assert c != null;
        c.close();
        return productosSubir;
    }

    public ArrayList<CategorieProveedorModel> VerCategoriaProveedorSubirNuevos(){
        ArrayList<CategorieProveedorModel> categorias = new ArrayList<CategorieProveedorModel>();
        Cursor c= getReadableDatabase().rawQuery("SELECT c.Categoria, cp.Id_Categoria, cp.Id_Proveedor, cp.Id_Categoria_Proveedor_Internet" +
                " FROM Local_Categorias_Proveedores cp LEFT JOIN Local_Categorias c ON cp.Id_Categoria_Proveedor_Internet = " +
                "c.Id_Categoria_Internet WHERE cp.Id_Categoria_Proveedor_Internet = 0", null);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
                //Asignamos el valor en nuestras variables para crear un nuevo objeto Comentario
                String categoria = c.getString(0);
                int IdCategoria = c.getInt(1);
                int IdProveedor = c.getInt(2);
                int idInternet = c.getInt(3);
                CategorieProveedorModel prov = new CategorieProveedorModel(idInternet,categoria,IdCategoria,IdProveedor);
                //Añadimos el comentario a la lista
                categorias.add(prov);
            } while (c.moveToNext());
        }
        //Cerramos el cursor
        assert c != null;
        c.close();
        return categorias;
    }

    public ArrayList<InventoryProveedorModel> VerProductoProveedorSubirNuevos(){
        //Creamos el cursor
        ArrayList<InventoryProveedorModel> productosprovs = new ArrayList<InventoryProveedorModel>();
        Cursor c= getReadableDatabase().rawQuery("SELECT pp.Id_Proveedor, pp.Id_Categoria, pp.Id_Producto, pp.Piezas, " +
                "pp.Precio_C, pp.Precio_C_Ant,pp.Precio_V, pp.Ultimo_Ingreso, pp.Descripcion, pp.Id_Producto_Proveedor_Internet, " +
                "p.Producto,p.Codigo FROM Local_Productos_Proveedor pp INNER JOIN Local_Productos p ON pp.Id_Producto_Proveedor_Internet = " +
                "p.Id_Producto_Internet WHERE Id_Producto_Proveedor_Internet = 0", null);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
                //Asignamos el valor en nuestras variables para crear un nuevo objeto Comentario
                int IdProveedor = c.getInt(0);
                int idCategoria = c.getInt(1);
                int idProducto = c.getInt(2);
                int piezas = c.getInt(3);
                float prec = c.getFloat(4);
                float preca = c.getFloat(5);
                float prev = c.getFloat(6);
                String ultin = c.getString(7);
                String desc = c.getString(8);
                int idInternet = c.getInt(9);
                String producto = c.getString(10);
                String codigo = c.getString(11);

                InventoryProveedorModel prov = new InventoryProveedorModel(idProducto,IdProveedor,idCategoria,prec,preca,
                        prev,desc,ultin,piezas,idInternet, producto, codigo, c.getCount());
                //Añadimos el comentario a la lista
                productosprovs.add(prov);
            } while (c.moveToNext());
        }
        //Cerramos el cursor
        assert c != null;
        c.close();
        return productosprovs;
    }

    public ArrayList<MovementsModel> VerMovimientosSubirNuevos(){
        //Creamos el cursor
        ArrayList<MovementsModel> movs = new ArrayList<MovementsModel>();
        //Cursor c= getReadableDatabase().rawQuery("SELECT Codigo, Producto, Minimo, Maximo, Punto_Reorden, Id_Producto_Internet FROM Local_Productos", null);
        Cursor c= getReadableDatabase().rawQuery("SELECT p.Producto,p.Id_Producto, m.Id_Usuario, m.Piezas, m.Fecha, " +
                "m.Estado, m.Id_Movimiento_Internet, Tipo FROM Local_Movimientos m INNER JOIN Local_Productos p ON " +
                "m.Id_Producto = p.Id_Producto WHERE m.Id_Movimiento_Internet = 0", null);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
                //Asignamos el valor en nuestras variables para crear un nuevo objeto Comentario
                String prod = c.getString(0);
                int idProd = c.getInt(1);
                int idUsu = c.getInt(2);
                int pie = c.getInt(3);
                String fecha = c.getString(4);
                int est = c.getInt(5);
                int idMov = c.getInt(6);
                int tip = c.getInt(7);
                String usu = Configuraciones.getString("Usuario", "Sin");

                MovementsModel mov = new MovementsModel(prod,fecha,usu,idMov,idProd,idUsu,pie,est,tip);
                //Añadimos el comentario a la lista
                movs.add(mov);
            } while (c.moveToNext());
        }
        //Cerramos el cursor
        assert c != null;
        c.close();
        return movs;
    }

    //Borrar datos (toda la tabla)
    //Proveedores
    public void ResetProveedores(){
        getReadableDatabase().delete("Local_Proveedores", "", null);
    }
    //Categorias
    public void ResetCategorias(){
        getReadableDatabase().delete("Local_Categorias", "", null);
    }
    public void ResetCategoriasProveedores(){
        getReadableDatabase().delete("Local_Categorias_Proveedores", "", null);
    }
    //Productos
    public void ResetProductos(){
        getReadableDatabase().delete("Local_Productos", "", null);
    }
    //Productos_Proveedor
    public void ResetProductosProveedores(){
        getReadableDatabase().delete("Local_Productos_Proveedor", "", null);
    }
    public void ResetInicioTabla(){
        getReadableDatabase().delete("Local_Inicio", "", null);
    }
    public void ResetMovimientos(){
        getReadableDatabase().delete("Local_Movimientos", "", null);
    }
}

