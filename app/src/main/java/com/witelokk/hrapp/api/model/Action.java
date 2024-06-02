package com.witelokk.hrapp.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Action implements Serializable {

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DepartmentTransferAction)) return false;
            DepartmentTransferAction that = (DepartmentTransferAction) o;
            return Objects.equals(previousDepartment, that.previousDepartment) && Objects.equals(newDepartment, that.newDepartment);
        }

        @Override
        public int hashCode() {
            return Objects.hash(previousDepartment, newDepartment);
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SalaryChangeAction)) return false;
            SalaryChangeAction that = (SalaryChangeAction) o;
            return Float.compare(previousSalary, that.previousSalary) == 0 && Float.compare(newSalary, that.newSalary) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(previousSalary, newSalary);
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PositionTransferAction)) return false;
            PositionTransferAction that = (PositionTransferAction) o;
            return Objects.equals(previousPosition, that.previousPosition) && Objects.equals(newPosition, that.newPosition);
        }

        @Override
        public int hashCode() {
            return Objects.hash(previousPosition, newPosition);
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof RecruitmentAction)) return false;
            RecruitmentAction that = (RecruitmentAction) o;
            return Float.compare(salary, that.salary) == 0 && Objects.equals(department, that.department) && Objects.equals(position, that.position);
        }

        @Override
        public int hashCode() {
            return Objects.hash(department, position, salary);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Action)) return false;
        Action action = (Action) o;
        return id == action.id && Objects.equals(type, action.type) && Objects.equals(date, action.date) && Objects.equals(recruitment, action.recruitment) && Objects.equals(departmentTransfer, action.departmentTransfer) && Objects.equals(dismissal, action.dismissal) && Objects.equals(positionTransfer, action.positionTransfer) && Objects.equals(salaryChange, action.salaryChange);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, date, recruitment, departmentTransfer, dismissal, positionTransfer, salaryChange);
    }
}
