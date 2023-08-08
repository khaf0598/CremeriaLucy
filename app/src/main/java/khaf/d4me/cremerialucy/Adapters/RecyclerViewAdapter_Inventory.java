package khaf.d4me.cremerialucy.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import khaf.d4me.cremerialucy.Interfaces.ItemClickListener;
import khaf.d4me.cremerialucy.Models.CategorieModel;
import khaf.d4me.cremerialucy.Models.CategorieProveedorModel;
import khaf.d4me.cremerialucy.Models.InventoryModel;
import khaf.d4me.cremerialucy.Models.InventoryProveedorModel;
import khaf.d4me.cremerialucy.Models.StoreModel;
import khaf.d4me.cremerialucy.R;

public class RecyclerViewAdapter_Inventory extends RecyclerView.Adapter<RecyclerViewAdapter_Inventory.ViewHolder> implements Filterable {

    // Initialize variable
    ArrayList<InventoryProveedorModel> arrayList;
    RecyclerViewAdapter_Inventory.RecyclerViewItemCheckListener recyclerViewItemCheckListener;

    private ArrayList<InventoryProveedorModel> arrayListFilter;
    private RecyclerViewAdapter_Inventory.CustomFilter mFilter;
    int selectedPosition = -1;
    private final RecyclerViewAdapter_Inventory.ClickListener clickListener;
    private final RecyclerViewAdapter_Inventory.RecyclerViewItemCheckListener itemCheckListener;
    Context mContext;
    InventoryProveedorModel productoSeleccionado;
    // Create constructor
    public RecyclerViewAdapter_Inventory(ArrayList<InventoryProveedorModel> arrayList,
                                         RecyclerViewAdapter_Inventory.RecyclerViewItemCheckListener listener,
                                         RecyclerViewAdapter_Inventory.ClickListener clickListener,
                                         Context cont)
    {
        this.clickListener = clickListener;
        this.itemCheckListener = listener;
        this.arrayListFilter = new ArrayList<InventoryProveedorModel>();
        this.arrayListFilter.addAll(arrayList);
        this.mFilter = new RecyclerViewAdapter_Inventory.CustomFilter(RecyclerViewAdapter_Inventory.this);
        this.arrayList = arrayList;
        this.recyclerViewItemCheckListener = listener;
        this.mContext = cont;
    }

    @NonNull
    @Override
    public ViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item_inventory, parent, false);
        // Pass holder view
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        int pos = position;
        InventoryProveedorModel recyclerData =  arrayList.get(arrayList.indexOf(arrayListFilter.get(holder.getAbsoluteAdapterPosition())));
        // Set text on radio button
        holder.lblProducto.setText(recyclerData.getProducto());
        // Checked selected radio button
        holder.radioButton.setChecked(selectedPosition == recyclerData.getPosicionTemp());
        //Toast.makeText(mContext, "Has: "+selectedPosition, Toast.LENGTH_SHORT).show();
        holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // When checked
                    // update selected position
                    try {
                        selectedPosition = arrayList.indexOf(arrayListFilter.get(holder.getAbsoluteAdapterPosition()));
                        // Call listener
                        if (arrayListFilter.size() > selectedPosition)
                            recyclerViewItemCheckListener.changeOnItem(selectedPosition);
                        //productoSeleccionado = arrayListFilter.get(selectedPosition);
                        //Toast.makeText(mContext, "Has seleccionado el item con la posicion: "+selectedPosition+" y id: "+productoSeleccionado.getIdProducto(), Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        System.out.println(e);
                    }
                }
            }

        });
    }

    @Override public long getItemId(int position)
    {
        // pass position
        return position;
    }
    @Override
    public Filter getFilter() {
        return mFilter;
    }
    @Override public int getItemViewType(int position)
    {
        // pass position
        return position;
    }

    @Override public int getItemCount()
    {
        // pass total list size
        return arrayListFilter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
        // Initialize variable
        TextView lblProducto;
        RadioButton radioButton;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            // Assign variable
            lblProducto = itemView.findViewById(R.id.lblIdProducto);
            radioButton = itemView.findViewById(R.id.rbProducto);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int position =  arrayList.indexOf(arrayListFilter.get(getAbsoluteAdapterPosition()));
            if (position >= 0) {
                itemCheckListener.changeOnItem(position);
            }
        }

        @Override
        public void onClick(View v) {
            int position =  arrayList.indexOf(arrayListFilter.get(getAbsoluteAdapterPosition()));
            if (position >= 0) {
                clickListener.onItemClick(position, v);
            }
        }
    }
    public interface ClickListener {
        void onItemClick(int position, View v);
    }
    public interface RecyclerViewItemCheckListener {
        void changeOnItem(Integer title);
    }

    //Para filtros
    public class CustomFilter extends Filter {
        private RecyclerViewAdapter_Inventory listAdapterProductos;
        private CustomFilter(RecyclerViewAdapter_Inventory listAdapter) {
            super();
            this.listAdapterProductos = listAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            arrayListFilter = new ArrayList<InventoryProveedorModel>();
            arrayListFilter.clear();

            final FilterResults results = new FilterResults();
            if (constraint.length() == 0) {
                arrayListFilter.addAll(arrayList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();
                for(int i = 0; i < arrayList.size(); i++){
                    InventoryProveedorModel producto = arrayList.get(i);
                    if (producto.getProducto().toLowerCase().startsWith(filterPattern) ||
                            producto.getCodigo().toLowerCase().contains(filterPattern) ||
                            String.valueOf(producto.getPiezas()).startsWith(filterPattern)) {

                        arrayListFilter.add(producto);
                    }
                }
            }
            results.values = arrayListFilter;
            results.count = arrayListFilter.size();
            return results;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            synchronized(this){
                listAdapterProductos.notifyDataSetChanged();
            }
        }
    }
}