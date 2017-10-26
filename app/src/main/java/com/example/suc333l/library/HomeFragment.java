package com.example.suc333l.library;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suc333l.library.interfaces.LibraryApi;
import com.example.suc333l.library.models.MemberInfo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    // Retrofit
    private LibraryApi service;
    private Call<JsonArray> memberInfoResponseCall;

    //Ui components
    TextView memberName;
    TextView registeredYear;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Setup retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(LibraryApi.class);
        attemptToFetchMemberInfo();

        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_home, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        memberName = (TextView) view.findViewById(R.id.member_name);
        registeredYear = (TextView) view.findViewById(R.id.registered_year);
        memberName.setText("");
        registeredYear.setText("");
    }

    private void attemptToFetchMemberInfo() {
        final Gson gson = new Gson();

        // Extract token from Shared preferences.
        SharedPreferences prefs = getActivity().getSharedPreferences(getString(R.string.login_data), MODE_PRIVATE);
        String token = prefs.getString("token", "");

        memberInfoResponseCall = service.getMemberInfo(token);
        memberInfoResponseCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                int statusCode = response.code();

                //If response is ok
                if (statusCode == 200) {
                    //Extract user info from response.
                    JsonArray memberInfoArray = response.body();
                    MemberInfo memberInfo = gson.fromJson(memberInfoArray.get(0), MemberInfo.class);
                    String member_name = memberInfo.getFirst_name() + " " + memberInfo.getLast_name();
                    String registered_year = memberInfo.getRegistered_year();

                    //Set text to ui
                    memberName.setText(member_name);
                    registeredYear.setText(registered_year);

                    Toast.makeText(getContext(), "" + memberInfo.getFirst_name(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "" + statusCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });
    }

    private void attemptToFetchBurrowedBooks() {

    }

    private void attemptToFetchReservedBooks() {

    }

    private void attemptToFetchRequestedBooks() {

    }

    @Override
    public void onPause() {
        memberInfoResponseCall.cancel();
        super.onPause();
    }
}