package com.witelokk.hrapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.Error;
import com.witelokk.hrapp.api.AuthApi;
import com.witelokk.hrapp.Result;
import com.witelokk.hrapp.api.model.Token;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepositoryImpl implements LoginRepository {
    private final AuthApi authApi;

    public LoginRepositoryImpl(AuthApi authApi) {
        this.authApi = authApi;
    }

    @Override
    public LiveData<Result<Token>> getAccessToken(String email, String password) {
        MutableLiveData<Result<Token>> resultLiveData = new MutableLiveData<>();

        authApi.getToken(email, password).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(@NonNull Call<Token> call, @NonNull Response<Token> response) {
                if (response.isSuccessful()) {
                    resultLiveData.setValue(Result.success(response.body()));
                } else if (response.code() == HttpURLConnection.HTTP_BAD_REQUEST) {
                    resultLiveData.setValue(Result.error(new Error.InvalidCredentials()));
                } else {
                    resultLiveData.setValue(Result.error(new Error.Unknown()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Token> call, @NonNull Throwable t) {
                resultLiveData.setValue(Result.error(new Error.Network()));
            }
        });

        return resultLiveData;
    }
}
