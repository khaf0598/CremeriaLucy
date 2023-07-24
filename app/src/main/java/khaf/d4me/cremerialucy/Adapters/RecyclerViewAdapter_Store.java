package khaf.d4me.cremerialucy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import khaf.d4me.cremerialucy.Models.StoreModel;
import khaf.d4me.cremerialucy.R;

public class RecyclerViewAdapter_Store extends RecyclerView.Adapter<RecyclerViewAdapter_Store.RecyclerViewHolder> {
    private final RecyclerViewAdapter_Store.ClickListener clickListener;

    private ArrayList<StoreModel> courseDataArrayList;
    private Context mcontext;

    public RecyclerViewAdapter_Store(ArrayList<StoreModel> recyclerDataArrayList, Context mcontext, RecyclerViewAdapter_Store.ClickListener clickListener) {
        this.courseDataArrayList = recyclerDataArrayList;
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
        StoreModel recyclerData = courseDataArrayList.get(position);
        holder.ProveedorNombre.setText(recyclerData.getTitle());
    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return courseDataArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
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
            int position =  getAbsoluteAdapterPosition();
            if (position >= 0) {
                clickListener.onItemClick(position, v);
            }
        }
    }
    public interface ClickListener {
        void onItemClick(int position, View v);
    }
}