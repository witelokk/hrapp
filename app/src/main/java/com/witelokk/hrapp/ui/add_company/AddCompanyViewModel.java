package com.witelokk.hrapp.ui.add_company;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.witelokk.hrapp.data.repository.CompaniesRepository;
import com.witelokk.hrapp.data.repository.CompaniesRepositoryImpl;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddCompanyViewModel extends ViewModel {
    private MutableLiveData<Boolean> isCompanyCreated = new MutableLiveData<>();
    private CompaniesRepository companiesRepository;

    @Inject
    AddCompanyViewModel(CompaniesRepository companiesRepository) {
        this.companiesRepository = companiesRepository;
    }

    LiveData<Boolean> getIsCompanyCreated() {
        return isCompanyCreated;
    }

    void createCompany(String name, String inn, String kpp) {
        companiesRepository.addCompany(name, inn, kpp).observeForever(result -> {
            if (!result.isSuccess()) {
//                Toast.makeText(getApplication(), result.getError(), Toast.LENGTH_SHORT).show();
                return;
            }
            isCompanyCreated.setValue(true);
        });
    }
}
