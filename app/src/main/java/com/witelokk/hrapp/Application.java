package com.witelokk.hrapp;

import com.google.android.material.color.DynamicColors;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DynamicColors.applyToActivitiesIfAvailable(this);
    }
}
