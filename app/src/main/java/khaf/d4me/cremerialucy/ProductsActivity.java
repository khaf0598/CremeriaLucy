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

import khaf.d4me.cremerialucy.Adapters.RecyclerViewAdapter_Inventory_General;
import khaf.d4me.cremerialucy.Adapters.RecyclerViewAdapter_Proveedores_General;
import khaf.d4me.cremerialucy.Models.InventoryModel;
import khaf.d4me.cremerialucy.Models.StoreModel;

public class ProductsActivity extends AppCompatActivity {
    ImageButton ibtnProductosAtras, ibtnAgregarProducto;
    RecyclerView rvProductos;
    ArrayList<InventoryModel> listaProductosLocal;
    RecyclerViewAdapter_Inventory_General adaptadorProductosGeneral;
    DatosLocales dbLocal;
    EditText txtBuscarProducto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        ibtnAgregarProducto = (ImageButton) findViewById(R.id.ibtnAgregarProductoGeneral);
        ibtnProductosAtras = (ImageButton) findViewById(R.id.ibtnAtrasPrincipal);

        rvProductos = (RecyclerView) findViewById(R.id.rvProductosGeneral);
        dbLocal = new DatosLocales(this);
        txtBuscarProducto = findViewById(R.id.txtBuscarProductoCat);

        LinearLayoutManager layoutManagerCategorias = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecorationCategorias = new DividerItemDecoration(rvProductos.getContext(),
                layoutManagerCategorias.getOrientation());
        dividerItemDecorationCategorias.setDrawable(getDrawable(R.drawable.divider));
        rvProductos.addItemDecoration(dividerItemDecorationCategorias);
        rvProductos.setLayoutManager(layoutManagerCategorias);
        rvProductos.setHasFixedSize(true);

        listaProductosLocal = dbLocal.ListarProductos();

        adaptadorProductosGeneral = new RecyclerViewAdapter_Inventory_General(listaProductosLocal, new RecyclerViewAdapter_Inventory_General.RecyclerViewItemClickListener() {
            @Override
            public void clickOnItem(String title, int posi) {
                Toast.makeText(getApplicationContext(),"funca si yaa",Toast.LENGTH_SHORT).show();
            }
        });

        rvProductos.setAdapter(adaptadorProductosGeneral);
        ibtnProductosAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ibtnAgregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogProductos(v);
            }
        });
        txtBuscarProducto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adaptadorProductosGeneral.getFilter().filter(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void showAlertDialogProductos(View view)
    {
        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_product_general, null);
        builder.setView(customLayout);
        AlertDialog dialog = builder.create();

        Button btnCancelarPG = (Button) customLayout.findViewById(R.id.btnCancelarAProductoG);
        Button btnAceptarPG = (Button) customLayout.findViewById(R.id.btnAceptarAProductoG);

        btnCancelarPG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnAceptarPG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Se guarda", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}