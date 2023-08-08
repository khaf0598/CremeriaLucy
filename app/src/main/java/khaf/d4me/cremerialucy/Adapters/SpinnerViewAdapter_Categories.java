package khaf.d4me.cremerialucy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import khaf.d4me.cremerialucy.Models.CategorieModel;
import khaf.d4me.cremerialucy.R;

public class SpinnerViewAdapter_Categories extends ArrayAdapter<CategorieModel> {

    int CategoriaMos;
    public SpinnerViewAdapter_Categories(Context context, ArrayList<CategorieModel> algorithmList, int categoriaMostrar) {
        super(context, 0, algorithmList);
        CategoriaMos = categoriaMostrar;
    }

    @Nullable
    @Override
    public CategorieModel getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        // It is used to set our custom view.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_view_item_category, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.lblCategoriaSpinner);
        CategorieModel currentItem = getItem(position);

        // It is used the name to the TextView when the
        // current item is not null.
        if (currentItem != null) {
            textViewName.setText(currentItem.getCategoria());
        }
        return convertView;
    }
}
