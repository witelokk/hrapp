package com.witelokk.hrapp.ui.auth;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.witelokk.hrapp.api.model.Token;
import com.witelokk.hrapp.data.repository.LoginRepository;
import com.witelokk.hrapp.data.repository.LoginRepositoryImpl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isAuthorized = new MutableLiveData<>(false);
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

    public void login(String email, String password) {
        repository.getAccessToken(email, password).observeForever(result -> {
            if (result.isSuccess()) {
                Token token = result.getData();

                sharedPreferences.edit().putString("access_token", token.getAccessToken()).apply();

                isAuthorized.setValue(true);
            } else {
//                Toast.makeText(getApplication(), result.getError(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void checkIfAuthorized() {
        if (sharedPreferences.contains("access_token")) isAuthorized.setValue(true);
    }
}
