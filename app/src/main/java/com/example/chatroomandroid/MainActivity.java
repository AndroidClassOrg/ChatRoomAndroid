package com.example.chatroomandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;


import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;
import kotlin.coroutines.CoroutineContext;

public class MainActivity extends AppCompatActivity {




    private EditText editTextUserName;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(gfgPolicy);

        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);

        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonSignup = findViewById(R.id.buttonSignup);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

    }

    private void login() {
        String userName = editTextUserName.getText().toString();
        String password = editTextPassword.getText().toString();

        try {
            String response = HttpHelper.login(userName, password);
            GlobalContainer.UserToken = response;
            Intent intent = new Intent(this, ChatRoomActivity.class);
            startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }


    private void signup() {
        // Get user input for signup (e.g., name, email, password, etc.)
        User user = new User(/* Set user details */);

        try {
            String response = HttpHelper.register(user);
            // Handle the signup response here
            // For example, if the response contains a success message, show a signup success message
            // Otherwise, show an error message indicating signup failed.
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Signup failed. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}
