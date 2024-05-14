package com.witelokk.hrapp.api.model;

import com.google.gson.annotations.SerializedName;

public class Token {
    @SerializedName("access_token")
    String accessToken;
    @SerializedName("refresh_token")
    String refreshToken;
    @SerializedName("token_type")
    String tokenType;

    public Token(String accessToken, String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }
}
