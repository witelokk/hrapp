package com.witelokk.hrapp.data.repository;

import androidx.lifecycle.LiveData;

import com.witelokk.hrapp.Result;
import com.witelokk.hrapp.api.model.Employee;

import java.util.List;

public interface EmployeesRepository {
    LiveData<Result<List<Employee>>> getEmployeesByCompany(int companyId);

    LiveData<Result<List<Employee>>> getEmployeesByDepartment(int departmentId);

    LiveData<Result<Employee>> getEmployee(int employeeId);

    LiveData<Result<Void>> deleteEmployee(int employeeId);

    LiveData<Result<Void>> editEmployee(int employeeId, String name);

    LiveData<Result<Void>> createEmployee(String name);
}
