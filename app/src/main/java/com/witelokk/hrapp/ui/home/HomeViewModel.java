package com.witelokk.hrapp.ui.home;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.witelokk.hrapp.api.model.Company;
import com.witelokk.hrapp.data.repository.CompaniesRepository;
import com.witelokk.hrapp.data.repository.CompaniesRepositoryImpl;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<Company>> companies = new MutableLiveData<>();

    private CompaniesRepository repository;

    @Inject
    HomeViewModel(CompaniesRepository repository) {
        this.repository = repository;
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

