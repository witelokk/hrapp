package com.witelokk.hrapp.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CreateRecruitmentActionRequest {
    @SerializedName("action_type")
    private final String actionType = "recruitment";

    @SerializedName("date")
    private Date date;

    @SerializedName("department_id")
    private int departmentId;

    @SerializedName("position")
    private String position;

    @SerializedName("salary")
    private float salary;

    public CreateRecruitmentActionRequest(Date date, int departmentId, String position, float salary) {
        this.date = date;
        this.departmentId = departmentId;
        this.position = position;
        this.salary = salary;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }
}
