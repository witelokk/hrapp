package com.witelokk.hrapp.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Action {

    static public class DepartmentTransferAction {
        @SerializedName("previous_department")
        private Department previousDepartment;

        @SerializedName("new_department")
        private Department newDepartment;

        public Department getPreviousDepartment() {
            return previousDepartment;
        }

        public Department getNewDepartment() {
            return newDepartment;
        }
    }

    static public class DismissalAction {
    }

    static public class SalaryChangeAction {
        @SerializedName("previous_salary")
        private float previousSalary;

        @SerializedName("new_salary")
        private float newSalary;

        public float getPreviousSalary() {
            return previousSalary;
        }

        public float getNewSalary() {
            return newSalary;
        }
    }

    static public class PositionTransferAction {
        @SerializedName("previous_position")
        private String previousPosition;

        @SerializedName("new_position")
        private String newPosition;

        public String getPreviousPosition() {
            return previousPosition;
        }

        public void setPreviousPosition(String previousPosition) {
            this.previousPosition = previousPosition;
        }

        public String getNewPosition() {
            return newPosition;
        }

        public void setNewPosition(String newPosition) {
            this.newPosition = newPosition;
        }
    }

    static public class RecruitmentAction {
        @SerializedName("department")
        private Department department;

        @SerializedName("position")
        private String position;

        @SerializedName("salary")
        private float salary;

        public Department getDepartment() {
            return department;
        }

        public String getPosition() {
            return position;
        }

        public float getSalary() {
            return salary;
        }
    }

    @SerializedName("id")
    private int id;

    @SerializedName("action_type")
    private String type;

    @SerializedName("date")
    private Date date;

    @SerializedName("recruitment")
    private RecruitmentAction recruitment;

    @SerializedName("department_transfer")
    private DepartmentTransferAction departmentTransfer;

    @SerializedName("dismissal")
    private DismissalAction dismissal;

    @SerializedName("position_transfer")
    private PositionTransferAction positionTransfer;

    @SerializedName("salary_change")
    private SalaryChangeAction salaryChange;

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }


    public Date getDate() {
        return date;
    }

    public RecruitmentAction getRecruitment() {
        return recruitment;
    }

    public DepartmentTransferAction getDepartmentTransfer() {
        return departmentTransfer;
    }

    public DismissalAction getDismissal() {
        return dismissal;
    }

    public PositionTransferAction getPositionTransfer() {
        return positionTransfer;
    }

    public SalaryChangeAction getSalaryChange() {
        return salaryChange;
    }
}
