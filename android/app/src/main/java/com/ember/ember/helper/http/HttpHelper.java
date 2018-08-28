package com.ember.ember.helper.http;

import com.ember.ember.helper.http.httpInterface.LoginInterface;
import com.ember.ember.model.Login;
import com.ember.ember.model.UserDetails;

import retrofit2.Call;
import retrofit2.Retrofit;

public class HttpHelper {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://google.com/")
            .build();

    private static LoginInterface loginService = retrofit.create(LoginInterface.class);

    public static Call<UserDetails> login(String username, String password) {
         return loginService.createUser(new Login(username, password));
    }
}
