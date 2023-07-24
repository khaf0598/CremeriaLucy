package khaf.d4me.cremerialucy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import khaf.d4me.cremerialucy.Adapters.RecyclerViewAdapter_Inventory;
import khaf.d4me.cremerialucy.Interfaces.ItemClickListener;
import khaf.d4me.cremerialucy.Models.InventoryModel;

public class InventoryActivity extends AppCompatActivity {
    ArrayList<InventoryModel> listaProductos;
    InventoryModel ModelProductos;
    private RecyclerView recyclerProductos;
    private RecyclerViewAdapter_Inventory adapter_inventory;
    ItemClickListener itemClickListener;
    ImageButton ibtnAtrasInventario, ibtnAgregarProducto;
    Button btnSalida, btnEntrada;
    TextView lblTitulo, lblProductoElegido;
    String Producto, Categoria, Proveedor;
    Integer IdProducto = -1;

    int idProveedor, idCategoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        recyclerProductos = findViewById(R.id.rvProductos);

        ibtnAgregarProducto = (ImageButton) findViewById(R.id.ibtnAgregarProducto);
        ibtnAtrasInventario = (ImageButton) findViewById(R.id.ibtnAtrasInventario);
        btnSalida = (Button) findViewById(R.id.btnSalida);
        btnEntrada = (Button) findViewById(R.id.btnEntrada);
        lblTitulo = (TextView) findViewById(R.id.lblProductoSeleccionado);
        lblProductoElegido = (TextView) findViewById(R.id.lblProductoElegido);

        String tituloProducto = getIntent().getExtras().getString("Proveedor")+" - "+getIntent().getExtras().getString("Categoria");
        idProveedor = getIntent().getExtras().getInt("IdProveedor");
        idCategoria = getIntent().getExtras().getInt("IdCategoria");
        Proveedor = getIntent().getExtras().getString("Proveedor");
        Categoria = getIntent().getExtras().getString("Categoria");
        lblTitulo.setText(tituloProducto);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerProductos.setLayoutManager(layoutManager);
        recyclerProductos.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerProductos.getContext(),
                layoutManager.getOrientation());

        dividerItemDecoration.setDrawable(getDrawable(R.drawable.divider));
        recyclerProductos.addItemDecoration(dividerItemDecoration);

        listaProductos = new ArrayList<InventoryModel>();
        for(int i = 0; i < 20; i++){
            ModelProductos = new InventoryModel("Producto "+(i+1),i, 10);
            listaProductos.add(ModelProductos);
        }

        adapter_inventory = new RecyclerViewAdapter_Inventory(listaProductos, new RecyclerViewAdapter_Inventory.RecyclerViewItemCheckListener() {
            @Override
            public void changeOnItem(Integer pos) {
                adapter_inventory.notifyDataSetChanged();

                Producto = listaProductos.get(pos).getProducto();
                IdProducto = listaProductos.get(pos).getIdProducto();
                lblProductoElegido.setText(Producto);
                //Toast.makeText(getApplicationContext(), "Selected : " + pos, Toast.LENGTH_SHORT).show();
            }
        }, new RecyclerViewAdapter_Inventory.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                showAlertDialogButtonClicked(v, 1, listaProductos.get(position));
            }
        });

        recyclerProductos.setAdapter(adapter_inventory);


        ibtnAtrasInventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ibtnAgregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogButtonClicked(v, 0, null);
                //Toast.makeText(getApplicationContext(),"Categoria: "+idCategoria+"-Proveedor: "+idProveedor+"-Producto: "+IdProducto,Toast.LENGTH_LONG).show();
            }
        });

        btnEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IdProducto!=-1)
                    showAlertDialogMovimiento(v, 0, String.valueOf(lblProductoElegido.getText()));
                else
                    Toast.makeText(getApplicationContext(), "Seleccione un producto", Toast.LENGTH_SHORT).show();
            }
        });
        btnSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IdProducto!=-1)
                    showAlertDialogMovimiento(v, 1, String.valueOf(lblProductoElegido.getText()));
                else
                    Toast.makeText(getApplicationContext(), "Seleccione un producto", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showAlertDialogButtonClicked(View view, Integer tipo, InventoryModel producto)
    {
        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_product, null);
        builder.setView(customLayout);
        AlertDialog dialog = builder.create();

        Spinner spnCat = (Spinner) customLayout.findViewById(R.id.spnCategorias);
        EditText txtNombreProd = (EditText) customLayout.findViewById(R.id.txtNombreAProducto);
        EditText txtPiezasProd = (EditText) customLayout.findViewById(R.id.txtPiezasAProducto);
        Button btnCancelarP = (Button) customLayout.findViewById(R.id.btnCancelarAProducto);
        Button btnAceptarP = (Button) customLayout.findViewById(R.id.btnAceptarAProducto);

        if(tipo == 1){
            txtNombreProd.setText(producto.getProducto());
            txtPiezasProd.setText(String.valueOf(producto.getPiezas()));
        }

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

        dialog.setCancelable(false);
        dialog.show();
    }

    public void showAlertDialogMovimiento(View view, int tipo, String producto)
    {
        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_motion, null);
        builder.setView(customLayout);
        AlertDialog dialog = builder.create();

        LinearLayout llMovimiento = (LinearLayout) customLayout.findViewById(R.id.llMovimiento);
        TextView lblMovimiento = (TextView) customLayout.findViewById(R.id.lblTipoMovimiento);
        TextView lblProductoMover = (TextView) customLayout.findViewById(R.id.lblProductoMovimiento);
        TextView lblMovimientoTexto = (TextView) customLayout.findViewById(R.id.lblTipoMovimientoTexto);
        Button btnCancelarM = (Button) customLayout.findViewById(R.id.btnCancelarMovimiento);
        Button btnAceptarM = (Button) customLayout.findViewById(R.id.btnAceptarMovimiento);

        if(tipo == 0) {
            lblMovimiento.setText("Entrada");
            lblMovimientoTexto.setText("Piezas a Agregar:");
            llMovimiento.setBackgroundColor(getResources().getColor(R.color.Entrada));
        }
        else {
            lblMovimiento.setText("Salida");
            lblMovimientoTexto.setText("Piezas a Retirar");
            llMovimiento.setBackgroundColor(getResources().getColor(R.color.Salida));
        }
        lblProductoMover.setText(producto);
        btnCancelarM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnAceptarM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Se guarda", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }
}