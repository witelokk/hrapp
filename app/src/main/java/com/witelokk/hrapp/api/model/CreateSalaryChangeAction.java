package com.witelokk.hrapp.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CreateSalaryChangeAction {
    @SerializedName("action_type")
    private final String actionType = "dismissal";

    @SerializedName("change_date")
    private Date changeDate;

    @SerializedName("new_salary")
    private float newSalary;

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public float getNewSalary() {
        return newSalary;
    }

    public void setNewSalary(float newSalary) {
        this.newSalary = newSalary;
    }
}