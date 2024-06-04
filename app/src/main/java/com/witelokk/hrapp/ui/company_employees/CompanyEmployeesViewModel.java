package com.witelokk.hrapp.ui.company_employees;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.api.model.Employee;
import com.witelokk.hrapp.data.repository.EmployeesRepository;
import com.witelokk.hrapp.ui.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CompanyEmployeesViewModel extends BaseViewModel {
    private final MutableLiveData<List<Employee>> employees = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);
    private final EmployeesRepository employeesRepository;

    @Inject
    public CompanyEmployeesViewModel(SharedPreferences sharedPreferences, EmployeesRepository employeesRepository) {
        super(sharedPreferences);
        this.employeesRepository = employeesRepository;
    }

    public LiveData<List<Employee>> getEmployees() {
        return employees;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void loadEmployees(int companyId) {
        employeesRepository.getEmployeesByCompany(companyId).observeForever(result -> {
            if (result.isSuccess()) {
                employees.setValue(result.getData());
                isLoading.setValue(false);
            } else {
                setError(result.getError());
            }
        });
    }
}
