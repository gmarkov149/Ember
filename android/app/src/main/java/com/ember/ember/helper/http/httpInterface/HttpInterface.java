package com.ember.ember.helper.http.httpInterface;

import com.ember.ember.model.Login;
import com.ember.ember.model.UserDetails;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface HttpInterface {
    @POST("login")
    Call<UserDetails> login(@Body Login login);

    @POST("users")
    Call<Void> register(@Body UserDetails userDetails);
}
