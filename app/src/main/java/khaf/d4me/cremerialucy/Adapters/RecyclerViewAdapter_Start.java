package khaf.d4me.cremerialucy.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import khaf.d4me.cremerialucy.Models.StartModel;
import khaf.d4me.cremerialucy.R;
import khaf.d4me.cremerialucy.StartActivity;
import khaf.d4me.cremerialucy.StoreActivity;

public class RecyclerViewAdapter_Start extends RecyclerView.Adapter<RecyclerViewAdapter_Start.RecyclerViewHolder> {
    private final ClickListener clickListener;

    private ArrayList<StartModel> courseDataArrayList;
    private Context mcontext;

    public RecyclerViewAdapter_Start(ArrayList<StartModel> recyclerDataArrayList, Context mcontext, ClickListener clickListener) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.clickListener = clickListener;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_start, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Set the data to textview and imageview.
        int pos = position;
        StartModel recyclerData = courseDataArrayList.get(position);
        holder.courseTV.setText(recyclerData.getTitle());
        holder.courseTV.setTextSize(courseDataArrayList.get(position).getTamanoTexto());
        holder.courseIV.setImageResource(recyclerData.getImgid());
        holder.courseIV.getLayoutParams().height = courseDataArrayList.get(position).getTamano();
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

        private TextView courseTV;
        private ImageView courseIV;
        private CardView menu;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            courseTV = itemView.findViewById(R.id.idTVCourse);
            courseIV = itemView.findViewById(R.id.idIVcourseIV);
            menu = itemView.findViewById(R.id.idCardMenu);

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