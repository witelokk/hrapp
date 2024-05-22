package com.witelokk.hrapp.api;

import com.witelokk.hrapp.api.model.CreateEmployeeRequest;
import com.witelokk.hrapp.api.model.EditEmployeeRequest;
import com.witelokk.hrapp.api.model.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EmployeesApi {
    @GET("/employees/company/{company_id}")
    Call<List<Employee>> getEmployeesByCompany(@Path("company_id") int companyId);

    @GET("/employees/department/{department_id}")
    Call<List<Employee>> getEmployeesByDepartment(@Path("department_id") int departmentId);

    @GET("/employees/{employee_id}")
    Call<Employee> getEmployee(@Path("employee_id") int employeeId, @Query("include_actions") boolean includeActions);

    @DELETE("/employees/{employee_id}")
    Call<Void> deleteEmployee(@Path("employee_id") int employeeId);

    @PATCH("/employees/{employee_id}")
    Call<Void> editEmployee(@Path("employee_id") int employeeId, @Body EditEmployeeRequest editEmployeeRequest);

    @POST("/employees/")
    Call<Void> createEmployee(@Body CreateEmployeeRequest createEmployeeRequest);
}
