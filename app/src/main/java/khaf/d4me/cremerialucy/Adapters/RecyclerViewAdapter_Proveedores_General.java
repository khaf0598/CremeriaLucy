package khaf.d4me.cremerialucy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import khaf.d4me.cremerialucy.Models.CategorieModel;
import khaf.d4me.cremerialucy.Models.StoreModel;
import khaf.d4me.cremerialucy.R;

public class RecyclerViewAdapter_Proveedores_General extends RecyclerView.Adapter<RecyclerViewAdapter_Proveedores_General.ViewHolder> implements Filterable {
    private ArrayList<StoreModel> courseDataArrayList;
    RecyclerViewItemClickListener recyclerViewItemClickListener;

    private ArrayList<StoreModel> courseDataArrayListFilter;
    private RecyclerViewAdapter_Proveedores_General.CustomFilter mFilter;

    public RecyclerViewAdapter_Proveedores_General(ArrayList<StoreModel> recyclerDataArrayList, RecyclerViewItemClickListener listener) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.courseDataArrayListFilter = new ArrayList<StoreModel>();
        this.courseDataArrayListFilter.addAll(recyclerDataArrayList);
        this.mFilter = new RecyclerViewAdapter_Proveedores_General.CustomFilter(RecyclerViewAdapter_Proveedores_General.this);
        this.recyclerViewItemClickListener = listener;
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
        StoreModel recyclerData = courseDataArrayListFilter.get(i);
        fruitViewHolder.mTextView.setText(recyclerData.getProveedor());
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
            int position = courseDataArrayList.indexOf(courseDataArrayListFilter.get(getAbsoluteAdapterPosition()));
            if (position >= 0) {
                recyclerViewItemClickListener.clickOnItem(courseDataArrayList.get(position).getProveedor(), position);
            }
        }
    }

    public interface RecyclerViewItemClickListener {
        void clickOnItem(String title, int posi);
    }

    //Para filtros
    public class CustomFilter extends Filter {
        private RecyclerViewAdapter_Proveedores_General listAdapterProveedoresGen;

        private CustomFilter(RecyclerViewAdapter_Proveedores_General listAdapter) {
            super();
            this.listAdapterProveedoresGen = listAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            courseDataArrayListFilter = new ArrayList<StoreModel>();
            courseDataArrayListFilter.clear();
            final FilterResults results = new FilterResults();
            if (constraint.length() == 0) {
                courseDataArrayListFilter.addAll(courseDataArrayList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();
                for(int i = 0; i < courseDataArrayList.size(); i++){
                    StoreModel proveedor = courseDataArrayList.get(i);
                    if (proveedor.getProveedor().toLowerCase().startsWith(filterPattern)) {
                        courseDataArrayListFilter.add(proveedor);
                    }
                }
            }
            results.values = courseDataArrayListFilter;
            results.count = courseDataArrayListFilter.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listAdapterProveedoresGen.notifyDataSetChanged();
        }
    }
}