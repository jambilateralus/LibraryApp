package com.example.suc333l.library.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.suc333l.library.R;
import com.example.suc333l.library.models.ReservedBook;

import java.util.List;

/**
 * Created by suc333l on 11/1/17.
 */

public class ReservedBookAdapter extends RecyclerView.Adapter<ReservedBookAdapter.ViewHolder> {

    private Context context;
    private List<ReservedBook> reservedBooks;

    public ReservedBookAdapter(Context context, List<ReservedBook> reservedBooks) {
        this.context = context;
        this.reservedBooks = reservedBooks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_reserved_book, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Collect data
        ReservedBook reservedBook = reservedBooks.get(position);

        //Call ui component methods.
        holder.reservedBookTitle.setText(reservedBook.getBook().getTitle());
        holder.reservedBookAuthor.setText(reservedBook.getBook().getAuthors().get(0).getName());
        holder.reservedBookDate.setText(reservedBook.getReserved_date());
        holder.numberOfCopiesAvailable.setText(String.valueOf(reservedBook.getBook().getAvailable_number_of_copies()));
    }

    @Override
    public int getItemCount() {
        return reservedBooks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        // Ui components
        TextView reservedBookTitle;
        TextView reservedBookAuthor;
        TextView reservedBookDate;
        TextView numberOfCopiesAvailable;

        public ViewHolder(View itemView) {
            super(itemView);

            // Initialize Ui components.
            reservedBookTitle = (TextView) itemView.findViewById(R.id.reserved_book_title);
            reservedBookAuthor = (TextView) itemView.findViewById(R.id.reserved_book_author);
            reservedBookDate = (TextView) itemView.findViewById(R.id.reserved_date);
            numberOfCopiesAvailable = (TextView) itemView.findViewById(R.id.reserved_book_copies_available);
        }
    }
}
