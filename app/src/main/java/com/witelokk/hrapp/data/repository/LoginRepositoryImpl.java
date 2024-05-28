package com.witelokk.hrapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.Error;
import com.witelokk.hrapp.api.AuthApi;
import com.witelokk.hrapp.Result;
import com.witelokk.hrapp.api.model.CreateUserRequest;
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

        authApi.getToken("password", email, password).enqueue(new Callback<Token>() {
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

    @Override
    public LiveData<Result<Void>> createUser(String email, String password) {
        MutableLiveData<Result<Void>> resultLiveData = new MutableLiveData<>();

        authApi.createUser(new CreateUserRequest(email, password)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    resultLiveData.setValue(Result.success(response.body()));
                } else if (response.code() == HttpURLConnection.HTTP_CONFLICT) {
                    resultLiveData.setValue(Result.error(new Error.UserAlreadyExists()));
                } else {
                    resultLiveData.setValue(Result.error(new Error.Unknown()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                resultLiveData.setValue(Result.error(new Error.Network()));
            }
        });

        return resultLiveData;
    }
}
