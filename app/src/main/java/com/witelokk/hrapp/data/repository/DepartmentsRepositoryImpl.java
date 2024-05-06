package com.witelokk.hrapp.data.repository;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.Result;
import com.witelokk.hrapp.api.DepartmentsApi;
import com.witelokk.hrapp.api.model.CreateDepartmentRequest;
import com.witelokk.hrapp.api.model.Department;
import com.witelokk.hrapp.api.model.EditDepartmentRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DepartmentsRepositoryImpl implements DepartmentsRepository {
    private DepartmentsApi departmentApi;


    public DepartmentsRepositoryImpl(DepartmentsApi departmentApi) {
        this.departmentApi = departmentApi;
    }

    @Override
    public LiveData<Result<Department>> getDepartment(int departmentId) {
        MutableLiveData<Result<Department>> departmentLiveData = new MutableLiveData<>();
        departmentApi.getDepartment(departmentId).enqueue(new Callback<Department>() {
            @Override
            public void onResponse(Call<Department> call, Response<Department> response) {
                if (response.isSuccessful()) {
                    departmentLiveData.setValue(new Result<>(response.body()));
                } else {
                    departmentLiveData.setValue(new Result<>("Error fetching department"));
                }
            }

            @Override
            public void onFailure(Call<Department> call, Throwable t) {
                departmentLiveData.setValue(new Result<>("Network error"));
            }
        });
        return departmentLiveData;
    }

    @Override
    public LiveData<Result<List<Department>>> getDepartmentsByCompany(int companyId) {
        MutableLiveData<Result<List<Department>>> departmentsLiveData = new MutableLiveData<>();
        departmentApi.getDepartmentsByCompany(companyId).enqueue(new Callback<List<Department>>() {
            @Override
            public void onResponse(Call<List<Department>> call, Response<List<Department>> response) {
                if (response.isSuccessful()) {
                    departmentsLiveData.setValue(new Result<>(response.body()));
                } else {
                    departmentsLiveData.setValue(new Result<>("Error fetching departments"));
                }
            }

            @Override
            public void onFailure(Call<List<Department>> call, Throwable t) {
                departmentsLiveData.setValue(new Result<>("Network error"));
            }
        });
        return departmentsLiveData;
    }

    @Override
    public LiveData<Result<Boolean>> createDepartment(String name, int companyId) {
        MutableLiveData<Result<Boolean>> resultLiveData = new MutableLiveData<>();
        departmentApi.createDepartment(new CreateDepartmentRequest(name, companyId)).enqueue(new Callback<Department>() {
            @Override
            public void onResponse(Call<Department> call, Response<Department> response) {
                if (response.isSuccessful()) {
                    resultLiveData.setValue(new Result<>(true));
                } else {
                    resultLiveData.setValue(new Result<>("Error creating department"));
                }
            }

            @Override
            public void onFailure(Call<Department> call, Throwable t) {
                resultLiveData.setValue(new Result<>("Network error"));
            }
        });
        return resultLiveData;
    }

    @Override
    public LiveData<Result<Boolean>> editDepartment(int departmentId, String name, int companyId) {
        MutableLiveData<Result<Boolean>> resultLiveData = new MutableLiveData<>();
        departmentApi.editDepartment(departmentId, new EditDepartmentRequest(name, companyId)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    resultLiveData.setValue(new Result<>(true));
                } else {
                    resultLiveData.setValue(new Result<>("Error editing department"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                resultLiveData.setValue(new Result<>("Network error"));
            }
        });
        return resultLiveData;
    }

    @Override
    public LiveData<Result<Boolean>> deleteDepartment(int departmentId) {
        MutableLiveData<Result<Boolean>> resultLiveData = new MutableLiveData<>();
        departmentApi.deleteDepartment(departmentId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    resultLiveData.setValue(new Result<>(true));
                } else {
                    resultLiveData.setValue(new Result<>("Error deleting department"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                resultLiveData.setValue(new Result<>("Network error"));
            }
        });
        return resultLiveData;
    }
}
