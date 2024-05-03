package com.witelokk.hrapp.api.model;

import com.google.gson.annotations.SerializedName;

public class GetTokenRequest {
    @SerializedName("username")
    String username;
    @SerializedName("password")
    String password;

    public GetTokenRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
