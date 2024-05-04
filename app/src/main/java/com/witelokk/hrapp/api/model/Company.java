package com.witelokk.hrapp.api.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Company {
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
}
