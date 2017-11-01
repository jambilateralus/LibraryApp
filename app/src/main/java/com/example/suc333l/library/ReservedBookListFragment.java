package com.example.suc333l.library;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.suc333l.library.adapters.RequestBookAdapter;
import com.example.suc333l.library.models.RequestedBookResponse;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReservedBookListFragment extends Fragment {

    //For ui
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RequestBookAdapter adapter;
    private RequestedBookResponse requestedBookList;


    public ReservedBookListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reserved_book_list, container, false);
    }

}
