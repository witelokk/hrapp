package com.witelokk.hrapp.api.model;

import com.google.gson.annotations.SerializedName;

public class CreateUserRequest {
    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;

    public CreateUserRequest(String username, String password) {
        this.email = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
