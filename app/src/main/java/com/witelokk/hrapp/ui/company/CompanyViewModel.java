package com.witelokk.hrapp.ui.company;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.api.model.Company;
import com.witelokk.hrapp.data.repository.CompaniesRepository;
import com.witelokk.hrapp.ui.BaseViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CompanyViewModel extends BaseViewModel {
    private final MutableLiveData<Company> company = new MutableLiveData<>();
    private final CompaniesRepository companiesRepository;
    private int companyId;

    @Inject
    public CompanyViewModel(SharedPreferences sharedPreferences, CompaniesRepository companiesRepository) {
        super(sharedPreferences);
        this.companiesRepository = companiesRepository;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public LiveData<Company> getCompany() {
        return company;
    }

    public void loadData() {
        companiesRepository.getCompany(companyId).observeForever(result -> {
            if (result.isSuccess()) {
                company.setValue(result.getData());
//                isLoading.setValue(false);
            } else {
                setError(result.getError());
            }
        });
    }
}
