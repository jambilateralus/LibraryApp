package com.example.suc333l.library;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.suc333l.library.interfaces.LibraryAPi;
import com.example.suc333l.library.models.TokenRequest;
import com.example.suc333l.library.models.TokenResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView mMemberIdView;
    private EditText mPasswordView;
    private LibraryAPi service;
    Call<TokenResponse> tokenResponseCall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_login);

        // Start main activity if already logged in.
        SharedPreferences prefs = getSharedPreferences(getString(R.string.login_data), MODE_PRIVATE);
        boolean hasLoggedIn = prefs.getBoolean("hasLoggedIn", false);
        if (hasLoggedIn) {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
        }


        // Load UI components
        mMemberIdView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);


        // Login Button On click listener
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        // Setup retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(LibraryAPi.class);
    }


    private void attemptLogin() {
        // Setup  progress dialog.
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Loading");

        // Create TokenRequest from login form data.
        final TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setUserName(mMemberIdView.getText().toString());
        tokenRequest.setPassword(mPasswordView.getText().toString());

        // Validate form
        if (isBlank(tokenRequest.getUserName())) {
            mMemberIdView.setError("Member Id is blank.");
            mMemberIdView.showDropDown();
            return;
        } else if (isLessThanFourChar(tokenRequest.getUserName())) {
            mMemberIdView.setError("Member Id is too short.");
            mMemberIdView.showDropDown();
            return;
        } else if (isBlank(tokenRequest.getPassword())) {
            mPasswordView.setError("Password is blank.");
            return;
        } else if (isLessThanFourChar(tokenRequest.getPassword())) {
            mPasswordView.setError("Password is too short.");
        }

        // Show progress dialog
        progressDialog.show();


        tokenResponseCall = service.getToken(tokenRequest);
        tokenResponseCall.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                progressDialog.hide();
                int statusCode = response.code();
                if (statusCode == 200) {
                    TokenResponse tokenResponse = response.body();

                    // Save token using shared preferences.
                    SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.login_data),
                            MODE_PRIVATE).edit();
                    editor.putString("token", getTokenHeader(tokenRequest.getUserName(),tokenRequest.getPassword()));
                    //editor.putString("token", tokenResponse.getToken());
                    editor.putBoolean("hasLoggedIn", true);
                    editor.commit();

                    Toast.makeText(LoginActivity.this, tokenResponse.getToken(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                } else if (statusCode == 400 ) {
                    Toast.makeText(LoginActivity.this, "Invalid credentials.", Toast.LENGTH_SHORT).show();
                    mPasswordView.setText("");
                } else if (statusCode == 502) {
                    Toast.makeText(LoginActivity.this, "Server is down.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                progressDialog.hide();
                Toast.makeText(LoginActivity.this, "No internet connection.", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private boolean isBlank(String string) {
        return string.length() == 0;
    }

    private boolean isLessThanFourChar(String string) {
        return string.length() <= 4;
    }

    @Override
    protected void onPause() {
        this.finish();
        super.onPause();
    }

    private String getTokenHeader(String username, String password){
        String credentials = username + ":" + password;
        String token = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        return token;
    }
}
