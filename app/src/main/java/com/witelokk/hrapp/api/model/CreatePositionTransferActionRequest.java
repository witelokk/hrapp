package com.witelokk.hrapp.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CreatePositionTransferActionRequest {
    @SerializedName("action_type")
    private final String actionType = "position_transfer";

    @SerializedName("transfer_date")
    private Date transferDate;

    @SerializedName("new_position")
    private String newPosition;

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public String getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(String newPosition) {
        this.newPosition = newPosition;
    }
}