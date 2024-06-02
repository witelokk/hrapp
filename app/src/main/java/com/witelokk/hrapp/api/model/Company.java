package com.witelokk.hrapp.api.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class Company implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    @NonNull
    private String name;
    @SerializedName("inn")
    @NonNull
    private String inn;
    @SerializedName("kpp")
    @NonNull
    private String kpp;

    public Company(int id, String name, String inn, String kpp) {
        this.id = id;
        this.name = name;
        this.inn = inn;
        this.kpp = kpp;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInn() {
        return inn;
    }

    public String getKpp() {
        return kpp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return id == company.id && Objects.equals(name, company.name) && Objects.equals(inn, company.inn) && Objects.equals(kpp, company.kpp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, inn, kpp);
    }
}
