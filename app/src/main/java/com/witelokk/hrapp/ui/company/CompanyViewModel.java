package com.witelokk.hrapp.ui.company;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.api.model.Company;
import com.witelokk.hrapp.api.model.Department;
import com.witelokk.hrapp.data.repository.CompaniesRepository;
import com.witelokk.hrapp.data.repository.DepartmentsRepository;
import com.witelokk.hrapp.ui.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CompanyViewModel extends BaseViewModel {
    private final MutableLiveData<Company> company = new MutableLiveData<>();
    private final MutableLiveData<List<Department>> departments = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);
    private final DepartmentsRepository departmentsRepository;
    private final CompaniesRepository companiesRepository;
    private int companyId;

    @Inject
    CompanyViewModel(SharedPreferences sharedPreferences, CompaniesRepository companiesRepository, DepartmentsRepository departmentsRepository) {
        super(sharedPreferences);
        this.companiesRepository = companiesRepository;
        this.departmentsRepository = departmentsRepository;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public LiveData<List<Department>> getDepartments() {
        return departments;
    }

    public LiveData<Company> getCompany() {
        return company;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void loadData() {
        departmentsRepository.getDepartmentsByCompany(companyId).observeForever(result -> {
            if (result.isSuccess()) {
                departments.setValue(result.getData());
                if (company.getValue() != null)
                    isLoading.setValue(false);
            } else {
                setError(result.getError());
            }
        });
        companiesRepository.getCompany(companyId).observeForever(result -> {
            if (result.isSuccess()) {
                company.setValue(result.getData());
                if (departments.getValue() != null)
                    isLoading.setValue(false);
            } else {
                setError(result.getError());
            }
        });
    }
}
