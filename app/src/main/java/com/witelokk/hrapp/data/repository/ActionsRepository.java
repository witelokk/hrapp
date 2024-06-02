package com.witelokk.hrapp.data.repository;

import androidx.lifecycle.LiveData;

import com.witelokk.hrapp.Result;
import com.witelokk.hrapp.api.model.Action;
import com.witelokk.hrapp.api.model.CreateRecruitmentActionRequest;

import java.util.Date;
import java.util.List;

public interface ActionsRepository {
    public LiveData<Result<List<Action>>> getActionsByEmployee(int employeeId);
    public LiveData<Result<Void>> createRecruitmentAction(int employeeId, int departmentId, Date recruitmentDate, String position, float salary);
    public LiveData<Result<Void>> createDepartmentTransferAction(int employeeId, int newDepartmentId, Date date);
    public LiveData<Result<Void>> createPositionTransferAction(int employeeId, String newPosition, Date date);
    public LiveData<Result<Void>> deleteAction(int actionId);
}
