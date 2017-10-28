package com.example.suc333l.library.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.suc333l.library.R;
import com.example.suc333l.library.models.Book;

import java.util.List;

/**
 * Created by suc333l on 10/27/17.
 */

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {

    private Context context;
    private List<Book> books;


    public BookListAdapter(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
    }

    @Override
    public BookListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_book, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookListAdapter.ViewHolder holder, int position) {
        // Call UI component methods.
        holder.bookTitle.setText(books.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // Ui components
        TextView bookTitle;

        ViewHolder(View itemView) {
            super(itemView);
            // Initialize Ui components.
            bookTitle = (TextView) itemView.findViewById(R.id.book_title);

        }
    }
}
