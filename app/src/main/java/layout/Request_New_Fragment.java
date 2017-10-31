package layout;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.suc333l.library.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Request_New_Fragment extends DialogFragment {


    public Request_New_Fragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // return super.onCreateDialog(savedInstanceState);
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_request__new_, new LinearLayout(getActivity()), false);

        // Retrieve layout elements
        TextView title = (TextView) view.findViewById(R.id.request_new_book);

        // Set values
        title.setText("Not perfect yet");

        // Build dialog
        Dialog builder = new Dialog(getActivity());
        //builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);
        return builder;
    }
}
