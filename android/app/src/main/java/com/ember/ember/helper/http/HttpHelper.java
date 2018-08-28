package com.ember.ember.helper.http;

import com.ember.ember.helper.http.httpInterface.HttpInterface;
import com.ember.ember.model.Login;
import com.ember.ember.model.UserDetails;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpHelper {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://google.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static HttpInterface loginService = retrofit.create(HttpInterface.class);

    public static Call<UserDetails> login(String username, String password) {
         return loginService.login(new Login(username, password));
    }

    public static Call<Void> register(UserDetails userDetails) {
        return loginService.register(userDetails);
    }
}
