package com.example.suc333l.library;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.suc333l.library.interfaces.LibraryAPi;
import com.google.gson.JsonArray;

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

    private LibraryAPi service;
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

        service = retrofit.create(LibraryAPi.class);
        attemptToFetchCategoryList();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_books, container, false);
    }

    private void attemptToFetchCategoryList() {
        // Extract token from Shared preferences.
        SharedPreferences prefs = getActivity().getSharedPreferences(getString(R.string.login_data), MODE_PRIVATE);
        String token = prefs.getString("token","");


        categoryListResponseCall = service.getCategoryList(token);
        categoryListResponseCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                int statusCode = response.code();


                Toast.makeText(getContext(), ""+statusCode+""+""+response.body(), Toast.LENGTH_SHORT).show();
                Log.d("Attempt to fetch ", "onResponse: "+response.raw());

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });
    }

    @Override
    public void onPause() {
        categoryListResponseCall.cancel();
        super.onPause();
    }


}
