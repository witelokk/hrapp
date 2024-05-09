package com.witelokk.hrapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.Error;
import com.witelokk.hrapp.api.CompaniesApi;
import com.witelokk.hrapp.api.model.Company;
import com.witelokk.hrapp.Result;
import com.witelokk.hrapp.api.model.CreateCompanyRequest;

import java.net.HttpURLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class CompaniesRepositoryImpl implements CompaniesRepository {
    CompaniesApi companiesApi;

    public CompaniesRepositoryImpl(CompaniesApi companiesApi) {
        this.companiesApi = companiesApi;
    }

    public LiveData<Result<List<Company>>> getCompanies() {
        MutableLiveData<Result<List<Company>>> resultLiveData = new MutableLiveData<>();

        companiesApi.getCompanies().enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(@NonNull Call<List<Company>> call, @NonNull retrofit2.Response<List<Company>> response) {
                if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    resultLiveData.setValue(Result.error(new Error.Unauthorized()));
                } else if (response.isSuccessful()) {
                    resultLiveData.setValue(Result.success(response.body()));
                } else {
                    resultLiveData.setValue(Result.error(new Error.Unknown()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Company>> call, @NonNull Throwable throwable) {
                resultLiveData.setValue(Result.error(new Error.Network()));
            }
        });

        return resultLiveData;
    }

    public LiveData<Result<Void>> addCompany(String name, String inn, String kpp) {
        MutableLiveData<Result<Void>> resultLiveData = new MutableLiveData<>();

        CreateCompanyRequest request = new CreateCompanyRequest(name, inn, kpp);
        companiesApi.createCompany(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull retrofit2.Response<Void> response) {
                if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    resultLiveData.setValue(Result.error(new Error.Unauthorized()));
                } else if (response.isSuccessful()) {
                    resultLiveData.setValue(Result.success(null));
                } else {
                    resultLiveData.setValue(Result.error(new Error.Unknown()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable throwable) {
                resultLiveData.setValue(Result.error(new Error.Unknown()));
            }
        });

        return resultLiveData;
    }
}
