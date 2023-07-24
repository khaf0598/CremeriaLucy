package khaf.d4me.cremerialucy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import khaf.d4me.cremerialucy.Models.PermissionsModel;
import khaf.d4me.cremerialucy.R;

public class RecyclerViewAdapter_Permissions extends RecyclerView.Adapter<RecyclerViewAdapter_Permissions.RecyclerViewHolder> {

    private ArrayList<PermissionsModel> courseDataArrayList;
    private Context mcontext;

    public RecyclerViewAdapter_Permissions(ArrayList<PermissionsModel> recyclerDataArrayList, Context mcontext) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter_Permissions.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_settings, parent, false);

        return new RecyclerViewAdapter_Permissions.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter_Permissions.RecyclerViewHolder holder, int position) {
        // Set the data to textview and imageview.
        PermissionsModel recyclerData = courseDataArrayList.get(position);
        holder.ProveedorNombre.setText(recyclerData.getPermiso());
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
    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        private TextView ProveedorNombre;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            ProveedorNombre = itemView.findViewById(R.id.lblDatoSettings);
        }
    }
}