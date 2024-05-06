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
    private AuthApi authApi;

    public LoginRepositoryImpl(AuthApi authApi) {
        this.authApi = authApi;
    }

    @Override
    public LiveData<Result<Token>> getAccessToken(String email, String password) {
        MutableLiveData<Result<Token>> resultLiveData = new MutableLiveData<>();

        authApi.getToken(email, password).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.code() == 200) {
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
