package com.witelokk.hrapp.ui.edit_employee;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.data.repository.EmployeesRepository;
import com.witelokk.hrapp.ui.BaseViewModel;

import java.util.Date;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class EditEmployeeViewModel extends BaseViewModel {
    EmployeesRepository employeesRepository;
    private final MutableLiveData<Boolean> isChanged = new MutableLiveData<>();

    @Inject
    public EditEmployeeViewModel(SharedPreferences sharedPreferences, EmployeesRepository employeesRepository) {
        super(sharedPreferences);
        this.employeesRepository = employeesRepository;
    }

    public LiveData<Boolean> getIsChanged() {
        return isChanged;
    }

    void editEmployee(int employeeId, String name, String gender, Date birthdate, String inn, String snils, String address, String passportIssuer, String passportNumber, Date passportDate) {
        employeesRepository.editEmployee(employeeId, name, gender, birthdate, inn, snils, address, passportIssuer, passportNumber, passportDate).observeForever(result -> {
            if (result.isSuccess()) {
                isChanged.setValue(true);
            } else {
                setError(result.getError());
            }
        });
    }
}
