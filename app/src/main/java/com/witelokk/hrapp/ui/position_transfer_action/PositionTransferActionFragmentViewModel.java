package com.witelokk.hrapp.ui.position_transfer_action;

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
import kotlin.NotImplementedError;

@HiltViewModel
public class PositionTransferActionFragmentViewModel extends BaseViewModel {
    private final MutableLiveData<Event<Boolean>> isDone = new MutableLiveData<>();
    private final ActionsRepository actionsRepository;

    @Inject
    public PositionTransferActionFragmentViewModel(SharedPreferences sharedPreferences, ActionsRepository actionsRepository) {
        super(sharedPreferences);
        this.actionsRepository = actionsRepository;
    }


    public LiveData<Event<Boolean>> getIsDone() {
        return isDone;
    }

    void createAction(int employeeId, Date date, String newPosition) {
        actionsRepository.createPositionTransferAction(employeeId, newPosition, date).observeForever(result -> {
            if (result.isSuccess()) {
                isDone.setValue(new Event<>(true));
            } else {
                setError(result.getError());
            }
        });
    }

    void editAction(int employeeId, Date date, String newPosition) {
        throw new NotImplementedError();
    }

    void deleteAction(int actionId) {
        actionsRepository.deleteAction(actionId).observeForever(result -> {
            if (result.isSuccess()) {
                isDone.setValue(new Event<>(true));
            } else {
                setError(result.getError());
            }
        });
    }
}
