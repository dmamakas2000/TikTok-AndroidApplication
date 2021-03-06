package gr.aueb.cs.tiktokapplication.ui.register;

import androidx.lifecycle.ViewModelProvider;
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

import java.nio.channels.AsynchronousChannelGroup;
import gr.aueb.cs.tiktokapplication.R;
import gr.aueb.cs.tiktokapplication.appnode.Consumer;
import gr.aueb.cs.tiktokapplication.appnode.Publisher;
import gr.aueb.cs.tiktokapplication.appnode.ThreadInformation;
import gr.aueb.cs.tiktokapplication.dao.ConsumerDAO;
import gr.aueb.cs.tiktokapplication.dao.PublisherDAO;
import gr.aueb.cs.tiktokapplication.ui.addhashtag.add_hashtag;

public class Register extends Fragment {

    private Button registerButton;
    private EditText hashTagToRegister;
    private ProgressDialog p;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,  @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_register, container, false);

        registerButton = root.findViewById(R.id.register_hashtag_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                hashTagToRegister = root.findViewById(R.id.register_hashtag_field);

                if (!hashTagToRegister.getText().toString().trim().equals("")) {
                    String text = hashTagToRegister.getText().toString().trim();
                    AsyncTaskRegisterHashtags task = new AsyncTaskRegisterHashtags();
                    task.execute(text);
                } else {
                    Toast.makeText(root.getContext(), "Please, give a hashtag!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return root;
    }

    private class AsyncTaskRegisterHashtags extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(Register.this.getContext());
            p.setMessage("Please wait until we find a server..");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            Consumer consumer = ConsumerDAO.getConsumer();
            consumer.register(strings[0]);
            return "";
        }

        @Override
        protected void onPostExecute(String hashtag) {
            super.onPostExecute(hashtag);
            p.hide();
            hashTagToRegister.setText("");
        }

    }

}