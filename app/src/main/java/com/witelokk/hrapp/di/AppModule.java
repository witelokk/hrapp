package com.witelokk.hrapp.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.witelokk.hrapp.api.AuthApi;
import com.witelokk.hrapp.api.CompaniesApi;
import com.witelokk.hrapp.api.DepartmentsApi;
import com.witelokk.hrapp.api.EmployeesApi;
import com.witelokk.hrapp.data.repository.CompaniesRepository;
import com.witelokk.hrapp.data.repository.CompaniesRepositoryImpl;
import com.witelokk.hrapp.data.repository.DepartmentsRepository;
import com.witelokk.hrapp.data.repository.DepartmentsRepositoryImpl;
import com.witelokk.hrapp.data.repository.EmployeesRepository;
import com.witelokk.hrapp.data.repository.EmployeesRepositoryImpl;
import com.witelokk.hrapp.data.repository.LoginRepository;
import com.witelokk.hrapp.data.repository.LoginRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
        return new OkHttpClient.Builder().addInterceptor(loggingInterceptor).addInterceptor(authInterceptor).build();
    }

    @Provides
    @Singleton
    static Retrofit provideRetrofit(String baseUrl, OkHttpClient okHttpClient) {
        return new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
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
    static SharedPreferences provideSharedPreferences(Application application) {
        return application.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }


    @Provides
    @Singleton
    static Interceptor provideAuthInterceptor(SharedPreferences sharedPreferences) {
        return chain -> {
            Request originalRequest = chain.request();
            String accessToken = sharedPreferences.getString("access_token", null);
            if (accessToken != null) {
                Request newRequest = originalRequest.newBuilder().header("Authorization", "Bearer " + accessToken).build();
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
}
