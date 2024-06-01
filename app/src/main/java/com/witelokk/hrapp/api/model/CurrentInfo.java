package com.witelokk.hrapp.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class CurrentInfo implements Parcelable {
    @SerializedName("position")
    String position;

    @SerializedName("department")
    Department department;

    @SerializedName("salary")
    float salary;

    public CurrentInfo(String position, Department department, float salary) {
        this.position = position;
        this.department = department;
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public Department getDepartment() {
        return department;
    }

    public float getSalary() {
        return salary;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(position);
        dest.writeInt(department.getId());
        dest.writeFloat(salary);
    }
}
