package com.witelokk.hrapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.Error;
import com.witelokk.hrapp.Result;
import com.witelokk.hrapp.api.ActionsApi;
import com.witelokk.hrapp.api.model.Action;

import java.net.HttpURLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

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
}
