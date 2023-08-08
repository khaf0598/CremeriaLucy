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
import khaf.d4me.cremerialucy.Models.InventoryModel;
import khaf.d4me.cremerialucy.R;

public class RecyclerViewAdapter_Inventory_General extends RecyclerView.Adapter<RecyclerViewAdapter_Inventory_General.ViewHolder> implements Filterable {
    private ArrayList<InventoryModel> courseDataArrayList;
    RecyclerViewItemClickListener recyclerViewItemClickListener;
    private ArrayList<InventoryModel> courseDataArrayListFilter;
    private RecyclerViewAdapter_Inventory_General.CustomFilter mFilter;

    public RecyclerViewAdapter_Inventory_General(ArrayList<InventoryModel> recyclerDataArrayList, RecyclerViewItemClickListener listener) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.courseDataArrayListFilter = new ArrayList<InventoryModel>();
        this.courseDataArrayListFilter.addAll(recyclerDataArrayList);
        this.mFilter = new RecyclerViewAdapter_Inventory_General.CustomFilter(RecyclerViewAdapter_Inventory_General.this);
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
        InventoryModel recyclerData = courseDataArrayListFilter.get(i);
        fruitViewHolder.mTextView.setText(recyclerData.getCodigo()+": "+recyclerData.getProducto());
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
                recyclerViewItemClickListener.clickOnItem(courseDataArrayList.get(position).getProducto(), position);
            }
        }
    }

    public interface RecyclerViewItemClickListener {
        void clickOnItem(String title, int posi);
    }

    //Para filtros
    public class CustomFilter extends Filter {
        private RecyclerViewAdapter_Inventory_General listAdapterProductosGen;
        private CustomFilter(RecyclerViewAdapter_Inventory_General listAdapter) {
            super();
            this.listAdapterProductosGen = listAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            courseDataArrayListFilter = new ArrayList<InventoryModel>();
            courseDataArrayListFilter.clear();
            final FilterResults results = new FilterResults();
            if (constraint.length() == 0) {
                courseDataArrayListFilter.addAll(courseDataArrayList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();
                for(int i = 0; i < courseDataArrayList.size(); i++){
                    InventoryModel producto = courseDataArrayList.get(i);
                    if (producto.getProducto().toLowerCase().startsWith(filterPattern) ||
                            producto.getCodigo().toLowerCase().contains(filterPattern)) {

                        courseDataArrayListFilter.add(producto);
                    }
                }
            }
            results.values = courseDataArrayListFilter;
            results.count = courseDataArrayListFilter.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listAdapterProductosGen.notifyDataSetChanged();
        }
    }
}