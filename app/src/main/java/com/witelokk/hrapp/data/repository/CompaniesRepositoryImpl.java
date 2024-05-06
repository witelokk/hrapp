package com.witelokk.hrapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.api.CompaniesApi;
import com.witelokk.hrapp.api.model.Company;
import com.witelokk.hrapp.Result;
import com.witelokk.hrapp.api.model.CreateCompanyRequest;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompaniesRepositoryImpl implements CompaniesRepository {
    CompaniesApi companiesApi;

    public CompaniesRepositoryImpl(CompaniesApi companiesApi) {
        this.companiesApi = companiesApi;
    }

    public LiveData<Result<List<Company>>> getCompanies() {
        MutableLiveData<Result<List<Company>>> companiesLiveData = new MutableLiveData<>();

        companiesApi.getCompanies().enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, retrofit2.Response<List<Company>> response) {
                if (response.code() != 200) {
                    try {
                        companiesLiveData.setValue(new Result(response.errorBody().string()));
                    } catch (IOException e) {
                        companiesLiveData.setValue(new Result(e.getMessage()));
                    }
                    return;
                }

                companiesLiveData.setValue(new Result(response.body()));
            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable throwable) {
                companiesLiveData.setValue(new Result(throwable.getMessage()));
            }
        });

        return companiesLiveData;
    }

    public LiveData<Result<Void>> addCompany(String name, String inn, String kpp) {
        MutableLiveData<Result<Void>> responseLiveData = new MutableLiveData<>();

        CreateCompanyRequest request = new CreateCompanyRequest(name, inn, kpp);
        companiesApi.createCompany(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.code() != 201) {
                    responseLiveData.setValue(new Result<>("Can't create company!"));
                    return;
                }

                responseLiveData.setValue(new Result<>((Void)null));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                responseLiveData.setValue(new Result<>("Can't create company!"));
            }
        });

        return responseLiveData;
    }
}
