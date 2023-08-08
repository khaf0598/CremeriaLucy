package khaf.d4me.cremerialucy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import khaf.d4me.cremerialucy.Adapters.RecyclerViewAdapter_Categories_General;
import khaf.d4me.cremerialucy.Adapters.RecyclerViewAdapter_Proveedores_General;
import khaf.d4me.cremerialucy.Models.CategorieModel;
import khaf.d4me.cremerialucy.Models.StoreModel;

public class ProveedorActivity extends AppCompatActivity {
    ImageButton ibtnProveedoresAtras, ibtnAgregarProveedor;
    RecyclerView rvProveedores;
    ArrayList<StoreModel> listaProveedoresLocal;
    RecyclerViewAdapter_Proveedores_General adaptadorProveedoresGeneral;
    DatosLocales dbLocal;
    EditText txtCategoriaBuscar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor);

        ibtnAgregarProveedor = (ImageButton) findViewById(R.id.ibtnAgregarProveedoresGeneral);
        ibtnProveedoresAtras = (ImageButton) findViewById(R.id.ibtnAtrasPPrincipal);

        rvProveedores = (RecyclerView) findViewById(R.id.rvProveedoresGeneral);
        dbLocal = new DatosLocales(this);
        txtCategoriaBuscar = findViewById(R.id.txtBuscarProveedoresCat);

        LinearLayoutManager layoutManagerCategorias = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecorationCategorias = new DividerItemDecoration(rvProveedores.getContext(),
                layoutManagerCategorias.getOrientation());
        dividerItemDecorationCategorias.setDrawable(getDrawable(R.drawable.divider));
        rvProveedores.addItemDecoration(dividerItemDecorationCategorias);
        rvProveedores.setLayoutManager(layoutManagerCategorias);
        rvProveedores.setHasFixedSize(true);

        listaProveedoresLocal = dbLocal.ListarProveedores();

        adaptadorProveedoresGeneral = new RecyclerViewAdapter_Proveedores_General(listaProveedoresLocal, new RecyclerViewAdapter_Proveedores_General.RecyclerViewItemClickListener() {
            @Override
            public void clickOnItem(String title, int posi) {
                Toast.makeText(getApplicationContext(),"funca si",Toast.LENGTH_SHORT).show();
            }
        });

        rvProveedores.setAdapter(adaptadorProveedoresGeneral);

        ibtnProveedoresAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ibtnAgregarProveedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogProveedores(v);
            }
        });

        txtCategoriaBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adaptadorProveedoresGeneral.getFilter().filter(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void showAlertDialogProveedores(View view)
    {
        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_supplier, null);
        builder.setView(customLayout);
        AlertDialog dialog = builder.create();

        Button btnCancelarP = (Button) customLayout.findViewById(R.id.btnCancelarAProveedor);
        Button btnAceptarP = (Button) customLayout.findViewById(R.id.btnAceptarAProveedor);

        btnCancelarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnAceptarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Se guarda", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}