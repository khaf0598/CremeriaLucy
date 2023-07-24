package khaf.d4me.cremerialucy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import khaf.d4me.cremerialucy.Adapters.RecyclerViewAdapter_Permissions;
import khaf.d4me.cremerialucy.Adapters.RecyclerViewAdapter_Reports;
import khaf.d4me.cremerialucy.Adapters.RecyclerViewAdapter_Store;
import khaf.d4me.cremerialucy.Models.PermissionsModel;
import khaf.d4me.cremerialucy.Models.ReportsModel;
import khaf.d4me.cremerialucy.Models.StoreModel;

public class SettingsActivity extends AppCompatActivity {

    ImageButton btnCerrarConfiguracion;
    Button btnAceptarConfiguracion, btnCancelarConfiguracion, btnDatos, btnPermisos;
    LinearLayout llDatosPersonales, llPermisos;

    RecyclerView rvReportes, rvAccesos;
    RecyclerViewAdapter_Permissions adaptador_permisos;
    RecyclerViewAdapter_Reports adaptador_reportes;

    PermissionsModel ModelPermisos;
    ReportsModel ModelReportes;

    ArrayList<PermissionsModel> listaPermisos;
    ArrayList<ReportsModel> listaReportes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        rvAccesos = findViewById(R.id.rvAccesosUsuario);
        rvReportes = findViewById(R.id.rvReportes);
        btnCerrarConfiguracion = (ImageButton) findViewById(R.id.ibtnCerrarConfiguracion);
        btnAceptarConfiguracion = (Button) findViewById(R.id.btnAceptarConfiguracion);
        btnCancelarConfiguracion = (Button) findViewById(R.id.btnCancelarConfiguracion);
        btnDatos = (Button) findViewById(R.id.btnDatosPConfiguracion);
        btnPermisos = (Button) findViewById(R.id.btnPermisosConfiguracion);
        llDatosPersonales = (LinearLayout) findViewById(R.id.llDatosPersonales);
        llPermisos = (LinearLayout) findViewById(R.id.llPermisos);

        LinearLayoutManager layoutManagerAccesos = new LinearLayoutManager(this);
        LinearLayoutManager layoutManagerReportes = new LinearLayoutManager(this);
        rvAccesos.setLayoutManager(layoutManagerAccesos);
        rvAccesos.setHasFixedSize(true);
        rvReportes.setLayoutManager(layoutManagerReportes);
        rvReportes.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecorationAccesos = new DividerItemDecoration(rvAccesos.getContext(),
                layoutManagerAccesos.getOrientation());
        DividerItemDecoration dividerItemDecorationReportes = new DividerItemDecoration(rvAccesos.getContext(),
                layoutManagerReportes.getOrientation());

        dividerItemDecorationAccesos.setDrawable(getDrawable(R.drawable.divider));
        dividerItemDecorationReportes.setDrawable(getDrawable(R.drawable.divider));

        rvAccesos.addItemDecoration(dividerItemDecorationAccesos);
        rvReportes.addItemDecoration(dividerItemDecorationReportes);
        listaPermisos = new ArrayList<PermissionsModel>();
        listaReportes = new ArrayList<ReportsModel>();

        for(int i = 0; i < 10; i++){
            ModelPermisos = new PermissionsModel("Permiso "+(i+1),i);
            listaPermisos.add(ModelPermisos);
        }
        for(int i = 0; i < 5; i++){
            ModelReportes = new ReportsModel("Reporte "+(i+1),i);
            listaReportes.add(ModelReportes);
        }

        adaptador_permisos = new RecyclerViewAdapter_Permissions(listaPermisos,this);
        adaptador_reportes = new RecyclerViewAdapter_Reports(listaReportes,this);

        rvAccesos.setAdapter(adaptador_permisos);
        rvReportes.setAdapter(adaptador_reportes);

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
                btnPermisos.setBackgroundColor(getResources().getColor(R.color.Botones_Configuracion_Sel));
                btnDatos.setBackgroundColor(getResources().getColor(R.color.Botones_Configuracion));
            }
        });
        btnDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llDatosPersonales.setVisibility(View.VISIBLE);
                llPermisos.setVisibility(View.GONE);
                btnPermisos.setBackgroundColor(getResources().getColor(R.color.Botones_Configuracion));
                btnDatos.setBackgroundColor(getResources().getColor(R.color.Botones_Configuracion_Sel));
            }
        });
    }
}