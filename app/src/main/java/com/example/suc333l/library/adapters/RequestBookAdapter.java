package com.example.suc333l.library.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.suc333l.library.R;
import com.example.suc333l.library.models.RequestedBook;

import java.util.List;

/**
 * Created by suc333l on 10/31/17.
 */

public class RequestBookAdapter extends RecyclerView.Adapter<RequestBookAdapter.ViewHolder> {

    private Context context;
    private List<RequestedBook> requestedBooks;

    public RequestBookAdapter(Context context, List<RequestedBook> requestedBooks) {
        this.context = context;
        this.requestedBooks = requestedBooks;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_request_book, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Collect data
        RequestedBook requestedBook = requestedBooks.get(position);

        //Call ui component methods.
        holder.bookTitle.setText(requestedBook.getTitle());
        holder.authorName.setText("Author: " + requestedBook.getAuthor());
        holder.requestDate.setText(requestedBook.getRequested_date());
        holder.status.setText("Pending");

    }

    @Override
    public int getItemCount() {
        return requestedBooks.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        // Ui components
        TextView bookTitle;
        TextView authorName;
        TextView requestDate;
        TextView status;


        public ViewHolder(View itemView) {
            super(itemView);

            // Initialize Ui components.
            bookTitle = (TextView) itemView.findViewById(R.id.request_book_title);
            authorName = (TextView) itemView.findViewById(R.id.request_book_author);
            requestDate = (TextView) itemView.findViewById(R.id.request_date);
            status = (TextView) itemView.findViewById(R.id.request_book_publisher);
        }
    }
}
