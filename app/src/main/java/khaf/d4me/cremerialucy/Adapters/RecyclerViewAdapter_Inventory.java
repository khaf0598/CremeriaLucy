package khaf.d4me.cremerialucy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import khaf.d4me.cremerialucy.Interfaces.ItemClickListener;
import khaf.d4me.cremerialucy.Models.CategorieModel;
import khaf.d4me.cremerialucy.Models.InventoryModel;
import khaf.d4me.cremerialucy.R;

public class RecyclerViewAdapter_Inventory extends RecyclerView.Adapter<RecyclerViewAdapter_Inventory.ViewHolder> {

    // Initialize variable
    ArrayList<InventoryModel> arrayList;
    RecyclerViewAdapter_Inventory.RecyclerViewItemCheckListener recyclerViewItemCheckListener;
    int selectedPosition = -1;
    private final RecyclerViewAdapter_Inventory.ClickListener clickListener;

    // Create constructor
    public RecyclerViewAdapter_Inventory(ArrayList<InventoryModel> arrayList,
                                         RecyclerViewAdapter_Inventory.RecyclerViewItemCheckListener listener,
                                         RecyclerViewAdapter_Inventory.ClickListener clickListener)
    {
        this.clickListener = clickListener;
        this.arrayList = arrayList;
        this.recyclerViewItemCheckListener = listener;
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
        InventoryModel recyclerData = arrayList.get(position);

        // Set text on radio button
        holder.lblProducto.setText(recyclerData.getProducto());
        // Checked selected radio button
        holder.radioButton.setChecked(position == selectedPosition);
        holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // When checked
                    // update selected position
                    selectedPosition
                            = holder.getAdapterPosition();
                    // Call listener
                    recyclerViewItemCheckListener.changeOnItem(selectedPosition);
                }
            }
        });
    }

    @Override public long getItemId(int position)
    {
        // pass position
        return position;
    }
    @Override public int getItemViewType(int position)
    {
        // pass position
        return position;
    }

    @Override public int getItemCount()
    {
        // pass total list size
        return arrayList.size();
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
        }

        @Override
        public void onClick(View v) {
            int position =  getAbsoluteAdapterPosition();
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
}