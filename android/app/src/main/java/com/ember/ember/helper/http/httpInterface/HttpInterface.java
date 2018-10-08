package com.ember.ember.helper.http.httpInterface;

import com.ember.ember.model.Login;
import com.ember.ember.model.LoginResponse;
import com.ember.ember.model.UserDetails;
import com.ember.ember.model.UserDetailsList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HttpInterface {
    @POST("users/exists")
    Call<LoginResponse> login(@Body Login login);

    @POST("users")
    Call<Void> register(@Body UserDetails userDetails);

    @POST("users/edit")
    Call<Void> editProfile(@Body UserDetails userDetails);

    @POST("users/matched")
    Call<UserDetailsList> getMatches(@Body Login login);

    @GET("users/potentialMatches/{username}")
    Call<UserDetailsList> getPotentialMatches(@Path("username") String username);

    @POST("users/match/{username}")
    Call<Void> match(@Path("username") String username, @Body UserDetails userDetails);
}
