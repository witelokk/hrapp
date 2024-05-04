package com.witelokk.hrapp.data.repository;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.api.AuthApi;
import com.witelokk.hrapp.Result;
import com.witelokk.hrapp.api.model.Token;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginRepositoryImpl implements LoginRepository {
    private SharedPreferences sharedPreferences;
    private AuthApi authApi;

    public LoginRepositoryImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://128.199.48.210/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build())
                .build();
        this.authApi = retrofit.create(AuthApi.class);

    }

    @Override
    public String getAccessToken() {
        return sharedPreferences.getString("access_token", null);
    }

    @Override
    public LiveData<Result<Token>> getAccessToken(String email, String password) {
        MutableLiveData<Result<Token>> resultLiveData = new MutableLiveData<>();

        authApi.getToken(email, password).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.code() == 200) {
                    sharedPreferences.edit().putString("access_token", response.body().getAccessToken()).apply();
                    resultLiveData.setValue(new Result<>(response.body()));
                } else {
                    try {
                        resultLiveData.setValue(new Result<>(String.format("ERROR %d: %s", response.code(), response.errorBody().string())));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable throwable) {
                resultLiveData.setValue(new Result<>(throwable.getMessage()));
            }
        });

        return resultLiveData;
    }
}
