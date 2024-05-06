package com.witelokk.hrapp.api.model;

import com.google.gson.annotations.SerializedName;

public class CurrentInfo {
    @SerializedName("position")
    String position;

    @SerializedName("department_id")
    int departmentId;

    @SerializedName("salary")
    float salary;

    public CurrentInfo(String position, int departmentId, float salary) {
        this.position = position;
        this.departmentId = departmentId;
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public float getSalary() {
        return salary;
    }
}
