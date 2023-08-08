package khaf.d4me.cremerialucy;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import khaf.d4me.cremerialucy.Models.CategorieModel;
import khaf.d4me.cremerialucy.Models.CategorieProveedorModel;
import khaf.d4me.cremerialucy.Models.InventoryModel;
import khaf.d4me.cremerialucy.Models.InventoryProveedorModel;
import khaf.d4me.cremerialucy.Models.MessageModel;
import khaf.d4me.cremerialucy.Models.StartModel;
import khaf.d4me.cremerialucy.Models.StoreModel;
import khaf.d4me.cremerialucy.Models.UserModel;


public class Backend_Queries {
    Gson Gson_Converter = new Gson();
    private RequestQueue queue;
    UserModel usuarioVolver = null;
    DatosLocales dbLocal;
    SharedPreferences Configuraciones;
    SharedPreferences.Editor EditarConfiguracion;
    EditText txtUsuario, txtContrasena;
    ProgressBar pbCargando;
    ArrayList<StoreModel> proveedoresObtener;
    ArrayList<CategorieModel> categoriasObtener;
    ArrayList<CategorieProveedorModel> categoriasProveedoresObtener;
    ArrayList<InventoryProveedorModel> productosProveedoresObtener;
    ArrayList<InventoryModel> productosObtener;
    ArrayList<StartModel> inicioObtener;


    public void Autenticar(UserModel user, Context context, EditText txtUsu, EditText txtCon, ProgressBar pbCar){
        dbLocal = new DatosLocales(context);
        txtUsuario = (EditText) txtUsu.findViewById(R.id.txtUsuario);
        txtContrasena = (EditText) txtCon.findViewById(R.id.txtContrasena);
        pbCargando = (ProgressBar) pbCar.findViewById(R.id.pbCargar);
        Configuraciones = PreferenceManager.getDefaultSharedPreferences(context);
        EditarConfiguracion = Configuraciones.edit();
        ConexionGlobal.handleSSLHandshake();
        String URL_SERVIDOR = ConexionGlobal.url + "consultas/Autenticacion.php";

        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, URL_SERVIDOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // En este apartado se programa lo que deseamos hacer en caso de no haber errores
                        if(response.equals("ERROR 1")) {
                            Toast.makeText(context, "Es necesario ingresar usuario y contraseña", Toast.LENGTH_SHORT).show();
                        } else if(response.equals("ERROR 2")) {
                            Toast.makeText(context, "El usuario o contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                        } else {
                            if(dbLocal.ListarInicioTabla()!=null){
                            if(dbLocal.ListarInicioTabla().size()>0){
                                EditarConfiguracion.clear();
                                EditarConfiguracion.commit();
                                dbLocal.ResetProveedores();
                                dbLocal.ResetCategorias();
                                dbLocal.ResetCategoriasProveedores();
                                dbLocal.ResetProductos();
                                dbLocal.ResetProductosProveedores();
                                dbLocal.ResetInicioTabla();
                                dbLocal.ResetMovimientos();
                            }}
                            System.out.println(response.toString());
                            String respuesta = response.toString();
                            TraerInicioTabla_Internet(context, txtUsu, txtCon); //Al terminar este carga el siguiente
                            usuarioVolver = Gson_Converter.fromJson(respuesta, UserModel.class);
                            EditarConfiguracion.putInt("IdUsuario", usuarioVolver.getIdUsuario());
                            EditarConfiguracion.putString("Usuario", usuarioVolver.getUsuario());
                            EditarConfiguracion.putString("Nombre", usuarioVolver.getNombre());
                            EditarConfiguracion.putString("Apellido_P", usuarioVolver.getApellidoP());
                            EditarConfiguracion.putString("Apellido_M", usuarioVolver.getApellidoM());
                            EditarConfiguracion.putString("Correo", usuarioVolver.getCorreo());
                            EditarConfiguracion.putString("Telefono", usuarioVolver.getTelefono());
                            EditarConfiguracion.putInt("Tipo", usuarioVolver.getTipo());
                            EditarConfiguracion.commit();
                            //Toast.makeText(context, "Inicio de Sesion exitoso.", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // En caso de tener algun error en la obtencion de los datos
                pbCargando.setVisibility(View.GONE);
                Toast.makeText(context, "ERROR AL INICIAR SESION - COMUNIQUE AL ADMINISTRADOR", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // En este metodo se hace el envio de valores de la aplicacion al servidor
                Map<String, String> parametros = new Hashtable<String, String>();
                parametros.put("Usuario", user.getUsuario().toString().trim());
                parametros.put("Contrasena", user.getContrasena().toString().trim());
                System.out.println(parametros.toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


    public void TraerProveedores_Internet(Context context, TextView txtUsu, TextView txtCon){
        dbLocal = new DatosLocales(context);
        ConexionGlobal.handleSSLHandshake();
        String URL_SERVIDOR = ConexionGlobal.url + "consultas/proveedores/ListarProveedor.php";
        proveedoresObtener = new ArrayList<StoreModel>();
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, URL_SERVIDOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // En este apartado se programa lo que deseamos hacer en caso de no haber errores
                        if(response.equals("ERROR 1")) {
                            //Toast.makeText(context, "Es necesario ingresar usuario y contraseña", Toast.LENGTH_SHORT).show();
                        } else if(response.equals("ERROR 2")) {
                            //Toast.makeText(context, "El usuario o contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                System.out.println(response.toString());
                                String respuesta = response.toString();
                                JSONArray jsonarray = new JSONArray(respuesta);
                                int cuantos = 0;
                                for(int i = 0; i < jsonarray.length(); i++){
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    StoreModel proveedor = new StoreModel(jsonobject.optString("Proveedor"),jsonobject.optString("Descripcion"),
                                            jsonobject.optInt("Id_Proveedor"));
                                    proveedoresObtener.add(proveedor);
                                }
                                System.out.println("se insertaron "+cuantos);
                                dbLocal.InsertProveedores(proveedoresObtener);

                                TraerCategorias_Internet(context,txtUsu, txtCon);
                            }catch (Exception ex){
                                System.out.println("error: "+ex);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pbCargando.setVisibility(View.GONE);
                // En caso de tener algun error en la obtencion de los datos
                //Toast.makeText(context, "ERROR AL INICIAR SESION - COMUNIQUE AL ADMINISTRADOR", Toast.LENGTH_LONG).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    public void TraerCategorias_Internet(Context context, TextView txtUsu, TextView txtCon){
        dbLocal = new DatosLocales(context);
        ConexionGlobal.handleSSLHandshake();
        String URL_SERVIDOR = ConexionGlobal.url + "consultas/categorias/ListarCategoria.php";
        categoriasObtener = new ArrayList<CategorieModel>();
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, URL_SERVIDOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // En este apartado se programa lo que deseamos hacer en caso de no haber errores
                        if(response.equals("ERROR 1")) {
                            //Toast.makeText(context, "Es necesario ingresar usuario y contraseña", Toast.LENGTH_SHORT).show();
                        } else if(response.equals("ERROR 2")) {
                            //Toast.makeText(context, "El usuario o contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                System.out.println(response.toString());
                                String respuesta = response.toString();
                                JSONArray jsonarray = new JSONArray(respuesta);
                                int cuantos = 0;
                                for(int i = 0; i < jsonarray.length(); i++){
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    CategorieModel categoria = new CategorieModel(jsonobject.optString("Categoria"),
                                            jsonobject.optString("Descripcion"),
                                            jsonobject.optInt("Id_Categoria"),0);
                                    categoriasObtener.add(categoria);
                                    cuantos++;
                                }
                                System.out.println("se insertaron "+cuantos);
                                dbLocal.InsertCategorias(categoriasObtener);

                                TraerCategoriasProveedores_Internet(context, txtUsu, txtCon);
                            }catch (Exception ex){
                                System.out.println("error: "+ex);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pbCargando.setVisibility(View.GONE);

                // En caso de tener algun error en la obtencion de los datos
                //Toast.makeText(context, "ERROR AL INICIAR SESION - COMUNIQUE AL ADMINISTRADOR", Toast.LENGTH_LONG).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    public void TraerCategoriasProveedores_Internet(Context context, TextView txtUsu, TextView txtCon){
        dbLocal = new DatosLocales(context);
        ConexionGlobal.handleSSLHandshake();
        String URL_SERVIDOR = ConexionGlobal.url + "consultas/categorias/ListarCategoriaProveedor.php";
        categoriasProveedoresObtener = new ArrayList<CategorieProveedorModel>();
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, URL_SERVIDOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // En este apartado se programa lo que deseamos hacer en caso de no haber errores
                        if(response.equals("ERROR 1")) {
                            //Toast.makeText(context, "Es necesario ingresar usuario y contraseña", Toast.LENGTH_SHORT).show();
                        } else if(response.equals("ERROR 2")) {
                            //Toast.makeText(context, "El usuario o contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                System.out.println(response.toString());
                                String respuesta = response.toString();
                                JSONArray jsonarray = new JSONArray(respuesta);
                                int cuantos = 0;
                                for(int i = 0; i < jsonarray.length(); i++){
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    CategorieProveedorModel categoria = new CategorieProveedorModel(jsonobject.optInt("Id_Categoria"),
                                            jsonobject.optString("Categoria"),
                                            jsonobject.optInt("Id_Proveedor_Categoria"),
                                            jsonobject.optInt("Id_Proveedor"));
                                    categoriasProveedoresObtener.add(categoria);
                                    cuantos++;
                                }
                                System.out.println("se insertaron "+cuantos);
                                dbLocal.InsertCategoriasProveedor(categoriasProveedoresObtener);

                                TraerProductos_Internet(context, txtUsu, txtCon);
                            }catch (Exception ex){
                                System.out.println("error: "+ex);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pbCargando.setVisibility(View.GONE);

                // En caso de tener algun error en la obtencion de los datos
                //Toast.makeText(context, "ERROR AL INICIAR SESION - COMUNIQUE AL ADMINISTRADOR", Toast.LENGTH_LONG).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    public void TraerProductos_Internet(Context context, TextView txtUsu, TextView txtCon){
        dbLocal = new DatosLocales(context);
        ConexionGlobal.handleSSLHandshake();
        String URL_SERVIDOR = ConexionGlobal.url + "consultas/productos/ListarProducto.php";
        productosObtener = new ArrayList<InventoryModel>();
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, URL_SERVIDOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // En este apartado se programa lo que deseamos hacer en caso de no haber errores
                        if(response.equals("ERROR 1")) {
                            //Toast.makeText(context, "Es necesario ingresar usuario y contraseña", Toast.LENGTH_SHORT).show();
                        } else if(response.equals("ERROR 2")) {
                            //Toast.makeText(context, "El usuario o contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                System.out.println(response.toString());
                                String respuesta = response.toString();
                                JSONArray jsonarray = new JSONArray(respuesta);
                                int cuantos = 0;
                                for(int i = 0; i < jsonarray.length(); i++){
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    InventoryModel productoProveedor = new InventoryModel(
                                            jsonobject.optString("Producto"),
                                            jsonobject.optInt("Id_Producto"),
                                            jsonobject.optString("Codigo"),
                                            jsonobject.optInt("Minimo"),
                                            jsonobject.optInt("Maximo"),
                                            jsonobject.optInt("Punto_Reorden"));
                                    productosObtener.add(productoProveedor);
                                    cuantos++;
                                }
                                System.out.println("se insertaron "+cuantos);
                                dbLocal.InsertProductos(productosObtener);

                                TraerProductosProveedor_Internet(context, txtUsu, txtCon);
                            }catch (Exception ex){
                                System.out.println("error: "+ex);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pbCargando.setVisibility(View.GONE);
                // En caso de tener algun error en la obtencion de los datos
                //Toast.makeText(context, "ERROR AL INICIAR SESION - COMUNIQUE AL ADMINISTRADOR", Toast.LENGTH_LONG).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    public void TraerProductosProveedor_Internet(Context context, TextView txtUsu, TextView txtCon){
        dbLocal = new DatosLocales(context);
        ConexionGlobal.handleSSLHandshake();
        String URL_SERVIDOR = ConexionGlobal.url + "consultas/productos/ListarProductoProveedor.php";
        productosProveedoresObtener = new ArrayList<InventoryProveedorModel>();
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, URL_SERVIDOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // En este apartado se programa lo que deseamos hacer en caso de no haber errores
                        if(response.equals("ERROR 1")) {
                            //Toast.makeText(context, "Es necesario ingresar usuario y contraseña", Toast.LENGTH_SHORT).show();
                        } else if(response.equals("ERROR 2")) {
                            //Toast.makeText(context, "El usuario o contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                System.out.println(response.toString());
                                String respuesta = response.toString();
                                JSONArray jsonarray = new JSONArray(respuesta);
                                int cuantos = 0;
                                for(int i = 0; i < jsonarray.length(); i++){
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    InventoryProveedorModel productoProveedor = new InventoryProveedorModel(
                                            jsonobject.optInt("Producto_Id_Producto"),
                                            jsonobject.optInt("Proveedor_Id_Proveedor"),
                                            jsonobject.optInt("Proveedor_Id_Categoria"),
                                            jsonobject.optDouble("Precio_C"),
                                            jsonobject.optDouble("Precio_C_Ant"),
                                            jsonobject.optDouble("Precio_V"),
                                            jsonobject.optString("Descripcion"),
                                            jsonobject.optString("Ultimo_Ingreso"),
                                            jsonobject.optInt("Piezas"),
                                            jsonobject.optInt("Id_Proveedor_Producto"),
                                            "","",0);
                                    productosProveedoresObtener.add(productoProveedor);
                                    cuantos++;
                                }
                                System.out.println("se insertaron "+cuantos);
                                dbLocal.InsertProductosProveedor(productosProveedoresObtener);
                                pbCargando.setVisibility(View.GONE);
                                txtUsu.setText("");
                                txtCon.setText("");
                                Intent intent = new Intent(context.getApplicationContext(), StartActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }catch (Exception ex){
                                System.out.println("error: "+ex);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pbCargando.setVisibility(View.GONE);
                // En caso de tener algun error en la obtencion de los datos
                //Toast.makeText(context, "ERROR AL INICIAR SESION - COMUNIQUE AL ADMINISTRADOR", Toast.LENGTH_LONG).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    public void TraerInicioTabla_Internet(Context context, TextView txtUsu, TextView txtCon){
        dbLocal = new DatosLocales(context);
        ConexionGlobal.handleSSLHandshake();
        String URL_SERVIDOR = ConexionGlobal.url + "consultas/inicio/ListarInicio.php";
        inicioObtener = new ArrayList<StartModel>();
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, URL_SERVIDOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // En este apartado se programa lo que deseamos hacer en caso de no haber errores
                        if(response.equals("ERROR 1")) {
                            //Toast.makeText(context, "Es necesario ingresar usuario y contraseña", Toast.LENGTH_SHORT).show();
                        } else if(response.equals("ERROR 2")) {
                            //Toast.makeText(context, "El usuario o contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                System.out.println(response.toString());
                                String respuesta = response.toString();
                                JSONArray jsonarray = new JSONArray(respuesta);
                                int cuantos = 0;
                                for(int i = 0; i < jsonarray.length(); i++){
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    StartModel inicioTabla = new StartModel(
                                            jsonobject.optString("Nombre"),
                                            jsonobject.optInt("Id_Inicio_Table"),
                                            jsonobject.optInt("Imagen_Tamano"),
                                            jsonobject.optInt("Texto_Tamano"),
                                            jsonobject.optString("ClaseOFuncion"),
                                            jsonobject.optInt("Tipo"),
                                            jsonobject.optString("Imagen_Nombre"));
                                    inicioObtener.add(inicioTabla);
                                    cuantos++;
                                }
                                System.out.println("se insertaron "+cuantos);
                                dbLocal.InsertInicioTabla(inicioObtener);

                                TraerProveedores_Internet(context,txtUsu,txtCon);
                            }catch (Exception ex){
                                System.out.println("error: "+ex);
                            }
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pbCargando.setVisibility(View.GONE);
                // En caso de tener algun error en la obtencion de los datos
                //Toast.makeText(context, "ERROR AL INICIAR SESION - COMUNIQUE AL ADMINISTRADOR", Toast.LENGTH_LONG).show();
            }

        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
