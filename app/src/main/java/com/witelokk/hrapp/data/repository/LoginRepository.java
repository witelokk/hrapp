package com.witelokk.hrapp.data.repository;


import androidx.lifecycle.LiveData;

import com.witelokk.hrapp.api.model.Token;
import com.witelokk.hrapp.Result;

public interface LoginRepository {
    LiveData<Result<Token>> getAccessToken(String email, String password);
}
