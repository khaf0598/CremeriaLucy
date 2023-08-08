package khaf.d4me.cremerialucy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import khaf.d4me.cremerialucy.Adapters.RecyclerViewAdapter_Start;
import khaf.d4me.cremerialucy.Models.StartModel;

public class StartActivity extends AppCompatActivity {
    int spanCount = 2;
    private RecyclerView recyclerView_start;
    private ArrayList<StartModel> recyclerDataArrayList;
    SharedPreferences Configuraciones;
    SharedPreferences.Editor EditarConfiguracion;
    DatosLocales dbLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Configuraciones = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        EditarConfiguracion = Configuraciones.edit();
        dbLocal = new DatosLocales(this);
        recyclerDataArrayList = new ArrayList<StartModel>();
        recyclerView_start = findViewById(R.id.rvMenus);

        // created new array list..
        // added data to array list
        recyclerDataArrayList = dbLocal.ListarInicioTabla();

        //recyclerDataArrayList.add(new StartModel("ALMACÉN",0, 600, 45,"StoreActivity",0,"uno"));
        //recyclerDataArrayList.add(new StartModel("CATEGORIAS",0, 200, 20,"CategoriesActivity",0,"seis"));
        //recyclerDataArrayList.add(new StartModel("PROVEEDORES",0, 200, 20,"ProveedorActivity",0,"dos"));
        //recyclerDataArrayList.add(new StartModel("PRODUCTOS",0, 200, 20,"ProductsActivity",0,"cinco"));
        //recyclerDataArrayList.add(new StartModel("ACTUALIZACIÓN",0, 200,20,"UpdatesActivity",0,"siete"));
        //recyclerDataArrayList.add(new StartModel("CONFIGURACIÓN",0, 200,20,"SettingsActivity",0,"tres"));
        //recyclerDataArrayList.add(new StartModel("CERRAR SESIÓN",0, 200,20,"AccionLocal",1,"cuatro"));

        // added data from arraylist to adapter class.
        RecyclerViewAdapter_Start adapter = new RecyclerViewAdapter_Start(recyclerDataArrayList, this, new RecyclerViewAdapter_Start.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                try {
                    if(recyclerDataArrayList.get(position).getTipoAccion()==0) {
                        Intent intent = new Intent(StartActivity.this, Class.forName("khaf.d4me.cremerialucy."+recyclerDataArrayList.get(position).getPagina()));
                        startActivity(intent);
                    }else
                        showAlertDialogCerrarSesion(v);
                }catch (Exception ex){}
            }
        });

        // setting grid layout manager to implement grid view.
        // in this method '2' represents number of columns to be displayed in grid view.
        GridLayoutManager layoutManager=new GridLayoutManager(this, spanCount);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //define span size for this position
                //for example, if you have 2 column per row, you can implement something like that:
                if(position == 0) {
                    return 2; //item will take 2 column (full row size)
                } else {
                    return 1; //you will have 2 rolumn per row
                }
            }

        });

        // at last set adapter to recycler view.
        recyclerView_start.setLayoutManager(layoutManager);
        recyclerView_start.setAdapter(adapter);
    }


    public void showAlertDialogProductos(View view)
    {
        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_category, null);
        builder.setView(customLayout);
        AlertDialog dialog = builder.create();

        Button btnCancelarC = (Button) customLayout.findViewById(R.id.btnCancelarACategoria);
        Button btnAceptarC = (Button) customLayout.findViewById(R.id.btnAceptarACategoria);

        btnCancelarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnAceptarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Se guarda", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    public void showAlertDialogCerrarSesion(View view)
    {
        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_close_session, null);
        builder.setView(customLayout);
        AlertDialog dialog = builder.create();

        Button btnCancelarC = (Button) customLayout.findViewById(R.id.btnCancelarCerrarSesion);
        Button btnAceptarC = (Button) customLayout.findViewById(R.id.btnAceptarCerrarSesion);

        btnCancelarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnAceptarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditarConfiguracion.clear();
                EditarConfiguracion.commit();
                if(Configuraciones.getInt("IdUsuario",-1)<=-1)
                {
                    dbLocal.ResetProveedores();
                    dbLocal.ResetCategorias();
                    dbLocal.ResetCategoriasProveedores();
                    dbLocal.ResetProductos();
                    dbLocal.ResetProductosProveedores();
                    dbLocal.ResetInicioTabla();
                    dbLocal.ResetMovimientos();
                    finish();
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }



    @Override
    public void onBackPressed() {
    }
}