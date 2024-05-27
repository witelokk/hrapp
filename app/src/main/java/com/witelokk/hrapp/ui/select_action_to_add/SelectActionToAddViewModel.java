package com.witelokk.hrapp.ui.select_action_to_add;

import android.content.SharedPreferences;

import com.witelokk.hrapp.ui.BaseViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SelectActionToAddViewModel extends BaseViewModel {
    @Inject
    public SelectActionToAddViewModel(SharedPreferences sharedPreferences) {
        super(sharedPreferences);
    }
}
