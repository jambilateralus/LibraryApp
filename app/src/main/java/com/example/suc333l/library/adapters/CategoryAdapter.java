package com.example.suc333l.library.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.suc333l.library.R;
import com.example.suc333l.library.models.Category;

import java.util.List;

/**
 * Created by suc333l on 10/26/17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_category, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.categoryTitle.setText(categories.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView categoryTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            categoryTitle = (TextView) itemView.findViewById(R.id.category_title);
        }
    }
}
