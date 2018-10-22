package com.ember.ember.helper.http;

import com.ember.ember.helper.http.httpInterface.HttpInterface;
import com.ember.ember.model.Chat;
import com.ember.ember.model.ChatList;
import com.ember.ember.model.Login;
import com.ember.ember.model.LoginResponse;
import com.ember.ember.model.UserDetails;
import com.ember.ember.model.UserDetailsList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpHelper {

    private static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.27.39.217:4567")//10.27.39.217//192.168.0.101//10.27.45.207
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();

    private static HttpInterface httpInterface = retrofit.create(HttpInterface.class);

    public static Call<LoginResponse> login(String username, String password) {
         return httpInterface.login(new Login(username, password));
    }

    public static Call<Void> register(UserDetails userDetails) {
        return httpInterface.register(userDetails);
    }

    public static Call<Void> editProfile(UserDetails userDetails) {
        return httpInterface.editProfile(userDetails);
    }

    public static Call<UserDetailsList> getMatches(String username) {
        return httpInterface.getMatches(username);
    }

    public static Call<UserDetailsList> getPotentialMatches(String username) {
        return httpInterface.getPotentialMatches(username);
    }

    public static Call<Void> match(String username, UserDetails matchedUser) {
        return httpInterface.match(username, matchedUser);
    }

    public static Call<ChatList> getChat(String username, String match, int startIndex) {
        return httpInterface.getChat(username, match, startIndex);
    }

    public static Call<Void> sendChat(Chat chat, String sender, String receiver) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = df.format(chat.getDate());
        return httpInterface.sendChat(sender, receiver, dateString.split(" ")[0], dateString.split(" ")[1], chat.getMessage());
    }
}
