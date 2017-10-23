package com.example.suc333l.library.services;

import com.example.suc333l.library.R;
import com.example.suc333l.library.interfaces.LibraryAPi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by suc333l on 10/23/17.
 */

public class LibraryApiService {
    private LibraryAPi service;
    private Retrofit retrofit;

    public LibraryApiService(){
        // Setup retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(String.valueOf((R.string.base_url)))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(LibraryAPi.class);
    }
}
