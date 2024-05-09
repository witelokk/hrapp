package com.witelokk.hrapp.ui.add_company;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.Error;
import com.witelokk.hrapp.data.repository.CompaniesRepository;
import com.witelokk.hrapp.ui.BaseViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddCompanyViewModel extends BaseViewModel {
    private final MutableLiveData<Boolean> isCompanyCreated = new MutableLiveData<>();
    private final CompaniesRepository companiesRepository;

    @Inject
    AddCompanyViewModel(SharedPreferences sharedPreferences, CompaniesRepository companiesRepository) {
        super(sharedPreferences);
        this.companiesRepository = companiesRepository;
    }

    LiveData<Boolean> getIsCompanyCreated() {
        return isCompanyCreated;
    }

    void createCompany(String name, String inn, String kpp) {
        companiesRepository.addCompany(name, inn, kpp).observeForever(result -> {
            if (result.isSuccess()) {
                isCompanyCreated.setValue(true);
            } else {
                setError(result.getError());
            }
        });
    }
}
