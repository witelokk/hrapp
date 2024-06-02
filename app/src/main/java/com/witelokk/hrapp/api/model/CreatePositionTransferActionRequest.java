package com.witelokk.hrapp.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CreatePositionTransferActionRequest {
    @SerializedName("action_type")
    private final String actionType = "position_transfer";

    @SerializedName("date")
    private Date date;

    @SerializedName("new_position")
    private String newPosition;

    public CreatePositionTransferActionRequest(Date date, String newPosition) {
        this.date = date;
        this.newPosition = newPosition;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(String newPosition) {
        this.newPosition = newPosition;
    }
}