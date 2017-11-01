package layout;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.suc333l.library.R;
import com.example.suc333l.library.RequestBookFragment;
import com.example.suc333l.library.interfaces.LibraryApi;
import com.example.suc333l.library.models.BookRequest;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Request_New_Fragment extends DialogFragment {
    // for ui
    private Button requestButton;
    private Button cancelButton;
    private EditText bookTitle;
    private EditText bookAuthor;
    private EditText bookPublisher;

    //For retrofit
    private LibraryApi service;
    private Call<JsonElement> requestBookResponseCall;


    public Request_New_Fragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_request__new_, new LinearLayout(getActivity()), false);


        // Setup retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(LibraryApi.class);


        // Build dialog
        Dialog builder = new Dialog(getActivity());
        builder.setTitle("Request New Book");

        // Retrieve layout elements
        requestButton = (Button) view.findViewById(R.id.action_proceed_request);
        cancelButton = (Button) view.findViewById(R.id.action_cancel_request);
        bookTitle = (EditText) view.findViewById(R.id.edittext_requestbook_title);
        bookAuthor = (EditText) view.findViewById(R.id.edittext_requestbook_author);
        bookPublisher = (EditText) view.findViewById(R.id.edittext_requestbook_publisher);


        // Button on click listener
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Attempt to request book
                attemptToRequestBook();


            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog box
                dismiss();
            }
        });
        builder.setContentView(view);


        return builder;
        //return super.onCreateDialog(savedInstanceState);
    }

    private void attemptToRequestBook() {
        // Setup  progress dialog.
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Requesting..");
        // Show progress dialog
        progressDialog.show();


        // Prepare Book Request Object
        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle(String.valueOf(bookTitle.getText()));
        bookRequest.setAuthor(String.valueOf(bookAuthor.getText()));
        bookRequest.setPublisher(String.valueOf(bookPublisher.getText()));

        // Extract token from Shared preferences.
        final SharedPreferences prefs = getActivity().getSharedPreferences(getString(R.string.login_data), MODE_PRIVATE);
        String token = prefs.getString("token", "");

        requestBookResponseCall = service.requestNewBook(token, bookRequest);
        requestBookResponseCall.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                dismiss();

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new RequestBookFragment());
                fragmentTransaction.commit();

                progressDialog.hide();


                Toast.makeText(getContext(), "" + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        });
    }

}
