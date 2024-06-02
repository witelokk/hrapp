package com.witelokk.hrapp.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.witelokk.hrapp.api.ActionsApi;
import com.witelokk.hrapp.api.AuthApi;
import com.witelokk.hrapp.api.CompaniesApi;
import com.witelokk.hrapp.api.DepartmentsApi;
import com.witelokk.hrapp.api.EmployeesApi;
import com.witelokk.hrapp.api.ReportsApi;
import com.witelokk.hrapp.api.model.Token;
import com.witelokk.hrapp.data.repository.ActionsRepository;
import com.witelokk.hrapp.data.repository.ActionsRepositoryImpl;
import com.witelokk.hrapp.data.repository.CompaniesRepository;
import com.witelokk.hrapp.data.repository.CompaniesRepositoryImpl;
import com.witelokk.hrapp.data.repository.DepartmentsRepository;
import com.witelokk.hrapp.data.repository.DepartmentsRepositoryImpl;
import com.witelokk.hrapp.data.repository.EmployeesRepository;
import com.witelokk.hrapp.data.repository.EmployeesRepositoryImpl;
import com.witelokk.hrapp.data.repository.LoginRepository;
import com.witelokk.hrapp.data.repository.LoginRepositoryImpl;
import com.witelokk.hrapp.data.repository.ReportsRepositoryImpl;
import com.witelokk.hrapp.data.repository.ReportsRepository;

import java.net.HttpURLConnection;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public abstract class AppModule {

    @Provides
    static String provideBaseUrl() {
        return "http://128.199.48.210/";
    }

    @Provides
    @Singleton
    static OkHttpClient provideOkHttpClient(Interceptor authInterceptor, HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder().addInterceptor(loggingInterceptor).addInterceptor(authInterceptor).retryOnConnectionFailure(true).build();
    }

    @Provides
    @Singleton
    static Gson provideGson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    }

    @Provides
    @Singleton
    static Retrofit provideRetrofit(String baseUrl, OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson)).baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
    }

    @Provides
    @Singleton
    static AuthApi provideAuthApi(Retrofit retrofit) {
        return retrofit.create(AuthApi.class);
    }

    @Provides
    @Singleton
    static CompaniesApi provideCompaniesApi(Retrofit retrofit) {
        return retrofit.create(CompaniesApi.class);
    }

    @Provides
    @Singleton
    static DepartmentsApi provideDepartmentsApi(Retrofit retrofit) {
        return retrofit.create(DepartmentsApi.class);
    }

    @Provides
    @Singleton
    static EmployeesApi provideEmployeesApi(Retrofit retrofit) {
        return retrofit.create(EmployeesApi.class);
    }

    @Provides
    @Singleton
    static ActionsApi provideActionsApi(Retrofit retrofit) {
        return retrofit.create(ActionsApi.class);
    }

    @Provides
    @Singleton
    static ReportsApi providReportsApi(Retrofit retrofit) {
        return retrofit.create(ReportsApi.class);
    }

    @Provides
    @Singleton
    static SharedPreferences provideSharedPreferences(Application application) {
        return application.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    static Interceptor provideAuthInterceptor(SharedPreferences sharedPreferences, String baseUrl) {
        return chain -> {
            Request originalRequest = chain.request();
            String accessToken = sharedPreferences.getString("access_token", null);
            Response response = null;

            if (accessToken != null) {
                Request newRequest = originalRequest.newBuilder().header("Authorization", "Bearer " + accessToken).build();
                response = chain.proceed(newRequest);
            }

            if (response != null && response.code() != HttpURLConnection.HTTP_UNAUTHORIZED) {
                return response;
            }

            // Try refreshing token
            String refreshToken = sharedPreferences.getString("refresh_token", null);

            if (refreshToken == null) {
                if (response == null) return chain.proceed(originalRequest);
                else return response;
            }

            if (response != null) response.close();

            Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
            AuthApi authApi = retrofit.create(AuthApi.class);
            retrofit2.Response<Token> tokenResponse = authApi.refreshToken("refresh_token", refreshToken).execute();
            if (tokenResponse.isSuccessful() && tokenResponse.body() != null) {
                sharedPreferences.edit().putString("access_token", tokenResponse.body().getAccessToken()).putString("refresh_token", tokenResponse.body().getRefreshToken()).apply();
                Request newRequest = originalRequest.newBuilder().header("Authorization", "Bearer " + tokenResponse.body().getAccessToken()).build();
                return chain.proceed(newRequest);
            }

            return chain.proceed(originalRequest);
        };
    }

    @Provides
    @Singleton
    static HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    @Provides
    @Singleton
    static CompaniesRepository provideCompaniesRepository(CompaniesApi companiesApi) {
        return new CompaniesRepositoryImpl(companiesApi);
    }

    @Provides
    @Singleton
    static LoginRepository provideLoginRepository(AuthApi authApi) {
        return new LoginRepositoryImpl(authApi);
    }

    @Provides
    @Singleton
    static DepartmentsRepository provideDepartmentsRepository(DepartmentsApi departmentsApi) {
        return new DepartmentsRepositoryImpl(departmentsApi);
    }

    @Provides
    @Singleton
    static EmployeesRepository provideEmployeesRepository(EmployeesApi employeesApi) {
        return new EmployeesRepositoryImpl(employeesApi);
    }

    @Provides
    @Singleton
    static ActionsRepository provideActionsRepository(ActionsApi actionsApi) {
        return new ActionsRepositoryImpl(actionsApi);
    }

    @Provides
    @Singleton
    static ReportsRepository provideReportsRepository(ReportsApi reportsApi) {
        return new ReportsRepositoryImpl(reportsApi);
    }
}
