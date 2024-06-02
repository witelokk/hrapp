package com.witelokk.hrapp.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return id == that.id && companyId == that.companyId && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, companyId);
    }
}