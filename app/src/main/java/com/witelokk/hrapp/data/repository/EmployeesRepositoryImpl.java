package com.witelokk.hrapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.Error;
import com.witelokk.hrapp.Result;
import com.witelokk.hrapp.api.EmployeesApi;
import com.witelokk.hrapp.api.model.CreateEmployeeRequest;
import com.witelokk.hrapp.api.model.Employee;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.HttpURLConnection;
import java.util.Date;
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
            public void onResponse(@NonNull Call<List<Employee>> call, @NonNull Response<List<Employee>> response) {
                if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    resultLiveData.setValue(Result.error(new Error.Unauthorized()));
                } else if (response.isSuccessful()) {
                    resultLiveData.setValue(Result.success(response.body()));
                } else {
                    resultLiveData.setValue(Result.error(new Error.Unknown()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Employee>> call, @NonNull Throwable t) {
                resultLiveData.setValue(Result.error(new Error.Network()));
            }
        });
        return resultLiveData;
    }

    @Override
    public LiveData<Result<List<Employee>>> getEmployeesByDepartment(int departmentId) {
        MutableLiveData<Result<List<Employee>>> resultLiveData = new MutableLiveData<>();
        employeesApi.getEmployeesByDepartment(departmentId).enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(@NonNull Call<List<Employee>> call, @NonNull Response<List<Employee>> response) {
                if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    resultLiveData.setValue(Result.error(new Error.Unauthorized()));
                } else if (response.isSuccessful()) {
                    resultLiveData.setValue(Result.success(response.body()));
                } else {
                    resultLiveData.setValue(Result.error(new Error.Unknown()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Employee>> call, @NonNull Throwable t) {
                resultLiveData.setValue(Result.error(new Error.Network()));
            }
        });
        return resultLiveData;
    }

    @Override
    public LiveData<Result<Employee>> getEmployee(int employeeId, boolean includeActions) {
        MutableLiveData<Result<Employee>> resultLiveData = new MutableLiveData<>();
        employeesApi.getEmployee(employeeId, includeActions).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(@NonNull Call<Employee> call, @NonNull Response<Employee> response) {
                if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    resultLiveData.setValue(Result.error(new Error.Unauthorized()));
                } else if (response.isSuccessful()) {
                    resultLiveData.setValue(Result.success(response.body()));
                } else {
                    resultLiveData.setValue(Result.error(new Error.Unknown()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Employee> call, @NonNull Throwable t) {
                resultLiveData.setValue(Result.error(new Error.Network()));
            }
        });
        return resultLiveData;
    }

    @Override
    public LiveData<Result<Void>> deleteEmployee(int employeeId) {
        MutableLiveData<Result<Void>> resultLiveData = new MutableLiveData<>();
        employeesApi.deleteEmployee(employeeId).enqueue(new Callback<Void>() {
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
    public LiveData<Result<Void>> editEmployee(int employeeId, String name, String gender, Date birthdate, String inn, String snils, String address, String passportIssuer, String passportNumber, Date passportDate) {
        MutableLiveData<Result<Void>> resultLiveData = new MutableLiveData<>();
        CreateEmployeeRequest createEmployeeRequest = new CreateEmployeeRequest(name, gender, birthdate, inn, snils, address, passportIssuer, passportNumber, passportDate);
        employeesApi.editEmployee(employeeId, createEmployeeRequest).enqueue(new Callback<Void>() {
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
    public LiveData<Result<Employee>> createEmployee(String name, String gender, Date birthdate, String inn, String snils, String address, String passportIssuer, String passportNumber, Date passportDate) {
        MutableLiveData<Result<Employee>> resultLiveData = new MutableLiveData<>();

        CreateEmployeeRequest createEmployeeRequest = new CreateEmployeeRequest(name, gender, birthdate, inn, snils, address, passportIssuer, passportNumber, passportDate);
        employeesApi.createEmployee(createEmployeeRequest).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(@NonNull Call<Employee> call, @NonNull Response<Employee> response) {
                if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    resultLiveData.setValue(Result.error(new Error.Unauthorized()));
                } else if (response.isSuccessful()) {
                    resultLiveData.setValue(Result.success(response.body()));
                } else {
                    resultLiveData.setValue(Result.error(new Error.Unknown()));
                }

            }

            @Override
            public void onFailure(@NonNull Call<Employee> call, @NonNull Throwable t) {
                resultLiveData.setValue(Result.error(new Error.Network()));
            }
        });
        return resultLiveData;
    }
}
