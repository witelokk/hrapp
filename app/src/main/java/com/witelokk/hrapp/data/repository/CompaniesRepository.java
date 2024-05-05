package com.witelokk.hrapp.data.repository;

import androidx.lifecycle.LiveData;

import com.witelokk.hrapp.api.model.Company;
import com.witelokk.hrapp.Result;

import java.util.List;

public interface CompaniesRepository {
    LiveData<Result<List<Company>>> getCompanies();

    LiveData<Result<Void>> addCompany(String name, String inn, String kpp);
}
