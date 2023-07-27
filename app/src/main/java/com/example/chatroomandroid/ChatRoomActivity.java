package com.example.chatroomandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ListView listViewMessages = findViewById(R.id.listViewMessages);

        List<MessageInfo> messageInfos = null;
        try {
            messageInfos = HttpHelper.getMessages();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayAdapter<MessageInfo> adapter = new ArrayAdapter<MessageInfo>(this, R.layout.message_item_layout, R.id.txtViewMessages, messageInfos) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                // Get the current MessageInfo object
                MessageInfo messageInfo = (MessageInfo) getItem(position);

                // Set the user name and message text to the TextView
                if (messageInfo != null) {
                    TextView textViewMessage = view.findViewById(R.id.txtViewMessages);
                    textViewMessage.setText(messageInfo.getUserName() + ": " + messageInfo.getMessage());
                }

                return view;
            }
        };

        // Set the ArrayAdapter to the ListView
        listViewMessages.setAdapter(adapter);
    }
}