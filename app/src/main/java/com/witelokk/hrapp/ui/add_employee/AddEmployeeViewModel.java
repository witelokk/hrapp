package com.witelokk.hrapp.ui.add_employee;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.Event;
import com.witelokk.hrapp.api.model.Employee;
import com.witelokk.hrapp.data.repository.ActionsRepository;
import com.witelokk.hrapp.data.repository.EmployeesRepository;
import com.witelokk.hrapp.ui.BaseViewModel;

import java.util.Date;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddEmployeeViewModel extends BaseViewModel {
    private final EmployeesRepository employeesRepository;
    private final ActionsRepository actionsRepository;
    private final MutableLiveData<Event<Boolean>> isCreated = new MutableLiveData<>();

    @Inject
    public AddEmployeeViewModel(SharedPreferences sharedPreferences, EmployeesRepository employeesRepository, ActionsRepository actionsRepository) {
        super(sharedPreferences);
        this.employeesRepository = employeesRepository;
        this.actionsRepository = actionsRepository;
    }

    public LiveData<Event<Boolean>> getIsCreated() {
        return isCreated;
    }

    public void addEmployee(int departmentId, String name, String gender, Date birthdate, String inn, String snils, String address, String passportIssuer, String passportNumber, Date passportDate, Date recruitmentDate, String position, float salary) {
        employeesRepository.createEmployee(name, gender, birthdate, inn, snils, address, passportIssuer, passportNumber, passportDate).observeForever(createEmployeeResult -> {
            if (!createEmployeeResult.isSuccess()) {
                setError(createEmployeeResult.getError());
                return;
            }
            Employee employee = createEmployeeResult.getData();
            actionsRepository.createRecruitmentAction(employee.getId(), departmentId, recruitmentDate, position, salary).observeForever(createActionResult -> {
                if (!createEmployeeResult.isSuccess()) {
                    setError(createEmployeeResult.getError());
                } else {
                    isCreated.setValue(new Event<>(true));
                }
            });

        });
    }
}
