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

import com.example.suc333l.library.adapters.ReservedBookAdapter;
import com.example.suc333l.library.interfaces.LibraryApi;
import com.example.suc333l.library.models.ReservedBook;
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
public class ReservedBookListFragment extends Fragment {

    //For ui
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ReservedBookAdapter adapter;
    private List<ReservedBook> reservedBookList;

    //For retrofit
    private LibraryApi service;
    private Call<JsonArray> reservedBookResponseCall;

    public ReservedBookListFragment() {
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

        attemptToGetReservedBooks();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reserved_book_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Set recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_reserved_booklist);
        reservedBookList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ReservedBookAdapter(getActivity(), reservedBookList);
        recyclerView.setAdapter(adapter);

        super.onViewCreated(view, savedInstanceState);
    }

    private void attemptToGetReservedBooks() {
        final Gson gson = new Gson();

        // Setup  progress dialog.
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");

        // Show progress dialog
        progressDialog.show();

        // Extract token from Shared preferences.
        SharedPreferences prefs = getActivity().getSharedPreferences(getString(R.string.login_data), MODE_PRIVATE);
        String token = prefs.getString("token", "");

        reservedBookResponseCall = service.getReservedBookList(token);
        reservedBookResponseCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                progressDialog.hide();

                JsonArray bookArray = response.body();
                for (int i = 0; i < bookArray.size(); i++) {
                    ReservedBook book = gson.fromJson(bookArray.get(i), ReservedBook.class);
                    reservedBookList.add(book);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                progressDialog.hide();
            }
        });
    }

}
