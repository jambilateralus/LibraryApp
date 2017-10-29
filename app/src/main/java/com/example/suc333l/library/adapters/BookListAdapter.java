package com.example.suc333l.library.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suc333l.library.R;
import com.example.suc333l.library.interfaces.LibraryApi;
import com.example.suc333l.library.models.Author;
import com.example.suc333l.library.models.Book;
import com.example.suc333l.library.models.ReserveBookResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

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


        // collect data
        final Book book = books.get(position);
        String authors = "Author: ";
        for (Author author : book.getAuthors()) {
            authors = authors + "" + author.getName() + ", ";
        }
        final String publisher = "Publisher: " + book.getPublisher().getName();
        final String booksAvailable = "" + book.getAvailable_number_of_copies();


        // Call UI component methods.
        holder.bookTitle.setText(book.getTitle());
        holder.authorName.setText(authors);
        holder.publisherName.setText(publisher);
        holder.bookCount.setText(booksAvailable);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        //For retrofit
        private LibraryApi service;
        private Call<ReserveBookResponse> reserveBookResponseCall;

        // Ui components
        TextView bookTitle;
        TextView authorName;
        TextView publisherName;
        TextView bookCount;

        ViewHolder(View itemView) {
            super(itemView);

            // Setup retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(context.getString(R.string.base_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(LibraryApi.class);

            // Initialize Ui components.
            bookTitle = (TextView) itemView.findViewById(R.id.book_title);
            authorName = (TextView) itemView.findViewById(R.id.book_author);
            publisherName = (TextView) itemView.findViewById(R.id.book_publisher);
            bookCount = (TextView) itemView.findViewById(R.id.book_count);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Long press to make reservation.", Toast.LENGTH_SHORT).show();
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //open dialog
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(context);
                    builder.setTitle("Reserve Book")
                            .setMessage("Are you sure you want to reserve this book?")
                            .setIcon(null)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    attemptToReserveBook(books.get(getAdapterPosition()).getPk());
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .show();
                    return false;
                }
            });

        }

        private void attemptToReserveBook(int book_pk) {
            // Extract token from Shared preferences.
            SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.login_data), MODE_PRIVATE);
            String token = prefs.getString("token", "");

            reserveBookResponseCall = service.reserveBook(token, book_pk);
            reserveBookResponseCall.enqueue(new Callback<ReserveBookResponse>() {
                @Override
                public void onResponse(Call<ReserveBookResponse> call, Response<ReserveBookResponse> response) {
                    int statusCode = response.code();
                    if (statusCode == 200) {
                        ReserveBookResponse reserveResponse = response.body();
                        Toast.makeText(context, reserveResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<ReserveBookResponse> call, Throwable t) {

                }
            });

        }
    }
}
