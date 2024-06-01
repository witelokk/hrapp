package com.witelokk.hrapp.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CreateDepartmentTransferActionRequest {
    @SerializedName("action_type")
    private final String actionType = "department_transfer";
    @SerializedName("date")
    private Date date;
    @SerializedName("new_department_id")
    private int newDepartmentId;

    public CreateDepartmentTransferActionRequest(Date date, int newDepartmentId) {
        this.date = date;
        this.newDepartmentId = newDepartmentId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNewDepartmentId() {
        return newDepartmentId;
    }

    public void setNewDepartmentId(int newDepartmentId) {
        this.newDepartmentId = newDepartmentId;
    }
}
