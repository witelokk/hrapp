package com.witelokk.hrapp.ui.department;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.api.model.Department;
import com.witelokk.hrapp.api.model.Employee;
import com.witelokk.hrapp.data.repository.DepartmentsRepository;
import com.witelokk.hrapp.data.repository.EmployeesRepository;
import com.witelokk.hrapp.ui.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DepartmentViewModel extends BaseViewModel {
    private final EmployeesRepository employeesRepository;
    private final DepartmentsRepository departmentsRepository;
    private int departmentId;
    private final MutableLiveData<Department> department = new MutableLiveData<>();
    private final MutableLiveData<List<Employee>> employees = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);

    @Inject
    public DepartmentViewModel(SharedPreferences sharedPreferences, EmployeesRepository employeesRepository, DepartmentsRepository departmentsRepository) {
        super(sharedPreferences);
        this.employeesRepository = employeesRepository;
        this.departmentsRepository = departmentsRepository;
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
    public LiveData<Department> getDepartment() {
        return department;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void loadData() {
        isLoading.setValue(true);
        departmentsRepository.getDepartment(departmentId).observeForever(result -> {
            if (result.isSuccess()) {
                department.setValue(result.getData());
                if (employees.getValue() != null)
                    isLoading.setValue(false);
            } else {
                setError(result.getError());
            }
        });

        employeesRepository.getEmployeesByDepartment(departmentId).observeForever(result -> {
            if (result.isSuccess()) {
                employees.setValue(result.getData());
                if (department.getValue() != null)
                    isLoading.setValue(false);
            } else {
                setError(result.getError());
            }
        });
    }
}
