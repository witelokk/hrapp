package com.witelokk.hrapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.witelokk.hrapp.api.model.Company;
import com.witelokk.hrapp.data.repository.CompaniesRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<Company>> companies = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    private CompaniesRepository repository;

    @Inject
    HomeViewModel(CompaniesRepository repository) {
        this.repository = repository;
    }

    void loadCompanies() {
        repository.getCompanies().observeForever(result -> {
            if (result.isSuccess()) {
                this.companies.setValue(result.getData());
            } else {
                errorLiveData.setValue(result.getError());
            }
        });
    }

    LiveData<List<Company>> getCompanies() {
        return companies;
    }

    LiveData<String> getErrorLiveData() {return errorLiveData;}
}

