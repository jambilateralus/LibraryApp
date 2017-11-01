package com.example.suc333l.library;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.suc333l.library.adapters.CategoryListAdapter;
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
    private CategoryListAdapter adapter;
    private List<Category> category_list;

    //For retrofit
    private LibraryApi service;
    private Call<JsonArray> categoryListResponseCall;

    public BooksFragment() {
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
        attemptToFetchCategoryList();


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_books, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Set recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        category_list = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CategoryListAdapter(getActivity(), category_list, this);
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
        String token = prefs.getString("token", "");

        categoryListResponseCall = service.getCategoryList(token);
        categoryListResponseCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                progressDialog.hide();
                int statusCode = response.code();
                if (statusCode == 200) {
                    JsonArray categoryArray = response.body();
                    // category_list.add(new Category(0, "All"));
                    for (int i = 0; i < categoryArray.size(); i++) {
                        Category category = gson.fromJson(categoryArray.get(i), Category.class);
                        category_list.add(category);
                    }
                }
                adapter.notifyDataSetChanged();

                // Toast.makeText(getContext(), "" + statusCode, Toast.LENGTH_SHORT).show();
                Log.d("Attempt to fetch ", "onResponse: " + response.raw());

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                progressDialog.hide();
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = new SearchView(((MainActivity) getContext()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Open book List fragment
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack(null);
                Fragment bookListFragment = new BookListFragment();

                // Add query text to bundle
                Bundle bundle = new Bundle();
                bundle.putString("searchText", String.valueOf(searchView.getQuery()));
                bookListFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment, bookListFragment);
                fragmentTransaction.commit();
                // Toast.makeText(getContext(), "" + searchView.getQuery(), Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {

                                          }
                                      }
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_reserved_books) {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment, new ReservedBookListFragment());
            fragmentTransaction.commit();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        categoryListResponseCall.cancel();
        super.onPause();
    }

    public List<Category> getCategoryList() {
        return this.category_list;
    }


}
