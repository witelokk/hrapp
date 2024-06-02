package com.witelokk.hrapp.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CreateSalaryChangeAction {
    @SerializedName("action_type")
    private final String actionType = "salary_change";

    @SerializedName("date")
    private Date date;

    @SerializedName("new_salary")
    private float newSalary;

    public CreateSalaryChangeAction(Date date, float newSalary) {
        this.date = date;
        this.newSalary = newSalary;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getNewSalary() {
        return newSalary;
    }

    public void setNewSalary(float newSalary) {
        this.newSalary = newSalary;
    }
}