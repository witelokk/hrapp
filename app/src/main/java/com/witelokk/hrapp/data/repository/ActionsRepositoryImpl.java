package com.witelokk.hrapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.Error;
import com.witelokk.hrapp.Result;
import com.witelokk.hrapp.api.ActionsApi;
import com.witelokk.hrapp.api.model.Action;
import com.witelokk.hrapp.api.model.CreateDepartmentTransferActionRequest;
import com.witelokk.hrapp.api.model.CreatePositionTransferActionRequest;
import com.witelokk.hrapp.api.model.CreateRecruitmentActionRequest;
import com.witelokk.hrapp.api.model.CreateSalaryChangeAction;

import java.net.HttpURLConnection;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActionsRepositoryImpl implements ActionsRepository {
    private final ActionsApi actionsApi;

    public ActionsRepositoryImpl(ActionsApi actionsApi) {
        this.actionsApi = actionsApi;
    }

    public LiveData<Result<List<Action>>> getActionsByEmployee(int employeeId) {
        MutableLiveData<Result<List<Action>>> resultLiveData = new MutableLiveData<>();

        actionsApi.getActionsByEmployee(employeeId).enqueue(new Callback<List<Action>>() {
            @Override
            public void onResponse(@NonNull Call<List<Action>> call, @NonNull retrofit2.Response<List<Action>> response) {
                if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    resultLiveData.setValue(Result.error(new Error.Unauthorized()));
                } else if (response.isSuccessful()) {
                    resultLiveData.setValue(Result.success(response.body()));
                } else {
                    resultLiveData.setValue(Result.error(new Error.Unknown()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Action>> call, @NonNull Throwable throwable) {
                resultLiveData.setValue(Result.error(new Error.Network()));
            }
        });

        return resultLiveData;
    }

    @Override
    public LiveData<Result<Void>> createRecruitmentAction(int employeeId, int departmentId, Date recruitmentDate, String position, float salary) {
        MutableLiveData<Result<Void>> resultLiveData = new MutableLiveData<>();

        CreateRecruitmentActionRequest createRecruitmentActionRequest = new CreateRecruitmentActionRequest(recruitmentDate, departmentId, position, salary);
        actionsApi.createAction(employeeId, createRecruitmentActionRequest).enqueue(new Callback<Void>() {
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
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable throwable) {
                resultLiveData.setValue(Result.error(new Error.Network()));
            }
        });

        return resultLiveData;
    }

    public LiveData<Result<Void>> createDepartmentTransferAction(int employeeId, int newDepartmentId, Date date) {
        MutableLiveData<Result<Void>> resultLiveData = new MutableLiveData<>();

        CreateDepartmentTransferActionRequest createDepartmentActionRequest = new CreateDepartmentTransferActionRequest(date, newDepartmentId);
        actionsApi.createAction(employeeId, createDepartmentActionRequest).enqueue(new Callback<Void>() {
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
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable throwable) {
                resultLiveData.setValue(Result.error(new Error.Network()));
            }
        });

        return resultLiveData;
    }

    public LiveData<Result<Void>> createPositionTransferAction(int employeeId, String newPosition, Date date) {
        MutableLiveData<Result<Void>> resultLiveData = new MutableLiveData<>();

        CreatePositionTransferActionRequest createPositionTransferActionRequest = new CreatePositionTransferActionRequest(date, newPosition);
        actionsApi.createAction(employeeId, createPositionTransferActionRequest).enqueue(new Callback<Void>() {
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
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable throwable) {
                resultLiveData.setValue(Result.error(new Error.Network()));
            }
        });

        return resultLiveData;
    }

    public LiveData<Result<Void>> createSalaryChangeAction(int employeeId, float newSalary, Date date) {
        MutableLiveData<Result<Void>> resultLiveData = new MutableLiveData<>();

        CreateSalaryChangeAction createSalaryChangeAction = new CreateSalaryChangeAction(date, newSalary);
        actionsApi.createAction(employeeId, createSalaryChangeAction).enqueue(new Callback<Void>() {
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
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable throwable) {
                resultLiveData.setValue(Result.error(new Error.Network()));
            }
        });

        return resultLiveData;
    }

    public LiveData<Result<Void>> deleteAction(int actionId) {
        MutableLiveData<Result<Void>> resultLiveData = new MutableLiveData<>();

        actionsApi.deleteAction(actionId).enqueue(new Callback<Void>() {
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
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable throwable) {
                resultLiveData.setValue(Result.error(new Error.Network()));
            }
        });

        return resultLiveData;
    }
}
