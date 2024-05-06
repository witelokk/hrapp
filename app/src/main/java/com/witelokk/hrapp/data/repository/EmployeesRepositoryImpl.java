package com.witelokk.hrapp.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.Result;
import com.witelokk.hrapp.api.EmployeesApi;
import com.witelokk.hrapp.api.model.CreateEmployeeRequest;
import com.witelokk.hrapp.api.model.EditEmployeeRequest;
import com.witelokk.hrapp.api.model.Employee;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class EmployeesRepositoryImpl implements EmployeesRepository {

    private final EmployeesApi employeesApi;

    public EmployeesRepositoryImpl(EmployeesApi employeesApi) {
        this.employeesApi = employeesApi;
    }

    @Override
    public LiveData<Result<List<Employee>>> getEmployeesByCompany(int companyId) {
        MutableLiveData<Result<List<Employee>>> resultLiveData = new MutableLiveData<>();
        employeesApi.getEmployeesByCompany(companyId).enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.isSuccessful()) {
                    resultLiveData.setValue(new Result<>(response.body()));
                } else {
                    try {
                        resultLiveData.setValue(new Result<>(response.errorBody().string()));
                    } catch (IOException e) {
                        resultLiveData.setValue(new Result<>("Unknown error occurred"));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                resultLiveData.setValue(new Result<>("Network error occurred"));
            }
        });
        return resultLiveData;
    }

    @Override
    public LiveData<Result<List<Employee>>> getEmployeesByDepartment(int departmentId) {
        MutableLiveData<Result<List<Employee>>> resultLiveData = new MutableLiveData<>();
        employeesApi.getEmployeesByDepartment(departmentId).enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.isSuccessful()) {
                    resultLiveData.setValue(new Result<>(response.body()));
                } else {
                    try {
                        resultLiveData.setValue(new Result<>(response.errorBody().string()));
                    } catch (IOException e) {
                        resultLiveData.setValue(new Result<>("Unknown error occurred"));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                resultLiveData.setValue(new Result<>("Network error occurred"));
            }
        });
        return resultLiveData;
    }

    @Override
    public LiveData<Result<Employee>> getEmployee(int employeeId) {
        MutableLiveData<Result<Employee>> resultLiveData = new MutableLiveData<>();
        employeesApi.getEmployee(employeeId).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.isSuccessful()) {
                    resultLiveData.setValue(new Result<>(response.body()));
                } else {
                    try {
                        resultLiveData.setValue(new Result<>(response.errorBody().string()));
                    } catch (IOException e) {
                        resultLiveData.setValue(new Result<>("Unknown error occurred"));
                    }
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                resultLiveData.setValue(new Result<>("Network error occurred"));
            }
        });
        return resultLiveData;
    }

    @Override
    public LiveData<Result<Void>> deleteEmployee(int employeeId) {
        MutableLiveData<Result<Void>> resultLiveData = new MutableLiveData<>();
        employeesApi.deleteEmployee(employeeId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    resultLiveData.setValue(new Result<>(null));
                } else {
                    try {
                        resultLiveData.setValue(new Result<>(response.errorBody().string()));
                    } catch (IOException e) {
                        resultLiveData.setValue(new Result<>("Unknown error occurred"));
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                resultLiveData.setValue(new Result<>("Network error occurred"));
            }
        });
        return resultLiveData;
    }

    @Override
    public LiveData<Result<Void>> editEmployee(int employeeId, String name) {
        MutableLiveData<Result<Void>> resultLiveData = new MutableLiveData<>();
        employeesApi.editEmployee(employeeId, new EditEmployeeRequest(name)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    resultLiveData.setValue(new Result<>(null));
                } else {
                    try {
                        resultLiveData.setValue(new Result<>(response.errorBody().string()));
                    } catch (IOException e) {
                        resultLiveData.setValue(new Result<>("Unknown error occurred"));
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                resultLiveData.setValue(new Result<>("Network error occurred"));
            }
        });
        return resultLiveData;
    }

    @Override
    public LiveData<Result<Void>> createEmployee(String name) {
        MutableLiveData<Result<Void>> resultLiveData = new MutableLiveData<>();
        employeesApi.createEmployee(new CreateEmployeeRequest(name)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    resultLiveData.setValue(new Result<>(null));
                } else {
                    try {
                        resultLiveData.setValue(new Result<>(response.errorBody().string()));
                    } catch (IOException e) {
                        resultLiveData.setValue(new Result<>("Unknown error occurred"));
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                resultLiveData.setValue(new Result<>("Network error occurred"));
            }
        });
        return resultLiveData;
    }
}
