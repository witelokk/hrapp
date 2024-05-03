package com.witelokk.hrapp.api;

import com.witelokk.hrapp.api.model.GetTokenRequest;
import com.witelokk.hrapp.api.model.Token;
import com.witelokk.hrapp.api.model.CreateUserRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AuthApi {
    @POST("auth/")
    Call<Void> createUser(@Body CreateUserRequest createUserRequest);

    @FormUrlEncoded
    @POST("auth/token")
    Call<Token> getToken(@Field("username") String username, @Field("password") String password);
}
