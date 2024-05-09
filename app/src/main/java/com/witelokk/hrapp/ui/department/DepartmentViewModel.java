package com.witelokk.hrapp.ui.department;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.Error;
import com.witelokk.hrapp.api.model.Employee;
import com.witelokk.hrapp.data.repository.EmployeesRepository;
import com.witelokk.hrapp.ui.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DepartmentViewModel extends BaseViewModel {
    private final EmployeesRepository employeesRepository;
    private int departmentId;
    private final MutableLiveData<List<Employee>> employees = new MutableLiveData<>();

    @Inject
    public DepartmentViewModel(SharedPreferences sharedPreferences, EmployeesRepository employeesRepository) {
        super(sharedPreferences);
        this.employeesRepository = employeesRepository;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public LiveData<List<Employee>> getEmployees() {
        return employees;
    }

    public void loadEmployees() {
        employeesRepository.getEmployeesByDepartment(departmentId).observeForever(result -> {
            if (result.isSuccess()) {
                employees.setValue(result.getData());
            } else {
                setError(result.getError());
            }
        });
    }
}
