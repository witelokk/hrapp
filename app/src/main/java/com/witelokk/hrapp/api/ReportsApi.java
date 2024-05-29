package com.witelokk.hrapp.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ReportsApi {
    @GET("/reports/company/{company_id}")
    Call<ResponseBody> generateReport(@Path("company_id") int companyId);
}
