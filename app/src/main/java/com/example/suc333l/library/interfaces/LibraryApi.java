package com.example.suc333l.library.interfaces;

import com.example.suc333l.library.models.TokenRequest;
import com.example.suc333l.library.models.TokenResponse;
import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by suc333l on 10/14/17.
 */

public interface LibraryApi {

    @POST("api-token-auth/")
    Call<TokenResponse> getToken(@Body TokenRequest tokenRequest);

    @GET("category/")
    Call<JsonArray> getCategoryList(@Header("Authorization") String token);

    @GET("member-info/")
    Call<JsonArray> getMemberInfo(@Header("Authorization") String token);
}
