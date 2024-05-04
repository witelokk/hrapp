package com.witelokk.hrapp.api.model;

import com.google.gson.annotations.SerializedName;

public class CreateCompanyRequest {
    @SerializedName("name")
    private String name;
    @SerializedName("inn")
    private String inn;
    @SerializedName("kpp")
    private String kpp;

    public CreateCompanyRequest(String name, String inn, String kpp) {
        this.name = name;
        this.inn = inn;
        this.kpp = kpp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }
}
