package com.witelokk.hrapp.api.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class EditEmployeeRequest {
    @SerializedName("name")
    String name;

    @SerializedName("gender")
    String gender;

    @SerializedName("birthdate")
    Date birthdate;

    @SerializedName("inn")
    String inn;

    @SerializedName("snils")
    String snils;

    @SerializedName("address")
    String address;

    @SerializedName("passport_issuer")
    String passportIssuer;

    @SerializedName("passport_number")
    String passportNumber;

    @SerializedName("passport_date")
    Date passportDate;

    public EditEmployeeRequest(String name, String gender, Date birthdate, String inn, String snils, String address, String passportIssuer, String passportNumber, Date passportDate) {
        this.name = name;
        this.gender = gender;
        this.birthdate = birthdate;
        this.inn = inn;
        this.snils = snils;
        this.address = address;
        this.passportIssuer = passportIssuer;
        this.passportNumber = passportNumber;
        this.passportDate = passportDate;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getGender() {
        return gender;
    }

    public void setGender(@NonNull String gender) {
        this.gender = gender;
    }

    @NonNull
    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(@NonNull Date birthdate) {
        this.birthdate = birthdate;
    }

    @NonNull
    public String getInn() {
        return inn;
    }

    public void setInn(@NonNull String inn) {
        this.inn = inn;
    }

    @NonNull
    public String getSnils() {
        return snils;
    }

    public void setSnils(@NonNull String snils) {
        this.snils = snils;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    @NonNull
    public String getPassportIssuer() {
        return passportIssuer;
    }

    public void setPassportIssuer(@NonNull String passportIssuer) {
        this.passportIssuer = passportIssuer;
    }

    @NonNull
    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(@NonNull String passportNumber) {
        this.passportNumber = passportNumber;
    }

    @NonNull
    public Date getPassportDate() {
        return passportDate;
    }

    public void setPassportDate(@NonNull Date passportDate) {
        this.passportDate = passportDate;
    }}
