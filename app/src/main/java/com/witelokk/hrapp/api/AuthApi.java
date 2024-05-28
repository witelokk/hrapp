package com.witelokk.hrapp.api;

import com.witelokk.hrapp.api.model.Token;
import com.witelokk.hrapp.api.model.CreateUserRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("auth/user")
    Call<Void> createUser(@Body CreateUserRequest createUserRequest);

    @FormUrlEncoded
    @POST("auth/token")
    Call<Token> getToken(@Field("grant_type") String grantType, @Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("auth/token")
    Call<Token> refreshToken(@Field("grant_type") String grantType, @Field("refresh_token") String refreshToken);
}
