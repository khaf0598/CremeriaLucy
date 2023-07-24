package khaf.d4me.cremerialucy;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    RecyclerView.Adapter adapter;
    String tit;
    DividerItemDecoration divFilas;
    public DialogCategories(Activity a, RecyclerView.Adapter adapter, String titulo, DividerItemDecoration divisor) {
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
        mLayoutManager = new LinearLayoutManager(activity);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(divFilas);
        title.setText(tit);
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
