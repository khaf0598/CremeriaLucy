package khaf.d4me.cremerialucy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import khaf.d4me.cremerialucy.Models.CategorieModel;
import khaf.d4me.cremerialucy.Models.StoreModel;
import khaf.d4me.cremerialucy.R;

public class RecyclerViewAdapter_Categories extends RecyclerView.Adapter<RecyclerViewAdapter_Categories.ViewHolder>  {
    private ArrayList<CategorieModel> courseDataArrayList;
    RecyclerViewItemClickListener recyclerViewItemClickListener;
    String Proveedor;
    Integer IdProveedor;
    public RecyclerViewAdapter_Categories(ArrayList<CategorieModel> recyclerDataArrayList, Integer idProv, RecyclerViewItemClickListener listener, String Prov) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.recyclerViewItemClickListener = listener;
        this.Proveedor = Prov;
        this.IdProveedor = idProv;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_supplier_category, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder fruitViewHolder, int i) {
        CategorieModel recyclerData = courseDataArrayList.get(i);
        fruitViewHolder.mTextView.setText(recyclerData.getTitle());
    }

    @Override
    public int getItemCount() {
        return courseDataArrayList.size();
    }



    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTextView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.textView);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerViewItemClickListener.clickOnItem(courseDataArrayList.get(getAbsoluteAdapterPosition()).getTitle(), getAbsoluteAdapterPosition(), courseDataArrayList.get(getAbsoluteAdapterPosition()).getIdCategoria(), Proveedor, IdProveedor);
        }
    }

    public interface RecyclerViewItemClickListener {
        void clickOnItem(String title, int posi, int idCat, String Prov, int idProv);
    }
}