package com.example.suc333l.library.interfaces;

import com.example.suc333l.library.models.TokenRequest;
import com.example.suc333l.library.models.TokenResponse;
import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by suc333l on 10/14/17.
 */

public interface LibraryApi {

    @POST("api-token-auth/")
    Call<TokenResponse> getToken(@Body TokenRequest tokenRequest);

    @GET("member-info/")
    Call<JsonArray> getMemberInfo(@Header("Authorization") String token);

    @GET("burrowed_book/")
    Call<JsonArray> getBurrowedBooks(@Header("Authorization") String token);

    @GET("category/")
    Call<JsonArray> getCategoryList(@Header("Authorization") String token);


    @GET("category/{category_id}/book_list")
    Call<JsonArray> getBooksOfCategory(@Header("Authorization") String token, @Path("category_id") int categoryId);

}
