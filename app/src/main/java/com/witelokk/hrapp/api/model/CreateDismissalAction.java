package com.witelokk.hrapp.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CreateDismissalAction {
    @SerializedName("action_type")
    private final String actionType = "dismissal";
    @SerializedName("dismissal_date")
    private Date dismissalDate;

    public Date getDismissalDate() {
        return dismissalDate;
    }

    public void setDismissalDate(Date dismissalDate) {
        this.dismissalDate = dismissalDate;
    }
}
