package com.witelokk.hrapp.ui.reports;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.Error;
import com.witelokk.hrapp.Event;
import com.witelokk.hrapp.data.repository.ReportsRepository;
import com.witelokk.hrapp.ui.BaseViewModel;

import java.io.DataOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ReportsViewModel extends BaseViewModel {
    private final MutableLiveData<Event<byte[]>> report = new MutableLiveData<>();
    private final ReportsRepository reportsRepository;
    private ContentResolver contentResolver;

    @Inject
    public ReportsViewModel(SharedPreferences sharedPreferences, ReportsRepository reportsRepository) {
        super(sharedPreferences);
        this.reportsRepository = reportsRepository;
    }

    public LiveData<Event<byte[]>> getReport() {
        return report;
    }

    public void setContentResolver(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    void generateReport(int companyId) {
        reportsRepository.getReport(companyId).observeForever(result -> {
            if (result.isSuccess()) {
                try {
                    report.setValue(new Event<>(result.getData().bytes()));
                } catch (IOException e) {
                    setError(new Error.Unknown());
                }
            } else {
                setError(result.getError());
            }
        });
    }

    void saveReport(Uri uri, byte[] content) {
        try {
            DataOutputStream dataStream = new DataOutputStream(this.contentResolver.openOutputStream(uri));
            dataStream.write(content);
            dataStream.close();
        } catch (IOException e) {
            setError(new Error.Unknown());
        }
    }
}
