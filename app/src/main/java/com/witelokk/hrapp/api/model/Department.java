package com.witelokk.hrapp.api.model;

import com.google.gson.annotations.SerializedName;

public class Department {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("company_id")
    private int companyId;

    public Department(int id, String name, int companyId) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCompanyId() {
        return companyId;
    }
}