package com.witelokk.hrapp.ui.auth;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.witelokk.hrapp.Error;
import com.witelokk.hrapp.api.model.Token;
import com.witelokk.hrapp.data.repository.LoginRepository;
import com.witelokk.hrapp.ui.BaseViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isAuthorized = new MutableLiveData<>(false);
    private final MutableLiveData<Void> invalidCredentials = new MutableLiveData<>();
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

    public LiveData<Void> getInvalidCredentials() {
        return invalidCredentials;
    }

    public void login(String email, String password) {
        repository.getAccessToken(email, password).observeForever(result -> {
            if (result.isSuccess()) {
                Token token = result.getData();
                sharedPreferences.edit().putString("access_token", token.getAccessToken()).apply();
                isAuthorized.setValue(true);
            } else {
                if (result.getError() instanceof Error.InvalidCredentials) {
                    invalidCredentials.setValue(null);
                } else {
                    // show error
//                    setError(result.getError());
                }
            }
        });
    }

    public void checkIfAuthorized() {
        isAuthorized.setValue(sharedPreferences.contains("access_token"));
    }
}
