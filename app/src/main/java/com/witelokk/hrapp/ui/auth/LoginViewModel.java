package com.witelokk.hrapp.ui.auth;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.AndroidViewModel;

import com.witelokk.hrapp.data.repository.LoginRepository;
import com.witelokk.hrapp.data.repository.LoginRepositoryImpl;

public class LoginViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> isAuthorized = new MutableLiveData<>(false);
    private LoginRepository repository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new LoginRepositoryImpl(application.getSharedPreferences("prefs", Context.MODE_PRIVATE));

        String access_token = repository.getAccessToken();
        if (access_token != null) {
            isAuthorized.setValue(true);
        }
    }

    public LiveData<Boolean> getIsAuthorized() {
        return isAuthorized;
    }

    public void login(String email, String password) {
        repository.getAccessToken(email, password).observeForever(result -> {
            if (result.isSuccess()) {
                isAuthorized.setValue(true);
            } else {
                Toast.makeText(getApplication(), result.getError(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
