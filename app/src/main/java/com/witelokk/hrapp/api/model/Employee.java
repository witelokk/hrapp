package com.witelokk.hrapp.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Employee implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("gender")
    private String gender;

    @SerializedName("current_info")
    private CurrentInfo currentInfo;

    @SerializedName("birthdate")
    private Date birthdate;

    @SerializedName("inn")
    private String inn;

    @SerializedName("snils")
    private String snils;

    @SerializedName("address")
    private String address;

    @SerializedName("passport_number")
    private String passportNumber;

    @SerializedName("passport_date")
    private Date passportDate;

    @SerializedName("passport_issuer")
    private String passwordIssuer;

    @SerializedName("actions")
    private List<Action> actions;

    public Employee(int id, String name, String gender, CurrentInfo currentInfo, Date birthdate, String inn, String snils, String address, String passportNumber, Date passportDate, String passwordIssuer, List<Action> actions) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.currentInfo = currentInfo;
        this.birthdate = birthdate;
        this.inn = inn;
        this.snils = snils;
        this.address = address;
        this.passportNumber = passportNumber;
        this.passportDate = passportDate;
        this.passwordIssuer = passwordIssuer;
        this.actions = actions;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public CurrentInfo getCurrentInfo() {
        return currentInfo;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getInn() {
        return inn;
    }

    public String getSnils() {
        return snils;
    }

    public String getAddress() {
        return address;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public Date getPassportDate() {
        return passportDate;
    }

    public String getPasswordIssuer() {
        return passwordIssuer;
    }

    public List<Action> getActions() {
        return actions;
    }
}
