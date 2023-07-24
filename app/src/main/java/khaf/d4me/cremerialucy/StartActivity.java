package khaf.d4me.cremerialucy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        recyclerView_start = findViewById(R.id.rvMenus);

        // created new array list..
        recyclerDataArrayList = new ArrayList<>();

        // added data to array list
        recyclerDataArrayList.add(new StartModel("ALMACÉN",R.drawable.uno, 600, 45));
        recyclerDataArrayList.add(new StartModel("CATEGORIAS",R.drawable.dos, 200, 20));
        recyclerDataArrayList.add(new StartModel("CONFIGURACIÓN",R.drawable.tres, 200,20));
        recyclerDataArrayList.add(new StartModel("CERRAR SESIÓN",R.drawable.cuatro, 200,20));

        // added data from arraylist to adapter class.
        RecyclerViewAdapter_Start adapter = new RecyclerViewAdapter_Start(recyclerDataArrayList, this, new RecyclerViewAdapter_Start.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                int pos = position;
                if(pos == 0){
                    Intent intent = new Intent(StartActivity.this, StoreActivity.class);
                    startActivity(intent);
                }
                else if(pos == 1){
                    showAlertDialogButtonClicked(v);
                }else if(pos == 2){
                    Intent intent = new Intent(StartActivity.this, SettingsActivity.class);
                    startActivity(intent);
                }
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

    private RecyclerViewAdapter_Start.ClickListener mItemClick = new RecyclerViewAdapter_Start.ClickListener() {
        @Override
        public void onItemClick(int position, View v) {
            showAlertDialogButtonClicked(v);
        }
    };

    public void showAlertDialogButtonClicked(View view)
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