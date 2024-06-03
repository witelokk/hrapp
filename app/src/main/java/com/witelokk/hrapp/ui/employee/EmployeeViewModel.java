package com.witelokk.hrapp.ui.employee;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.Event;
import com.witelokk.hrapp.api.model.Action;
import com.witelokk.hrapp.api.model.Employee;
import com.witelokk.hrapp.data.repository.ActionsRepository;
import com.witelokk.hrapp.data.repository.EmployeesRepository;
import com.witelokk.hrapp.ui.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class EmployeeViewModel extends BaseViewModel {
    private final MutableLiveData<Employee> employee = new MutableLiveData<>();

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);
    private final MutableLiveData<Event<Boolean>> isDeleted = new MutableLiveData<>();

    private final EmployeesRepository employeesRepository;

    private int employeeId;

    @Inject
    public EmployeeViewModel(SharedPreferences sharedPreferences, EmployeesRepository employeesRepository) {
        super(sharedPreferences);
        this.employeesRepository = employeesRepository;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public LiveData<Employee> getEmployee() {
        return employee;
    }

    public LiveData<Event<Boolean>> getIsDeleted() {
        return isDeleted;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void loadData() {
        employeesRepository.getEmployee(employeeId, true).observeForever(result -> {
            if (result.isSuccess()) {
                employee.setValue(result.getData());
                isLoading.setValue(false);
            } else {
                setError(result.getError());
            }
        });
    }

    public void deleteEmployee() {
        employeesRepository.deleteEmployee(employeeId).observeForever(result -> {
            if (result.isSuccess()) {
                isDeleted.setValue(new Event<>(true));
            } else {
                setError(result.getError());
            }
        });
    }
}
