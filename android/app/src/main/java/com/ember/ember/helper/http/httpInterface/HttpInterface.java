package com.ember.ember.helper.http.httpInterface;

import com.ember.ember.model.ChatList;
import com.ember.ember.model.Login;
import com.ember.ember.model.LoginResponse;
import com.ember.ember.model.UserDetails;
import com.ember.ember.model.UserDetailsList;

import java.util.List;

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

    @GET("users/matched/{username}/0/10")
    Call<UserDetailsList> getMatches(@Path("username") String username);

    @GET("users/potentialMatches/{username}/0/10")
    Call<UserDetailsList> getPotentialMatches(@Path("username") String username);

    @POST("users/match/{username}")
    Call<Void> match(@Path("username") String username, @Body UserDetails userDetails);

    @GET("users/chat/{user}/{match}/{startIndex}")
    Call<ChatList> getChat(@Path("user") String username, @Path("match") String match, @Path("startIndex") int startIndex);

    @GET("users/chat/message/{sender}/{receiver}/{date}/{time}/{message}")
    Call<Void> sendChat(@Path("sender") String sender, @Path("receiver") String receiver,
                    @Path("date") String date, @Path("time") String time, @Path("message") String message);
}
