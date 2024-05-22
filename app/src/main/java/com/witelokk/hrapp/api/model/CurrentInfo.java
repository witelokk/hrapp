package com.witelokk.hrapp.api.model;

import com.google.gson.annotations.SerializedName;

public class CurrentInfo {
    @SerializedName("position")
    String position;

    @SerializedName("department")
    Department department;

    @SerializedName("salary")
    float salary;

    public CurrentInfo(String position, Department department, float salary) {
        this.position = position;
        this.department = department;
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public Department getDepartment() {
        return department;
    }

    public float getSalary() {
        return salary;
    }
}
