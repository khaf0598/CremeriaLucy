package khaf.d4me.cremerialucy;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import khaf.d4me.cremerialucy.Adapters.RecyclerViewAdapter_Categories;

public class DialogCategories extends Dialog implements View.OnClickListener {


    public DialogCategories(Context context, int themeResId) {
        super(context, themeResId);
    }

    public DialogCategories(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    public Activity activity;
    public Dialog dialog;
    TextView title;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    RecyclerViewAdapter_Categories adapter;
    String tit;
    DividerItemDecoration divFilas;
    EditText txtBuscarCategoria;
    public DialogCategories(Activity a, RecyclerViewAdapter_Categories adapter, String titulo,
                            DividerItemDecoration divisor) {
        super(a);
        this.activity = a;
        this.adapter = adapter;
        this.tit = titulo;
        this.divFilas = divisor;
        setupLayout();
    }

    private void setupLayout() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_supplier);
        title = findViewById(R.id.lblCategoriaProveedor);
        recyclerView = findViewById(R.id.rvProveedoresCategorias);
        txtBuscarCategoria = findViewById(R.id.txtBuscarCategoria);

        mLayoutManager = new LinearLayoutManager(activity);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(divFilas);
        title.setText(tit);

        txtBuscarCategoria.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
        dismiss();
    }
}
