package com.witelokk.hrapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.Error;
import com.witelokk.hrapp.Result;
import com.witelokk.hrapp.api.ReportsApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportsRepositoryImpl implements ReportsRepository {
    private final ReportsApi reportsApi;

    public ReportsRepositoryImpl(ReportsApi reportsApi) {
        this.reportsApi = reportsApi;
    }

    @Override
    public LiveData<Result<ResponseBody>> getReport(int companyId) {
        MutableLiveData<Result<ResponseBody>> resultLiveData = new MutableLiveData<>();

        reportsApi.generateReport(companyId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    resultLiveData.setValue(Result.success(response.body()));
                } else {
                    resultLiveData.setValue(Result.error(new Error.Unknown()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable throwable) {
                resultLiveData.setValue(Result.error(new Error.Network()));
            }
        });

        return resultLiveData;
    }
}
