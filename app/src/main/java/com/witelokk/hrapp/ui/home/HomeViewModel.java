package com.witelokk.hrapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.Error;
import com.witelokk.hrapp.api.model.Company;
import com.witelokk.hrapp.data.repository.CompaniesRepository;
import com.witelokk.hrapp.ui.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeViewModel extends BaseViewModel {
    private final MutableLiveData<List<Company>> companies = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);
    private final CompaniesRepository repository;

    @Inject
    HomeViewModel(CompaniesRepository repository) {
        this.repository = repository;
    }

    void loadCompanies() {
        repository.getCompanies().observeForever(result -> {
            if (result.isSuccess()) {
                companies.setValue(result.getData());
                isLoading.setValue(false);
            } else {
                if (result.getError() instanceof Error.Unauthorized) {
                    logout();
                } else if (result.getError() instanceof Error.Network) {
                    setNetworkError();
                } else if (result.getError() instanceof Error.Unknown) {
                    setUnknownError();
                }
            }
        });
    }

    LiveData<List<Company>> getCompanies() {
        return companies;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
}

