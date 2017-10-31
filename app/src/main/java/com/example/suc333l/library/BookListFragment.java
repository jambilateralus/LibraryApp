package com.example.suc333l.library;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.suc333l.library.adapters.BookListAdapter;
import com.example.suc333l.library.interfaces.LibraryApi;
import com.example.suc333l.library.models.Book;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookListFragment extends Fragment {

    //For ui
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private BookListAdapter adapter;
    private List<Book> book_list;

    //For retrofit
    private LibraryApi service;
    private Call<JsonArray> bookListResponseCall;

    public BookListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Setup retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(LibraryApi.class);

        int categoryId;
        String queryText;
        Bundle bundle = this.getArguments();
        categoryId = bundle.getInt("categoryId", 0);
        queryText = bundle.getString("searchText");
        // Toast.makeText(getContext(), "" + categoryId, Toast.LENGTH_SHORT).show();

        if (categoryId != 0)
            attemptToFetchBookList(categoryId);
        else
            attemptToSearchBookList(queryText);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        // Set recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_booklist);
        book_list = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new BookListAdapter(getActivity(), book_list);
        recyclerView.setAdapter(adapter);

        super.onViewCreated(view, savedInstanceState);
    }

    private void attemptToFetchBookList(int categoryId) {
        // Setup  progress dialog.
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");

        // Show progress dialog
        progressDialog.show();

        final Gson gson = new Gson();

        // Extract token from Shared preferences.
        SharedPreferences prefs = getActivity().getSharedPreferences(getString(R.string.login_data), MODE_PRIVATE);
        String token = prefs.getString("token", "");

        bookListResponseCall = service.getBooksOfCategory(token, categoryId);
        bookListResponseCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                progressDialog.hide();
                int statusCode = response.code();
                if (statusCode == 200) {
                    JsonArray bookArray = response.body();
                    for (int i = 0; i < bookArray.size(); i++) {
                        Book book = gson.fromJson(bookArray.get(i), Book.class);
                        book_list.add(book);
                    }
                }
                // Update recycler view
                adapter.notifyDataSetChanged();
                // Toast.makeText(getContext(), "" + statusCode, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                progressDialog.hide();
            }
        });
    }


    private void attemptToSearchBookList(String searchText) {
        // Setup  progress dialog.
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Searching..");

        // Show progress dialog
        progressDialog.show();

        final Gson gson = new Gson();

        // Extract token from Shared preferences.
        final SharedPreferences prefs = getActivity().getSharedPreferences(getString(R.string.login_data), MODE_PRIVATE);
        String token = prefs.getString("token", "");

        bookListResponseCall = service.getBookList(token, searchText);
        bookListResponseCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                progressDialog.hide();
                int statusCode = response.code();
                if (statusCode == 200) {
                    JsonArray bookArray = response.body();
                    for (int i = 0; i < bookArray.size(); i++) {
                        Book book = gson.fromJson(bookArray.get(i), Book.class);
                        book_list.add(book);
                    }
                }
                // Update recycler view
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                progressDialog.hide();

            }
        });

    }


}
