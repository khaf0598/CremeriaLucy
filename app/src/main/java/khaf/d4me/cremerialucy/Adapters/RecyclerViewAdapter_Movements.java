package khaf.d4me.cremerialucy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import khaf.d4me.cremerialucy.Models.CategorieModel;
import khaf.d4me.cremerialucy.Models.MovementsModel;
import khaf.d4me.cremerialucy.R;

public class RecyclerViewAdapter_Movements extends RecyclerView.Adapter<RecyclerViewAdapter_Movements.ViewHolder>  {
    private ArrayList<MovementsModel> courseDataArrayList;
    RecyclerViewItemClickListener recyclerViewItemClickListener;

    public RecyclerViewAdapter_Movements(ArrayList<MovementsModel> recyclerDataArrayList, Context mcontext) {
        this.courseDataArrayList = recyclerDataArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_table_movements, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder fruitViewHolder, int i) {
        MovementsModel recyclerData = courseDataArrayList.get(i);
        fruitViewHolder.lblProductoTabla.setText(recyclerData.getProducto());
        fruitViewHolder.lblPiezaTabla.setText(String.valueOf(recyclerData.getPiezas()));
    }

    @Override
    public int getItemCount() {
        return courseDataArrayList.size();
    }



    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView lblProductoTabla,lblPiezaTabla;

        public ViewHolder(View v) {
            super(v);
            lblProductoTabla = (TextView) v.findViewById(R.id.lblProductoTablaM);
            lblPiezaTabla = (TextView) v.findViewById(R.id.lblPiezasTablaM);
            //v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerViewItemClickListener.clickOnItem(courseDataArrayList.get(getAbsoluteAdapterPosition()).getProducto(), getAbsoluteAdapterPosition());
        }
    }

    public interface RecyclerViewItemClickListener {
        void clickOnItem(String title, int posi);
    }
}