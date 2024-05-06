package com.witelokk.hrapp.api.model;

import com.google.gson.annotations.SerializedName;

public class Employee {
    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("current_info")
    CurrentInfo currentInfo;

    public Employee(int id, String name, CurrentInfo currentInfo) {
        this.id = id;
        this.name = name;
        this.currentInfo = currentInfo;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CurrentInfo getCurrentInfo() {
        return currentInfo;
    }
}
