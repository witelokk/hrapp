package com.witelokk.hrapp.api;

import com.witelokk.hrapp.api.model.CreateDepartmentRequest;
import com.witelokk.hrapp.api.model.Department;
import com.witelokk.hrapp.api.model.EditDepartmentRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DepartmentsApi {
    @GET("/departments/{department_id}")
    Call<Department> getDepartment(@Path("department_id") int departmentId);

    @PATCH("/departments/{department_id}")
    Call<Void> editDepartment(@Path("department_id") int departmentId, @Body EditDepartmentRequest request);

    @DELETE("/departments/{department_id}")
    Call<Void> deleteDepartment(@Path("department_id") int departmentId);

    @GET("/departments/company/{company_id}")
    Call<List<Department>> getDepartmentsByCompany(@Path("company_id") int companyId);

    @POST("/departments/")
    Call<Department> createDepartment(@Body CreateDepartmentRequest request);
}
