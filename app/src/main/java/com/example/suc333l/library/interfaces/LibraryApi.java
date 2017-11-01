package com.example.suc333l.library.interfaces;

import com.example.suc333l.library.models.BookRequest;
import com.example.suc333l.library.models.ReserveBookResponse;
import com.example.suc333l.library.models.TokenRequest;
import com.example.suc333l.library.models.TokenResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by suc333l on 10/14/17.
 */

public interface LibraryApi {

    @POST("api-token-auth/")
    Call<TokenResponse> getToken(@Body TokenRequest tokenRequest);

    //get member information
    @GET("member-info/")
    Call<JsonArray> getMemberInfo(@Header("Authorization") String token);

    //get a list of all categories
    @GET("category/")
    Call<JsonArray> getCategoryList(@Header("Authorization") String token);

    //search book
    @GET("books/")
    Call<JsonArray> getBookList(@Header("Authorization") String token, @Query("search") String search);

    //reserve a book
    @GET("reserve_book/{book_pk}")
    Call<ReserveBookResponse> reserveBook(@Header("Authorization") String token, @Path("book_pk") int book_pk);

    //get a list of reservedBooks
    @GET("reserved_book/")
    Call<JsonArray> getReservedBookList(@Header("Authorization") String token);

    //request new book
    @POST("request_new_book/")
    Call<JsonElement> requestNewBook(@Header("Authorization") String token, @Body BookRequest bookRequest);

    //requested book list
    @GET("request_book/")
    Call<JsonArray> getRequestedBookList(@Header("Authorization") String token);

    //get books of given category
    @GET("category/{category_id}/book_list")
    Call<JsonArray> getBooksOfCategory(@Header("Authorization") String token, @Path("category_id") int categoryId);

}
