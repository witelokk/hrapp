package com.witelokk.hrapp.api.model;

import com.google.gson.annotations.SerializedName;

public class EditDepartmentRequest {
    @SerializedName("name")
    private String name;
    @SerializedName("company_id")
    private int companyId;

    public EditDepartmentRequest(String name, int companyId) {
        this.name = name;
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public int getCompanyId() {
        return companyId;
    }
}