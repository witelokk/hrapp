package com.witelokk.hrapp.ui.recruitment_action;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.Event;
import com.witelokk.hrapp.api.model.Department;
import com.witelokk.hrapp.data.repository.ActionsRepository;
import com.witelokk.hrapp.data.repository.DepartmentsRepository;
import com.witelokk.hrapp.ui.BaseViewModel;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RecruitmentActionFragmentViewModel extends BaseViewModel {
    private final MutableLiveData<List<Department>> departments = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> isDone = new MutableLiveData<>();
    private final DepartmentsRepository departmentsRepository;
    private final ActionsRepository actionsRepository;

    @Inject
    public RecruitmentActionFragmentViewModel(SharedPreferences sharedPreferences, DepartmentsRepository departmentsRepository, ActionsRepository actionsRepository) {
        super(sharedPreferences);
        this.departmentsRepository = departmentsRepository;
        this.actionsRepository = actionsRepository;
    }

    public LiveData<List<Department>> getDepartments() {
        return departments;
    }

    public LiveData<Event<Boolean>> getIsDone() {
        return isDone;
    }

    void loadDepartments(int companyId) {
        departmentsRepository.getDepartmentsByCompany(companyId).observeForever(result -> {
            if (result.isSuccess()) {
                departments.setValue(result.getData());
            } else {
                setError(result.getError());
            }
        });
    }

    void editAction(int actionId, Date date, int departmentId, String position, float salary) {
        actionsRepository.editRecruitmentAction(actionId, departmentId, date, position, salary).observeForever(result -> {
            if (result.isSuccess()) {
                isDone.setValue(new Event<>(true));
            } else {
                setError(result.getError());
            }
        });
    }
}
