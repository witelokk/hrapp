package com.witelokk.hrapp.api.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class CreateEmployeeRequest {
    @SerializedName("name")
    @NonNull
    String name;

    public CreateEmployeeRequest(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
