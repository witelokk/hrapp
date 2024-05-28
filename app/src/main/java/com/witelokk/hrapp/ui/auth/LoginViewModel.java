package com.witelokk.hrapp.ui.auth;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.witelokk.hrapp.Error;
import com.witelokk.hrapp.Event;
import com.witelokk.hrapp.Result;
import com.witelokk.hrapp.api.model.Token;
import com.witelokk.hrapp.data.repository.LoginRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isAuthorized = new MutableLiveData<>(false);
    private final MutableLiveData<Event<Boolean>> invalidCredentials = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> userAlreadyExists = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> unknownError = new MutableLiveData<>();
    private final LoginRepository repository;
    private final SharedPreferences sharedPreferences;

    @Inject
    LoginViewModel(LoginRepository loginRepository, SharedPreferences sharedPreferences) {
        this.repository = loginRepository;
        this.sharedPreferences = sharedPreferences;
    }

    public LiveData<Boolean> getIsAuthorized() {
        return isAuthorized;
    }

    public LiveData<Event<Boolean>> getInvalidCredentials() {
        return invalidCredentials;
    }

    public LiveData<Event<Boolean>> getUserAlreadyExists() {
        return userAlreadyExists;
    }

    public LiveData<Event<Boolean>> getUnknownError() {
        return unknownError;
    }

    public void login(String email, String password) {
        repository.getAccessToken(email, password).observeForever(result -> {
            if (result.isSuccess()) {
                Token token = result.getData();
                sharedPreferences.edit().putString("access_token", token.getAccessToken()).apply();
                sharedPreferences.edit().putString("refresh_token", token.getRefreshToken()).apply();
                isAuthorized.setValue(true);
            } else if (result.getError() instanceof Error.InvalidCredentials) {
                invalidCredentials.setValue(new Event<>(true));
            } else {
                unknownError.setValue(new Event<>(true));
            }
        });
    }

    public void registerAndLogin(String email, String password) {
        repository.createUser(email, password).observeForever(result -> {
            if (!result.isSuccess()) {
                if (result.getError() instanceof Error.UserAlreadyExists) {
                    userAlreadyExists.setValue(new Event<>(true));
                } else {
                    unknownError.setValue(new Event<>(true));
                }
                return;
            }

            login(email, password);
        });
    }

    public void checkIfAuthorized() {
        isAuthorized.setValue(sharedPreferences.contains("access_token"));
    }
}
