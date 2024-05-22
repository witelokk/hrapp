package com.witelokk.hrapp.api;

import com.witelokk.hrapp.api.model.Action;
import com.witelokk.hrapp.api.model.CreateDepartmentTransferActionRequest;
import com.witelokk.hrapp.api.model.CreateDismissalAction;
import com.witelokk.hrapp.api.model.CreatePositionTransferActionRequest;
import com.witelokk.hrapp.api.model.CreateRecruitmentActionRequest;
import com.witelokk.hrapp.api.model.CreateSalaryChangeAction;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ActionsApi {
    @POST("/actions/employee/{employee_id}")
    Call<Void> createAction(@Path("employee_id") int employeeId, @Body CreateDepartmentTransferActionRequest request);

    @POST("/actions/employee/{employee_id}")
    Call<Void> createAction(@Path("employee_id") int employeeId, @Body CreateDismissalAction request);

    @POST("/actions/employee/{employee_id}")
    Call<Void> createAction(@Path("employee_id") int employeeId, @Body CreatePositionTransferActionRequest request);

    @POST("/actions/employee/{employee_id}")
    Call<Void> createAction(@Path("employee_id") int employeeId, @Body CreateRecruitmentActionRequest request);

    @POST("/actions/employee/{employee_id}")
    Call<Void> createAction(@Path("employee_id") int employeeId, @Body CreateSalaryChangeAction request);

    @GET("/actions/{action_id}")
    Call<Action> getAction(@Path("action_id") int actionId);

    @GET("/actions/employee/{employee_id}")
    Call<List<Action>> getActionsByEmployee(@Path("employee_id") int actionId);
}
