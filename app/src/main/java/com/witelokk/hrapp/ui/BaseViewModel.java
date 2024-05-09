package com.witelokk.hrapp.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {
    private final MutableLiveData<Void> unknownError = new MutableLiveData<>();
    private final MutableLiveData<Void> networkError = new MutableLiveData<>();
    private final MutableLiveData<Void> unauthorizedError = new MutableLiveData<>();

    public LiveData<Void> getUnknownError() {
        return unknownError;
    }

    public LiveData<Void> getNetworkError() {
        return networkError;
    }

    public LiveData<Void> getUnauthorizedError() {
        return unauthorizedError;
    }

    protected void setUnknownError() {
        unknownError.setValue(null);
    }

    protected void setNetworkError() {
        networkError.setValue(null);
    }

    protected void logout() {
        unauthorizedError.setValue(null);
    }
}
