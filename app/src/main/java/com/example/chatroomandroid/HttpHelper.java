package com.example.chatroomandroid;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpHelper {
    private static final String TAG = HttpHelper.class.getSimpleName();
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new GsonBuilder().create();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static List<User> getUsers() throws IOException {
        Request request = new Request.Builder()
                .url(GlobalContainer.ChatRoomApiUrl + "User")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response);
            }
            String responseBody = response.body().string();

            try {
                Type userListType = new TypeToken<List<String>>() {
                }.getType();

                List<String> users = gson.fromJson(responseBody, userListType);

                List<User> userList = new ArrayList<User>();

                for (String user :  users)
                {
                    User userObj = new User();
                    userObj.setUserName(user);
                    userList.add(userObj);
                }

                return  userList;
            }
            catch (Exception ex)
            {
                String s = ex.getMessage();
                return  null;
            }
        }
    }

    public static String login(String userName, String password) throws IOException {
        Request request = new Request.Builder()
                .url(GlobalContainer.ChatRoomApiUrl + "User/" + userName + "/" + password)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response);
            }
            return response.body().string();
        }
    }

    public static String register(User user) throws IOException {
        String jsonPayload = gson.toJson(user);
        RequestBody requestBody = RequestBody.create(JSON, jsonPayload);

        Request request = new Request.Builder()
                .url(GlobalContainer.ChatRoomApiUrl + "User")
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response);
            }
            return response.body().string();
        }
    }

    public static List<MessageInfo> getMessages() throws IOException {
        Request request = new Request.Builder()
                .url(GlobalContainer.ChatRoomApiUrl + "Message")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response);
            }
            String responseBody = response.body().string();
            return gson.fromJson(responseBody, new TypeToken<List<MessageInfo>>() {}.getType());
        }
    }

    public static void sendMessage(String message) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, gson.toJson(message));

        Request request = new Request.Builder()
                .url(GlobalContainer.ChatRoomApiUrl + "Message")
                .post(requestBody)
                .addHeader("UserToken", GlobalContainer.UserToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response);
            }
        }
    }
}
