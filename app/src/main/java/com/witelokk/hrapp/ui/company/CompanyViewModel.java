package com.witelokk.hrapp.ui.company;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.Error;
import com.witelokk.hrapp.api.model.Department;
import com.witelokk.hrapp.data.repository.DepartmentsRepository;
import com.witelokk.hrapp.ui.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CompanyViewModel extends BaseViewModel {
    private final MutableLiveData<List<Department>> departments = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);
    private final DepartmentsRepository departmentsRepository;
    private int companyId;

    @Inject
    CompanyViewModel(SharedPreferences sharedPreferences, DepartmentsRepository departmentsRepository) {
        super(sharedPreferences);
        this.departmentsRepository = departmentsRepository;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public LiveData<List<Department>> getDepartments() {
        return departments;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void loadDepartments() {
        departmentsRepository.getDepartmentsByCompany(companyId).observeForever(result -> {
            if (result.isSuccess()) {
                departments.setValue(result.getData());
                isLoading.setValue(false);
            } else {
                setError(result.getError());
            }
        });
    }
}
