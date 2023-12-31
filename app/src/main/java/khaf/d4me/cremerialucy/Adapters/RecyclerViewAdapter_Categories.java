package khaf.d4me.cremerialucy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import khaf.d4me.cremerialucy.Models.CategorieModel;
import khaf.d4me.cremerialucy.Models.CategorieProveedorModel;
import khaf.d4me.cremerialucy.Models.StoreModel;
import khaf.d4me.cremerialucy.R;

public class RecyclerViewAdapter_Categories extends RecyclerView.Adapter<RecyclerViewAdapter_Categories.ViewHolder>  implements Filterable {
    private ArrayList<CategorieProveedorModel> courseDataArrayList;
    RecyclerViewItemClickListener recyclerViewItemClickListener;

    private ArrayList<CategorieProveedorModel> courseDataArrayListFilter;
    private RecyclerViewAdapter_Categories.CustomFilter mFilter;

    String Proveedor;
    Integer IdProveedor;
    public RecyclerViewAdapter_Categories(ArrayList<CategorieProveedorModel> recyclerDataArrayList, Integer idProv, RecyclerViewItemClickListener listener, String Prov) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.recyclerViewItemClickListener = listener;
        this.courseDataArrayListFilter = new ArrayList<CategorieProveedorModel>();
        this.courseDataArrayListFilter.addAll(recyclerDataArrayList);
        this.mFilter = new RecyclerViewAdapter_Categories.CustomFilter(RecyclerViewAdapter_Categories.this);
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
        CategorieProveedorModel recyclerData = courseDataArrayListFilter.get(i);
        fruitViewHolder.mTextView.setText(recyclerData.getCategoria());
    }

    @Override
    public int getItemCount() {
        return courseDataArrayListFilter.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
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
            int position =  courseDataArrayList.indexOf(courseDataArrayListFilter.get(getAbsoluteAdapterPosition()));
            recyclerViewItemClickListener.clickOnItem(courseDataArrayList.get(position).getCategoria(), position, courseDataArrayList.get(position).getIdCategoria(), Proveedor, IdProveedor);
        }
    }

    public interface RecyclerViewItemClickListener {
        void clickOnItem(String title, int posi, int idCat, String Prov, int idProv);
    }

    //Para filtros
    public class CustomFilter extends Filter {
        private RecyclerViewAdapter_Categories listAdapterCategoriasProv;

        private CustomFilter(RecyclerViewAdapter_Categories listAdapter) {
            super();
            this.listAdapterCategoriasProv = listAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            courseDataArrayListFilter = new ArrayList<CategorieProveedorModel>();
            courseDataArrayListFilter.clear();
            final FilterResults results = new FilterResults();
            if (constraint.length() == 0) {
                courseDataArrayListFilter.addAll(courseDataArrayList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();
                for(int i = 0; i < courseDataArrayList.size(); i++){
                    CategorieProveedorModel categoria = courseDataArrayList.get(i);
                    if (categoria.getCategoria().toLowerCase().startsWith(filterPattern)) {
                        courseDataArrayListFilter.add(categoria);
                    }
                }
            }
            results.values = courseDataArrayListFilter;
            results.count = courseDataArrayListFilter.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listAdapterCategoriasProv.notifyDataSetChanged();
        }
    }
}