package com.witelokk.hrapp.ui.add_company;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.data.repository.CompaniesRepository;
import com.witelokk.hrapp.data.repository.CompaniesRepositoryImpl;

public class AddCompanyViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> isCompanyCreated = new MutableLiveData<>();
    private CompaniesRepository companiesRepository;

    public AddCompanyViewModel(@NonNull Application application) {
        super(application);
        companiesRepository = new CompaniesRepositoryImpl(application.getSharedPreferences("prefs", Context.MODE_PRIVATE).getString("access_token", "none"));
    }

    LiveData<Boolean> getIsCompanyCreated() {
        return isCompanyCreated;
    }

    void createCompany(String name, String inn, String kpp) {
        companiesRepository.addCompany(name, inn, kpp).observeForever(result -> {
            if (!result.isSuccess()) {
                Toast.makeText(getApplication(), result.getError(), Toast.LENGTH_SHORT).show();
                return;
            }
            isCompanyCreated.setValue(true);
        });
    }
}
