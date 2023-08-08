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

public class RecyclerViewAdapter_Categories_General extends RecyclerView.Adapter<RecyclerViewAdapter_Categories_General.ViewHolder> implements Filterable {
    private ArrayList<CategorieModel> courseDataArrayList;
    RecyclerViewItemClickListener recyclerViewItemClickListener;

    private ArrayList<CategorieModel> courseDataArrayListFilter;
    private RecyclerViewAdapter_Categories_General.CustomFilter mFilter;

    public RecyclerViewAdapter_Categories_General(ArrayList<CategorieModel> recyclerDataArrayList, RecyclerViewItemClickListener listener) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.courseDataArrayListFilter = new ArrayList<CategorieModel>();
        this.courseDataArrayListFilter.addAll(recyclerDataArrayList);
        this.mFilter = new RecyclerViewAdapter_Categories_General.CustomFilter(RecyclerViewAdapter_Categories_General.this);
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
        CategorieModel recyclerData = courseDataArrayListFilter.get(i);
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
            if (position >= 0) {
                recyclerViewItemClickListener.clickOnItem(courseDataArrayList.get(position).getCategoria(), position);
            }
        }
    }

    public interface RecyclerViewItemClickListener {
        void clickOnItem(String title, int posi);
    }

    //Para filtros
    public class CustomFilter extends Filter {
        private RecyclerViewAdapter_Categories_General listAdapterCategoriasGen;

        private CustomFilter(RecyclerViewAdapter_Categories_General listAdapter) {
            super();
            this.listAdapterCategoriasGen = listAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            courseDataArrayListFilter = new ArrayList<CategorieModel>();
            courseDataArrayListFilter.clear();
            final FilterResults results = new FilterResults();
            if (constraint.length() == 0) {
                courseDataArrayListFilter.addAll(courseDataArrayList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();
                for(int i = 0; i < courseDataArrayList.size(); i++){
                    CategorieModel categoria = courseDataArrayList.get(i);
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
            listAdapterCategoriasGen.notifyDataSetChanged();
        }
    }
}