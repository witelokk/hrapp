package com.witelokk.hrapp.data.repository;

import androidx.lifecycle.LiveData;

import com.witelokk.hrapp.Result;
import com.witelokk.hrapp.api.model.Employee;

import java.util.Date;
import java.util.List;

public interface EmployeesRepository {
    LiveData<Result<List<Employee>>> getEmployeesByCompany(int companyId);

    LiveData<Result<List<Employee>>> getEmployeesByDepartment(int departmentId);

    LiveData<Result<Employee>> getEmployee(int employeeId, boolean includeActions);

    LiveData<Result<Void>> deleteEmployee(int employeeId);

    LiveData<Result<Void>> editEmployee(int employeeId, String name, String gender, Date birthdate, String inn, String snils, String address, String passportIssuer, String passportNumber, Date passportDate);

    LiveData<Result<Employee>> createEmployee(String name, String gender, Date birthdate, String inn, String snils, String address, String passportIssuer, String passportNumber, Date passportDate);
}
