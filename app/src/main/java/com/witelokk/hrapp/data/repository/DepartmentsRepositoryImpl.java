package com.witelokk.hrapp.data.repository;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.Error;
import com.witelokk.hrapp.Result;
import com.witelokk.hrapp.api.DepartmentsApi;
import com.witelokk.hrapp.api.model.CreateDepartmentRequest;
import com.witelokk.hrapp.api.model.Department;
import com.witelokk.hrapp.api.model.EditDepartmentRequest;

import java.net.HttpURLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DepartmentsRepositoryImpl implements DepartmentsRepository {
    private final DepartmentsApi departmentApi;


    public DepartmentsRepositoryImpl(DepartmentsApi departmentApi) {
        this.departmentApi = departmentApi;
    }

    @Override
    public LiveData<Result<Department>> getDepartment(int departmentId) {
        MutableLiveData<Result<Department>> resultLiveData = new MutableLiveData<>();
        departmentApi.getDepartment(departmentId).enqueue(new Callback<Department>() {
            @Override
            public void onResponse(@NonNull Call<Department> call, @NonNull Response<Department> response) {
                if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    resultLiveData.setValue(Result.error(new Error.Unauthorized()));
                } else if (response.isSuccessful()) {
                    resultLiveData.setValue(Result.success(response.body()));
                } else {
                    resultLiveData.setValue(Result.error(new Error.Unknown()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Department> call, @NonNull Throwable t) {
                resultLiveData.setValue(Result.error(new Error.Network()));
            }
        });
        return resultLiveData;
    }

    @Override
    public LiveData<Result<List<Department>>> getDepartmentsByCompany(int companyId) {
        MutableLiveData<Result<List<Department>>> resultLiveData = new MutableLiveData<>();
        departmentApi.getDepartmentsByCompany(companyId).enqueue(new Callback<List<Department>>() {
            @Override
            public void onResponse(@NonNull Call<List<Department>> call, @NonNull Response<List<Department>> response) {
                if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    resultLiveData.setValue(Result.error(new Error.Unauthorized()));
                } else if (response.isSuccessful()) {
                    resultLiveData.setValue(Result.success(response.body()));
                } else {
                    resultLiveData.setValue(Result.error(new Error.Unknown()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Department>> call, @NonNull Throwable t) {
                resultLiveData.setValue(Result.error(new Error.Network()));
            }
        });
        return resultLiveData;
    }

    @Override
    public LiveData<Result<Void>> createDepartment(String name, int companyId) {
        MutableLiveData<Result<Void>> resultLiveData = new MutableLiveData<>();
        departmentApi.createDepartment(new CreateDepartmentRequest(name, companyId)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    resultLiveData.setValue(Result.error(new Error.Unauthorized()));
                } else if (response.isSuccessful()) {
                    resultLiveData.setValue(Result.success());
                } else {
                    resultLiveData.setValue(Result.error(new Error.Unknown()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                resultLiveData.setValue(Result.error(new Error.Network()));
            }
        });
        return resultLiveData;
    }

    @Override
    public LiveData<Result<Void>> editDepartment(int departmentId, String name, int companyId) {
        MutableLiveData<Result<Void>> resultLiveData = new MutableLiveData<>();
        departmentApi.editDepartment(departmentId, new EditDepartmentRequest(name, companyId)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    resultLiveData.setValue(Result.error(new Error.Unauthorized()));
                } else if (response.isSuccessful()) {
                    resultLiveData.setValue(Result.success(response.body()));
                } else {
                    resultLiveData.setValue(Result.error(new Error.Unknown()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                resultLiveData.setValue(Result.error(new Error.Network()));
            }
        });
        return resultLiveData;
    }

    @Override
    public LiveData<Result<Void>> deleteDepartment(int departmentId) {
        MutableLiveData<Result<Void>> resultLiveData = new MutableLiveData<>();
        departmentApi.deleteDepartment(departmentId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    resultLiveData.setValue(Result.error(new Error.Unauthorized()));
                } else if (response.isSuccessful()) {
                    resultLiveData.setValue(Result.success(response.body()));
                } else {
                    resultLiveData.setValue(Result.error(new Error.Unknown()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                resultLiveData.setValue(Result.error(new Error.Network()));
            }
        });
        return resultLiveData;
    }
}
