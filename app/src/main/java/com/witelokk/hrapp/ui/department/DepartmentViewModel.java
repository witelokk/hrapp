package com.witelokk.hrapp.ui.department;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.witelokk.hrapp.api.model.Department;
import com.witelokk.hrapp.api.model.Employee;
import com.witelokk.hrapp.data.repository.EmployeesRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DepartmentViewModel extends ViewModel {
    private EmployeesRepository employeesRepository;
    private int departmentId;
    private MutableLiveData<List<Employee>> employees = new MutableLiveData<>();

    @Inject
    public DepartmentViewModel(EmployeesRepository employeesRepository) {
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
                // show error
            }
        });
    }
}
