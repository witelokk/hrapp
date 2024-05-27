package com.witelokk.hrapp.api;

import com.witelokk.hrapp.api.model.Company;
import com.witelokk.hrapp.api.model.CreateCompanyRequest;
import com.witelokk.hrapp.api.model.EditCompanyRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CompaniesApi {
    @GET("/companies")
    Call<List<Company>> getCompanies();

    @GET("/companies/{company_id}")
    Call<Company> getCompany(@Path("company_id") int companyId);

    @POST("/companies")
    Call<Void> createCompany(@Body CreateCompanyRequest createCompanyRequest);

    @PATCH("/companies/{company_id}")
    Call<Void> editCompany(@Path("company_id") int companyId, @Body EditCompanyRequest editCompanyRequest);

    @DELETE("/companies/{company_id}")
    Call<Void> deleteCompany(@Path("company_id") int companyId);
}
