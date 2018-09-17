package com.ember.ember.helper.http.httpInterface;

import com.ember.ember.model.Login;
import com.ember.ember.model.LoginResponse;
import com.ember.ember.model.UserDetails;
import com.ember.ember.model.UserDetailsList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface HttpInterface {
    @POST("users/exists")
    Call<LoginResponse> login(@Body Login login);

    @POST("users")
    Call<Void> register(@Body UserDetails userDetails);

    @POST("users/matched")
    Call<UserDetailsList> getMatches(@Body Login login);
}
