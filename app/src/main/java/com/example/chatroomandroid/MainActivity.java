package com.example.chatroomandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView userList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userList = findViewById(R.id.userList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        userList.setAdapter(adapter);

        // Call the method to retrieve and display the list of users
        getUsers();
    }

    private void getUsers() {
        new AsyncTask<Void, Void, List<User>>() {

            @Override
            protected List<User> doInBackground(Void... voids) {
                try {
                    return HttpHelper.getUsers();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<User> users) {
                if (users != null) {
                    for (User user : users) {
                        adapter.add(user.getUserName());
                    }
                }
            }
        }.execute();
    }
}
