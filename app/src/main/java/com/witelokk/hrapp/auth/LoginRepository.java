package com.witelokk.hrapp.auth;

import androidx.lifecycle.LiveData;

import com.witelokk.hrapp.api.model.Token;

public interface LoginRepository {
    String getAccessToken();

    LiveData<Result<Token>> getAccessToken(String email, String password);
}
