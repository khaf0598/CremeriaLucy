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
import khaf.d4me.cremerialucy.Adapters.RecyclerViewAdapter_Inventory;
import khaf.d4me.cremerialucy.Adapters.RecyclerViewAdapter_Permissions;
import khaf.d4me.cremerialucy.Models.CategorieModel;
import khaf.d4me.cremerialucy.Models.StoreModel;

public class CategoriesActivity extends AppCompatActivity {
    ImageButton ibtnCategoriasAtras, ibtnAgregarCategoria;
    RecyclerView rvCategorias;
    ArrayList<CategorieModel> listaCategoriasLocal;
    RecyclerViewAdapter_Categories_General adaptadorCategoriasGeneral;
    DatosLocales dbLocal;
    EditText txtBuscarCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        ibtnAgregarCategoria = (ImageButton) findViewById(R.id.ibtnAgregarCategoriasGeneral);
        ibtnCategoriasAtras = (ImageButton) findViewById(R.id.ibtnAtrasCPrincipal);
        rvCategorias = (RecyclerView) findViewById(R.id.rvCategoriasGeneral);
        dbLocal = new DatosLocales(this);
        txtBuscarCategoria = findViewById(R.id.txtBuscarCategoriasCat);

        LinearLayoutManager layoutManagerCategorias = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecorationCategorias = new DividerItemDecoration(rvCategorias.getContext(),
                layoutManagerCategorias.getOrientation());
        dividerItemDecorationCategorias.setDrawable(getDrawable(R.drawable.divider));
        rvCategorias.addItemDecoration(dividerItemDecorationCategorias);
        rvCategorias.setLayoutManager(layoutManagerCategorias);
        rvCategorias.setHasFixedSize(true);

        listaCategoriasLocal = dbLocal.ListarCategorias();

        adaptadorCategoriasGeneral = new RecyclerViewAdapter_Categories_General(listaCategoriasLocal, new RecyclerViewAdapter_Categories_General.RecyclerViewItemClickListener() {
            @Override
            public void clickOnItem(String title, int posi) {
                Toast.makeText(getApplicationContext(),"funca",Toast.LENGTH_SHORT).show();
            }
        });

        rvCategorias.setAdapter(adaptadorCategoriasGeneral);

        ibtnCategoriasAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ibtnAgregarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogCategorias(v);
            }
        });

        txtBuscarCategoria.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adaptadorCategoriasGeneral.getFilter().filter(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void showAlertDialogCategorias(View view)
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
}