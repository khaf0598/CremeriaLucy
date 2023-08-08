package khaf.d4me.cremerialucy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import khaf.d4me.cremerialucy.Adapters.RecyclerViewAdapter_Movements;
import khaf.d4me.cremerialucy.Adapters.RecyclerViewAdapter_Permissions;
import khaf.d4me.cremerialucy.Adapters.RecyclerViewAdapter_Reports;
import khaf.d4me.cremerialucy.Adapters.RecyclerViewAdapter_Store;
import khaf.d4me.cremerialucy.Models.MovementsModel;
import khaf.d4me.cremerialucy.Models.PermissionsModel;
import khaf.d4me.cremerialucy.Models.ReportsModel;
import khaf.d4me.cremerialucy.Models.StoreModel;

public class SettingsActivity extends AppCompatActivity {

    ImageButton btnCerrarConfiguracion;
    Button btnAceptarConfiguracion, btnCancelarConfiguracion, btnDatos, btnPermisos, btnMovimientos;
    LinearLayout llDatosPersonales, llPermisos, llMovimientos;

    RecyclerView rvReportes, rvAccesos, rvMEntradas, rvMSalidas;
    RecyclerViewAdapter_Permissions adaptador_permisos;
    RecyclerViewAdapter_Reports adaptador_reportes;
    RecyclerViewAdapter_Movements adaptador_mov_salidas;
    RecyclerViewAdapter_Movements adaptador_mov_entradas;

    PermissionsModel ModelPermisos;
    ReportsModel ModelReportes;
    MovementsModel ModelMEntradas;
    MovementsModel ModelMSalidas;

    ArrayList<PermissionsModel> listaPermisos;
    ArrayList<ReportsModel> listaReportes;
    ArrayList<MovementsModel> listaMEntradas;
    ArrayList<MovementsModel> listaMSalidas;

    SharedPreferences Configuraciones;
    SharedPreferences.Editor EditarConfiguracion;

    EditText txtNombre, txtApellidoP, txtApellidoM, txtCorreo, txtCelular;

    DatosLocales dbLocal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Configuraciones = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        EditarConfiguracion = Configuraciones.edit();
        rvAccesos = findViewById(R.id.rvAccesosUsuario);
        rvReportes = findViewById(R.id.rvReportes);
        rvMEntradas = findViewById(R.id.rvMovimientoEntrada);
        rvMSalidas = findViewById(R.id.rvMovimientoSalida);
        btnCerrarConfiguracion = (ImageButton) findViewById(R.id.ibtnCerrarConfiguracion);
        btnAceptarConfiguracion = (Button) findViewById(R.id.btnAceptarConfiguracion);
        btnCancelarConfiguracion = (Button) findViewById(R.id.btnCancelarConfiguracion);
        btnDatos = (Button) findViewById(R.id.btnDatosPConfiguracion);
        btnPermisos = (Button) findViewById(R.id.btnPermisosConfiguracion);
        btnMovimientos = (Button) findViewById(R.id.btnMovimientosConfiguracion);
        llDatosPersonales = (LinearLayout) findViewById(R.id.llDatosPersonales);
        llPermisos = (LinearLayout) findViewById(R.id.llPermisos);
        llMovimientos = (LinearLayout) findViewById(R.id.llMovimientos);

        txtNombre = (EditText) findViewById(R.id.txtNombreUsuario);
        txtApellidoP = (EditText) findViewById(R.id.txtApellidoPUsuario);
        txtApellidoM = (EditText) findViewById(R.id.txtApellidoMUsuario);
        txtCorreo = (EditText) findViewById(R.id.txtCorreoUsuario);
        txtCelular = (EditText) findViewById(R.id.txtCelularUsuario);

        dbLocal = new DatosLocales(this);

        LinearLayoutManager layoutManagerAccesos = new LinearLayoutManager(this);
        LinearLayoutManager layoutManagerReportes = new LinearLayoutManager(this);
        LinearLayoutManager layoutManagerMovimientosEnt = new LinearLayoutManager(this);
        LinearLayoutManager layoutManagerMovimientosSal = new LinearLayoutManager(this);

        rvAccesos.setLayoutManager(layoutManagerAccesos);
        rvAccesos.setHasFixedSize(true);
        rvReportes.setLayoutManager(layoutManagerReportes);
        rvReportes.setHasFixedSize(true);
        rvMEntradas.setLayoutManager(layoutManagerMovimientosEnt);
        rvMEntradas.setHasFixedSize(true);
        rvMSalidas.setLayoutManager(layoutManagerMovimientosSal);
        rvMSalidas.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecorationAccesos = new DividerItemDecoration(rvAccesos.getContext(),
                layoutManagerAccesos.getOrientation());
        DividerItemDecoration dividerItemDecorationReportes = new DividerItemDecoration(rvAccesos.getContext(),
                layoutManagerReportes.getOrientation());
        DividerItemDecoration dividerItemDecorationMEntradas = new DividerItemDecoration(rvMEntradas.getContext(),
                layoutManagerReportes.getOrientation());
        DividerItemDecoration dividerItemDecorationMSalidas = new DividerItemDecoration(rvMSalidas.getContext(),
                layoutManagerReportes.getOrientation());

        dividerItemDecorationAccesos.setDrawable(getDrawable(R.drawable.divider));
        dividerItemDecorationReportes.setDrawable(getDrawable(R.drawable.divider));
        dividerItemDecorationMEntradas.setDrawable(getDrawable(R.drawable.divider));
        dividerItemDecorationMSalidas.setDrawable(getDrawable(R.drawable.divider));

        rvAccesos.addItemDecoration(dividerItemDecorationAccesos);
        rvReportes.addItemDecoration(dividerItemDecorationReportes);
        rvMEntradas.addItemDecoration(dividerItemDecorationMEntradas);
        rvMSalidas.addItemDecoration(dividerItemDecorationMSalidas);
        listaPermisos = new ArrayList<PermissionsModel>();
        listaReportes = new ArrayList<ReportsModel>();
        listaMEntradas = new ArrayList<MovementsModel>();
        listaMSalidas = new ArrayList<MovementsModel>();

        for(int i = 0; i < 10; i++){
            ModelPermisos = new PermissionsModel("Permiso "+(i+1),i);
            listaPermisos.add(ModelPermisos);
        }

        for(int i = 0; i < 5; i++){
            ModelReportes = new ReportsModel("Reporte "+(i+1),i);
            listaReportes.add(ModelReportes);
        }

        listaMEntradas = dbLocal.ListarMovimientos(0);
        listaMSalidas = dbLocal.ListarMovimientos(1);

        adaptador_permisos = new RecyclerViewAdapter_Permissions(listaPermisos,this);
        adaptador_reportes = new RecyclerViewAdapter_Reports(listaReportes,this);
        adaptador_mov_entradas = new RecyclerViewAdapter_Movements(listaMEntradas,this);
        adaptador_mov_salidas = new RecyclerViewAdapter_Movements(listaMSalidas,this);

        rvAccesos.setAdapter(adaptador_permisos);
        rvReportes.setAdapter(adaptador_reportes);
        rvMEntradas.setAdapter(adaptador_mov_entradas);
        rvMSalidas.setAdapter(adaptador_mov_salidas);

        if(Configuraciones.getInt("IdUsuario",-1)>-1) {
            txtNombre.setEnabled(false);
            txtApellidoP.setEnabled(false);
            txtApellidoM.setEnabled(false);
            txtCorreo.setEnabled(false);
            txtCelular.setEnabled(false);
            txtNombre.setText(Configuraciones.getString("Nombre", ""));
            txtApellidoP.setText(Configuraciones.getString("Apellido_P", ""));
            txtApellidoM.setText(Configuraciones.getString("Apellido_M", ""));
            txtCorreo.setText(Configuraciones.getString("Correo", ""));
            txtCelular.setText(Configuraciones.getString("Telefono", ""));
        }

        btnCerrarConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnAceptarConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Se guarda",Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });

        btnCancelarConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnPermisos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llDatosPersonales.setVisibility(View.GONE);
                llPermisos.setVisibility(View.VISIBLE);
                llMovimientos.setVisibility(View.GONE);
                btnPermisos.setBackgroundColor(getResources().getColor(R.color.Botones_Configuracion_Sel));
                btnDatos.setBackgroundColor(getResources().getColor(R.color.Botones_Configuracion));
                btnMovimientos.setBackgroundColor(getResources().getColor(R.color.Botones_Configuracion));
            }
        });
        btnDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llDatosPersonales.setVisibility(View.VISIBLE);
                llPermisos.setVisibility(View.GONE);
                llMovimientos.setVisibility(View.GONE);
                btnPermisos.setBackgroundColor(getResources().getColor(R.color.Botones_Configuracion));
                btnDatos.setBackgroundColor(getResources().getColor(R.color.Botones_Configuracion_Sel));
                btnMovimientos.setBackgroundColor(getResources().getColor(R.color.Botones_Configuracion));
            }
        });
        btnMovimientos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llDatosPersonales.setVisibility(View.GONE);
                llPermisos.setVisibility(View.GONE);
                llMovimientos.setVisibility(View.VISIBLE);
                btnPermisos.setBackgroundColor(getResources().getColor(R.color.Botones_Configuracion));
                btnDatos.setBackgroundColor(getResources().getColor(R.color.Botones_Configuracion));
                btnMovimientos.setBackgroundColor(getResources().getColor(R.color.Botones_Configuracion_Sel));
            }
        });
    }
}