package com.witelokk.hrapp.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CreateDepartmentTransferActionRequest {
    @SerializedName("action_type")
    private final String actionType = "department_transfer";
    @SerializedName("transfer_date")
    private Date transferDate;
    @SerializedName("new_department_id")
    private int newDepartmentId;

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public int getNewDepartmentId() {
        return newDepartmentId;
    }

    public void setNewDepartmentId(int newDepartmentId) {
        this.newDepartmentId = newDepartmentId;
    }
}
