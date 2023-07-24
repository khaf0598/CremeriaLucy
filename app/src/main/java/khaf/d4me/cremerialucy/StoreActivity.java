package khaf.d4me.cremerialucy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import khaf.d4me.cremerialucy.Adapters.RecyclerViewAdapter_Categories;
import khaf.d4me.cremerialucy.Adapters.RecyclerViewAdapter_Store;
import khaf.d4me.cremerialucy.Models.CategorieModel;
import khaf.d4me.cremerialucy.Models.StoreModel;

public class StoreActivity extends AppCompatActivity implements RecyclerViewAdapter_Categories.RecyclerViewItemClickListener{
    ImageButton ibtnVolver;
    Button btnProveedor;
    StoreModel storeModelProveedor;
    CategorieModel storeModelCategory;
    ArrayList<StoreModel> listaProveedores;
    ArrayList<CategorieModel> listaCategorias;
    TextView lblProveedorElegido;
    private RecyclerView recyclerProveedores;
    private RecyclerViewAdapter_Store adapter_store;
    private RecyclerView recyclerCategorias;
    private RecyclerViewAdapter_Categories adapter_categories;
    DialogCategories customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        recyclerProveedores = findViewById(R.id.rvProveedores);
        // Esta línea mejora el rendimiento, si sabemos que el contenido
        // no va a afectar al tamaño del RecyclerView

        // Nuestro RecyclerView usará un linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerProveedores.setLayoutManager(layoutManager);
        recyclerProveedores.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerProveedores.getContext(),
                layoutManager.getOrientation());

        dividerItemDecoration.setDrawable(getDrawable(R.drawable.divider));
        recyclerProveedores.addItemDecoration(dividerItemDecoration);

        listaProveedores = new ArrayList<StoreModel>();
        for(int i = 0; i < 20; i++){
            storeModelProveedor = new StoreModel("Proveedor "+(i+1),i);
            listaProveedores.add(storeModelProveedor);
        }

        adapter_store = new RecyclerViewAdapter_Store(listaProveedores,this, new RecyclerViewAdapter_Store.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                clickHere(v, listaProveedores.get(position).getTitle(),listaProveedores.get(position).getIdProveedor());
            }
        });
        recyclerProveedores.setAdapter(adapter_store);

        btnProveedor = (Button) findViewById(R.id.btnAgregarProveedor);
        ibtnVolver = (ImageButton) findViewById(R.id.ibtnAtrasAlmacen);
        ibtnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnProveedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogProveedor(v);
            }
        });
    }

    public void showAlertDialogProveedor(View view)
    {
        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_supplier, null);
        builder.setView(customLayout);
        AlertDialog dialog = builder.create();

        Button btnCancelarC = (Button) customLayout.findViewById(R.id.btnCancelarAProveedor);
        Button btnAceptarC = (Button) customLayout.findViewById(R.id.btnAceptarAProveedor);

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
    public void clickHere(View view, String titulo, Integer idProv) {
        ArrayList<CategorieModel> listaCategorias = new ArrayList<CategorieModel>();
        for(int i = 0; i < 10; i++){
            CategorieModel storeModelCategory = new CategorieModel("Categoria "+(i+1), i);
            listaCategorias.add(storeModelCategory);
        }
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerProveedores.getContext(),DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getDrawable(R.drawable.divider));

        RecyclerViewAdapter_Categories dataAdapter = new RecyclerViewAdapter_Categories(listaCategorias, idProv, this, titulo);
        customDialog = new DialogCategories(StoreActivity.this, dataAdapter, titulo, dividerItemDecoration);

        customDialog.show();
        customDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void clickOnItem(String data, int pos, int idcat, String prov, int idprov) {
        if (customDialog != null){
            Intent intent = new Intent(StoreActivity.this, InventoryActivity.class);
            intent.putExtra("Categoria", data);
            intent.putExtra("IdCategoria", idcat);
            intent.putExtra("Proveedor",prov);
            intent.putExtra("IdProveedor", idprov);
            startActivity(intent);
            //customDialog.dismiss();
        }
    }
}