package com.example.suc333l.library;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.suc333l.library.adapters.CategoryAdapter;
import com.example.suc333l.library.interfaces.LibraryApi;
import com.example.suc333l.library.models.Category;
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
public class BooksFragment extends Fragment {

    //For ui
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CategoryAdapter adapter;
    private List<Category> category_list;

    private LibraryApi service;
    private Call<JsonArray> categoryListResponseCall;

    public BooksFragment() {
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


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_books, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Set recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        category_list = new ArrayList<>();
        attemptToFetchCategoryList();

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new CategoryAdapter(getActivity(), category_list);
        recyclerView.setAdapter(adapter);

        super.onViewCreated(view, savedInstanceState);
    }

    private void attemptToFetchCategoryList() {
        // Setup  progress dialog.
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        // Show progress dialog
        progressDialog.show();

        final Gson gson = new Gson();
        // Extract token from Shared preferences.
        final SharedPreferences prefs = getActivity().getSharedPreferences(getString(R.string.login_data), MODE_PRIVATE);
        String token = prefs.getString("token","");


        categoryListResponseCall = service.getCategoryList(token);
        categoryListResponseCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                progressDialog.hide();
                int statusCode = response.code();
                if (statusCode == 200) {
                    JsonArray categoryArray = response.body();
                    category_list.add(new Category(0, "All"));
                    for (int i = 0; i < categoryArray.size(); i++) {
                        Category category = gson.fromJson(categoryArray.get(i), Category.class);
                        category_list.add(category);
                    }
                }
                adapter.notifyDataSetChanged();

                Toast.makeText(getContext(), "" + statusCode, Toast.LENGTH_SHORT).show();
                Log.d("Attempt to fetch ", "onResponse: "+response.raw());

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                progressDialog.hide();
            }
        });
    }

    @Override
    public void onPause() {
        categoryListResponseCall.cancel();
        super.onPause();
    }


}
