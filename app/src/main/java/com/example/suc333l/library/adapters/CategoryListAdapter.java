package com.example.suc333l.library.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.suc333l.library.BookListFragment;
import com.example.suc333l.library.BooksFragment;
import com.example.suc333l.library.R;
import com.example.suc333l.library.models.Category;

import java.util.List;

/**
 * Created by suc333l on 10/26/17.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    private Context context;
    private List<Category> categories;
    private BooksFragment booksFragment;

    public CategoryListAdapter(Context context, List<Category> categories, BooksFragment booksFragment) {
        this.context = context;
        this.categories = categories;
        this.booksFragment = booksFragment;
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

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView categoryTitle;

        ViewHolder(View itemView) {
            super(itemView);
            categoryTitle = (TextView) itemView.findViewById(R.id.category_title);

            // On category card click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  Toast.makeText(context, "You clicked on item " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    // Open Book list fragment for clicked category.
                    int categoryId = booksFragment.getCategoryList().get(getAdapterPosition()).getPk();
                    startBookListFragment(context, categoryId);
                }
            });
        }

        private void startBookListFragment(Context context, int categoryId) {
            AppCompatActivity activity = (AppCompatActivity) context;
            FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.addToBackStack(null);
            Fragment bookListFragment = new BookListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("categoryId", categoryId);
            bookListFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment, bookListFragment);
            fragmentTransaction.commit();
        }
    }
}
