package com.witelokk.hrapp.ui.company;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.witelokk.hrapp.api.model.Department;
import com.witelokk.hrapp.data.repository.DepartmentsRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CompanyViewModel extends ViewModel {
    private final MutableLiveData<List<Department>> departments = new MutableLiveData<>();
    private final DepartmentsRepository departmentsRepository;
    private int companyId;

    @Inject
    CompanyViewModel(DepartmentsRepository departmentsRepository) {
        this.departmentsRepository = departmentsRepository;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public LiveData<List<Department>> getDepartments() {
        return departments;
    }

    public void loadDepartments() {
        departmentsRepository.getDepartmentsByCompany(companyId).observeForever(result -> {
            if (result.isSuccess()) {
               departments.setValue(result.getData());
            } else {
                // show error
            }
        });
    }
}
