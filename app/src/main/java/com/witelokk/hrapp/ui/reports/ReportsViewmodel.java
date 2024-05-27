package com.witelokk.hrapp.ui.reports;

import android.content.SharedPreferences;

import com.witelokk.hrapp.ui.BaseViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ReportsViewmodel extends BaseViewModel {
    @Inject
    public ReportsViewmodel(SharedPreferences sharedPreferences) {
        super(sharedPreferences);
    }
}
