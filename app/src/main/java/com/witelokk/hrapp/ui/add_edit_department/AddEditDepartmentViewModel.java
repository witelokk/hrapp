package com.witelokk.hrapp.ui.add_edit_department;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.Event;
import com.witelokk.hrapp.data.repository.DepartmentsRepository;
import com.witelokk.hrapp.ui.BaseViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddEditDepartmentViewModel extends BaseViewModel {
    private final MutableLiveData<Event<Boolean>> isDone = new MutableLiveData<>();
    private final DepartmentsRepository departmentsRepository;

    @Inject
    AddEditDepartmentViewModel(SharedPreferences sharedPreferences, DepartmentsRepository departmentsRepository) {
        super(sharedPreferences);
        this.departmentsRepository = departmentsRepository;
    }

    LiveData<Event<Boolean>> getIsDone() {
        return isDone;
    }

    void createDepartment(int companyId, String name) {
        departmentsRepository.createDepartment(name, companyId).observeForever(result -> {
            if (result.isSuccess()) {
                isDone.setValue(new Event<>(true));
            } else {
                setError(result.getError());
            }
        });
    }

    void editDepartment(int departmentId, int companyId, String name) {
        departmentsRepository.editDepartment(departmentId, name, companyId).observeForever(result -> {
            if (result.isSuccess()) {
                isDone.setValue(new Event<>(true));
            } else {
                setError(result.getError());
            }
        });
    }
}
