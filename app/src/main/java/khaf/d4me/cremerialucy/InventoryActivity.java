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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import khaf.d4me.cremerialucy.Adapters.RecyclerViewAdapter_Inventory;
import khaf.d4me.cremerialucy.Adapters.SpinnerViewAdapter_Categories;
import khaf.d4me.cremerialucy.Interfaces.ItemClickListener;
import khaf.d4me.cremerialucy.Models.CategorieModel;
import khaf.d4me.cremerialucy.Models.InventoryModel;
import khaf.d4me.cremerialucy.Models.InventoryProveedorModel;

public class InventoryActivity extends AppCompatActivity {
    ArrayList<InventoryProveedorModel> listaProductosProveedor;
    ArrayList<InventoryProveedorModel> listaProductos;
    InventoryProveedorModel ModelProductos;
    private RecyclerView recyclerProductos;
    private RecyclerViewAdapter_Inventory adapter_inventory;
    ItemClickListener itemClickListener;
    ImageButton ibtnAtrasInventario, ibtnAgregarProducto;
    Button btnSalida, btnEntrada;
    TextView lblTitulo, lblProductoElegido;
    String Producto, Categoria, Proveedor;
    Integer IdProducto = -1;
    DatosLocales dbLocal;
    EditText txtBuscarProducto;

    int idProveedor, idCategoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        recyclerProductos = findViewById(R.id.rvProductos);
        dbLocal = new DatosLocales(this);

        listaProductosProveedor = new ArrayList<InventoryProveedorModel>();
        listaProductos = new ArrayList<InventoryProveedorModel>();


        ibtnAgregarProducto = (ImageButton) findViewById(R.id.ibtnAgregarProducto);
        ibtnAtrasInventario = (ImageButton) findViewById(R.id.ibtnAtrasInventario);
        btnSalida = (Button) findViewById(R.id.btnSalida);
        btnEntrada = (Button) findViewById(R.id.btnEntrada);
        lblTitulo = (TextView) findViewById(R.id.lblProductoSeleccionado);
        lblProductoElegido = (TextView) findViewById(R.id.lblProductoElegido);
        txtBuscarProducto = (EditText) findViewById(R.id.txtBuscarProducto);

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

        listaProductosProveedor = dbLocal.ListarProductosProveedores(idProveedor,idCategoria);
        for(int i = 0; i < listaProductosProveedor.size(); i++){
            ModelProductos = new InventoryProveedorModel(listaProductosProveedor.get(i).getIdProducto(),
                    listaProductosProveedor.get(i).getIdProveedor(),
                    listaProductosProveedor.get(i).getIdCategoria(),
                    listaProductosProveedor.get(i).getPrecioCompra(),
                    listaProductosProveedor.get(i).getPrecioCompraA(),
                    listaProductosProveedor.get(i).getPrecioVenta(),
                    listaProductosProveedor.get(i).getDescripcion(),
                    listaProductosProveedor.get(i).getUltimoIngreso(),
                    listaProductosProveedor.get(i).getPiezas(),
                    listaProductosProveedor.get(i).getIdProductoProveedor(),
                    listaProductosProveedor.get(i).getProducto(),
                    listaProductosProveedor.get(i).getCodigo(),i);
            listaProductos.add(ModelProductos);
        }

        adapter_inventory = new RecyclerViewAdapter_Inventory(listaProductos,
                new RecyclerViewAdapter_Inventory.RecyclerViewItemCheckListener() {
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
        }, this);

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
                InventoryProveedorModel ProductoNuevo = new InventoryProveedorModel(0,idProveedor,idCategoria,0,
                        0,0,"","",0,0,"","",0);

                showAlertDialogButtonClicked(v, 0, ProductoNuevo);
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

        txtBuscarProducto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter_inventory.getFilter().filter(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void showAlertDialogButtonClicked(View view, Integer tipo, InventoryProveedorModel producto)
    {
        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_product, null);
        builder.setView(customLayout);
        AlertDialog dialog = builder.create();

        Spinner spnCat = (Spinner) customLayout.findViewById(R.id.spnCategorias);
        TextView lblTipoMov = (TextView) customLayout.findViewById(R.id.lblTipoMovimiento);
        EditText txtNombreProd = (EditText) customLayout.findViewById(R.id.txtNombreAProducto);
        EditText txtPiezasProd = (EditText) customLayout.findViewById(R.id.txtPiezasAProducto);
        Button btnCancelarP = (Button) customLayout.findViewById(R.id.btnCancelarAProducto);
        Button btnAceptarP = (Button) customLayout.findViewById(R.id.btnAceptarAProducto);
        SpinnerViewAdapter_Categories adapterSpinnerCat;


        ArrayList<CategorieModel> listarCategoriaSpinner = dbLocal.ListarCategoriasSpinner(producto.getIdProveedor(), producto.getIdCategoria());
        adapterSpinnerCat = new SpinnerViewAdapter_Categories(this, listarCategoriaSpinner, producto.getIdCategoria());
        spnCat.setAdapter(adapterSpinnerCat);

        if(tipo == 1){
            lblTipoMov.setText("EDITAR PRODUCTO");
            txtNombreProd.setText(producto.getProducto());
            txtPiezasProd.setText(String.valueOf(producto.getPiezas()));

            for(int i = 0; i < listarCategoriaSpinner.size(); i++){
                if(listarCategoriaSpinner.get(i).getIdSeleccionBD()==1){
                    spnCat.setSelection(i);
                    break;
                }
            }
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