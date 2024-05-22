package com.witelokk.hrapp.ui;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.witelokk.hrapp.Error;
import com.witelokk.hrapp.Event;

public class BaseViewModel extends ViewModel {
    private final MutableLiveData<Event<Error>> error = new MutableLiveData<>();
    private final SharedPreferences sharedPreferences;

    public BaseViewModel(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public LiveData<Event<Error>> getError() {
        return error;
    }

    protected void setError(Error error) {
        if (error instanceof Error.Unauthorized) logout();
        this.error.setValue(new Event<>(error));
    }

    public void logout() {
        sharedPreferences
                .edit()
                .remove("access_token")
                .apply();
    }
}
