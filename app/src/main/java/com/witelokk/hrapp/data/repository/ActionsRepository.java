package com.witelokk.hrapp.data.repository;

import androidx.lifecycle.LiveData;

import com.witelokk.hrapp.Result;
import com.witelokk.hrapp.api.model.Action;

import java.util.Date;
import java.util.List;

public interface ActionsRepository {
    public LiveData<Result<List<Action>>> getActionsByEmployee(int employeeId);
    public LiveData<Result<Void>> createRecruitmentAction(int employeeId, int departmentId, Date date, String position, float salary);
    public LiveData<Result<Void>> createDepartmentTransferAction(int employeeId, int newDepartmentId, Date date);
    public LiveData<Result<Void>> createPositionTransferAction(int employeeId, String newPosition, Date date);
    public LiveData<Result<Void>> createSalaryChangeAction(int employeeId, float newSalary, Date date);
    public LiveData<Result<Void>> createDismissalAction(int employeeId, Date date);
    public LiveData<Result<Void>> editRecruitmentAction(int actionId, int departmentId, Date date, String position, float salary);
    public LiveData<Result<Void>> editDepartmentTransferAction(int actionId, int newDepartmentId, Date date);
    public LiveData<Result<Void>> editPositionTransferAction(int actionId, String newPosition, Date date);
    public LiveData<Result<Void>> editSalaryChangeAction(int actionId, float newSalary, Date date);
    public LiveData<Result<Void>> editDismissalAction(int actionId, Date date);
    public LiveData<Result<Void>> deleteAction(int actionId);
}
