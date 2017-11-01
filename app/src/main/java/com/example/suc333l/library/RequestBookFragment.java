package com.example.suc333l.library;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.suc333l.library.adapters.RequestBookAdapter;
import com.example.suc333l.library.interfaces.LibraryApi;
import com.example.suc333l.library.models.RequestedBook;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

import layout.Request_New_Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestBookFragment extends Fragment {

    //For ui
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RequestBookAdapter adapter;
    private List<RequestedBook> requestedBookList;

    //For retrofit
    private LibraryApi service;
    private Call<JsonArray> requestedBookResponseCall;

    public RequestBookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Enable option menu
        setHasOptionsMenu(true);

        // Setup retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(LibraryApi.class);

        attemptToFetchRequestedBookList();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request_book, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Set recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_requested_book);
        requestedBookList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RequestBookAdapter(getActivity(), requestedBookList);
        recyclerView.setAdapter(adapter);


        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.request_book, menu);
        MenuItem item = menu.findItem(R.id.action_request_new_book);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_request_new_book) {
            // Open request new book dialog fragment
            DialogFragment requestDialog = new Request_New_Fragment();
            requestDialog.show(getFragmentManager(), "dialog");
        }
        return super.onOptionsItemSelected(item);
    }

    public void attemptToFetchRequestedBookList() {
        final Gson gson = new Gson();

        // Setup  progress dialog.
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");

        // Show progress dialog
        progressDialog.show();

        // Extract token from Shared preferences.
        SharedPreferences prefs = getActivity().getSharedPreferences(getString(R.string.login_data), MODE_PRIVATE);
        String token = prefs.getString("token", "");

        requestedBookResponseCall = service.getRequestedBookList(token);
        requestedBookResponseCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                progressDialog.hide();

                JsonArray bookArray = response.body();
                for (int i = 0; i < bookArray.size(); i++) {
                    RequestedBook book = gson.fromJson(bookArray.get(i), RequestedBook.class);
                    requestedBookList.add(book);
                }
                adapter.notifyDataSetChanged();
                // Toast.makeText(getContext(), "" + bookArray.get(1).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });

    }
}
