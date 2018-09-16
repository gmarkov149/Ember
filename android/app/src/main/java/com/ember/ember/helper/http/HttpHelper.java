package com.ember.ember.helper.http;

import com.ember.ember.helper.http.httpInterface.HttpInterface;
import com.ember.ember.model.Login;
import com.ember.ember.model.UserDetails;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpHelper {

//    private static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

//    private static OkHttpClient client = new OkHttpClient.Builder()
//            .addInterceptor(interceptor)
//            .build();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.0.101:4567")
            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
            .build();

    private static HttpInterface loginService = retrofit.create(HttpInterface.class);

    public static Call<UserDetails> login(String username, String password) {
         return loginService.login(new Login(username, password));
    }

    public static Call<Void> register(UserDetails userDetails) {
        return loginService.register(userDetails);
    }
}
