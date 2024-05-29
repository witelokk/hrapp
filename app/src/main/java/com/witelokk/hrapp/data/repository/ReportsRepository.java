package com.witelokk.hrapp.data.repository;

import androidx.lifecycle.LiveData;

import com.witelokk.hrapp.Result;

import okhttp3.ResponseBody;

public interface ReportsRepository {
    LiveData<Result<ResponseBody>> getReport(int companyId);
}
