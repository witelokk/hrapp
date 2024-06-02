package com.witelokk.hrapp.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CreateDismissalAction {
    @SerializedName("action_type")
    private final String actionType = "dismissal";
    @SerializedName("date")
    private Date date;

    public CreateDismissalAction(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
