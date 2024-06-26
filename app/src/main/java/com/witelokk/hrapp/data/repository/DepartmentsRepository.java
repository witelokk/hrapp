package com.witelokk.hrapp.data.repository;

import androidx.lifecycle.LiveData;

import com.witelokk.hrapp.Result;
import com.witelokk.hrapp.api.model.Department;

import java.util.List;

public interface DepartmentsRepository {
    LiveData<Result<Department>> getDepartment(int departmentId);

    LiveData<Result<List<Department>>> getDepartmentsByCompany(int companyId);

    LiveData<Result<Void>> createDepartment(String name, int companyId);

    LiveData<Result<Void>> editDepartment(int departmentId, String name, int companyId);

    LiveData<Result<Void>> deleteDepartment(int departmentId);
}
