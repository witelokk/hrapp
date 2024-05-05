package com.witelokk.hrapp.ui.home;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.api.model.Company;
import com.witelokk.hrapp.data.repository.CompaniesRepository;
import com.witelokk.hrapp.data.repository.CompaniesRepositoryImpl;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private MutableLiveData<List<Company>> companies = new MutableLiveData<>();
    private CompaniesRepository repository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = new CompaniesRepositoryImpl(application.getSharedPreferences("prefs", Context.MODE_PRIVATE).getString("access_token", "none"));
    }

    void loadCompanies() {
        repository.getCompanies().observeForever(companies -> {
            if (companies.isSuccess()) {
                this.companies.setValue(companies.getData());
            }
        });
    }

    LiveData<List<Company>> getCompanies() {
        return companies;
    }
}

