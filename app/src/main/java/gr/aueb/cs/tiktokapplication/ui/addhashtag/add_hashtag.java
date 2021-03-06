package gr.aueb.cs.tiktokapplication.ui.addhashtag;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import gr.aueb.cs.tiktokapplication.dao.PublisherDAO;
import gr.aueb.cs.tiktokapplication.R;
import gr.aueb.cs.tiktokapplication.appnode.Publisher;

public class add_hashtag extends Fragment {

    private EditText hashtagField;
    private Button addHashtagButton;
    private ProgressDialog p;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_hashtag, container, false);

        addHashtagButton = root.findViewById(R.id.add_hashtag_button);
        addHashtagButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hashtagField = root.findViewById(R.id.add_hashtag_field);

                if (!hashtagField.getText().toString().trim().equals("")) {
                    String text = hashtagField.getText().toString().trim();
                    AsyncTaskAddHashtag add = new AsyncTaskAddHashtag();
                    add.execute(text);
                } else {
                    Toast.makeText(root.getContext(), "Please, give a hashtag!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return root;
    }

    private class AsyncTaskAddHashtag extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(add_hashtag.this.getContext());
            p.setMessage("Please wait until we find a server..");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            // Add the hashtag to the channel
            Publisher p = PublisherDAO.getPublisher();      // Get publisher's instance
            p.addHashTag(strings[0]);
            return "";
        }

        @Override
        protected void onPostExecute(String hashtag) {
            super.onPostExecute(hashtag);
            p.hide();
            hashtagField.setText("");
        }

    } // AsyncTaskAddHashtag

}