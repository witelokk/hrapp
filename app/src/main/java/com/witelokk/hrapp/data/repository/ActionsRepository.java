package com.witelokk.hrapp.data.repository;

import androidx.lifecycle.LiveData;

import com.witelokk.hrapp.Result;
import com.witelokk.hrapp.api.model.Action;

import java.util.List;

public interface ActionsRepository {
    public LiveData<Result<List<Action>>> getActionsByEmployee(int employeeId);
}
