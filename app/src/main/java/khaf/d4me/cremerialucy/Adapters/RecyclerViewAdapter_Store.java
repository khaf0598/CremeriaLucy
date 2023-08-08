package khaf.d4me.cremerialucy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import khaf.d4me.cremerialucy.Models.StoreModel;
import khaf.d4me.cremerialucy.R;

public class RecyclerViewAdapter_Store extends RecyclerView.Adapter<RecyclerViewAdapter_Store.RecyclerViewHolder> implements Filterable {
    private final RecyclerViewAdapter_Store.ClickListener clickListener;

    private ArrayList<StoreModel> courseDataArrayList;
    private Context mcontext;

    private ArrayList<StoreModel> courseDataArrayListFilter;
    private CustomFilter mFilter;

    public RecyclerViewAdapter_Store(ArrayList<StoreModel> recyclerDataArrayList, Context mcontext, RecyclerViewAdapter_Store.ClickListener clickListener) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.courseDataArrayListFilter = new ArrayList<StoreModel>();
        this.courseDataArrayListFilter.addAll(recyclerDataArrayList);
        this.mFilter = new CustomFilter(RecyclerViewAdapter_Store.this);
        this.clickListener = clickListener;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter_Store.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_store, parent, false);

        return new RecyclerViewAdapter_Store.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter_Store.RecyclerViewHolder holder, int position) {
        // Set the data to textview and imageview.
        StoreModel recyclerData = courseDataArrayListFilter.get(position);
        holder.ProveedorNombre.setText(recyclerData.getProveedor());
    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return courseDataArrayListFilter.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView ProveedorNombre;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            ProveedorNombre = itemView.findViewById(R.id.idProveedorModel);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position =  courseDataArrayList.indexOf(courseDataArrayListFilter.get(getAbsoluteAdapterPosition()));
            if (position >= 0) {
                clickListener.onItemClick(position, v);
            }
        }
    }
    public interface ClickListener {
        void onItemClick(int position, View v);
    }


    //Para filtros
    public class CustomFilter extends Filter {
        private RecyclerViewAdapter_Store listAdapterProveedores;

        private CustomFilter(RecyclerViewAdapter_Store listAdapter) {
            super();
            this.listAdapterProveedores = listAdapter;
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
            listAdapterProveedores.notifyDataSetChanged();
        }
    }
}