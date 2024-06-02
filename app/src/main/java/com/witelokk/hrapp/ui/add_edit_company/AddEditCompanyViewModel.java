package com.witelokk.hrapp.ui.add_edit_company;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.witelokk.hrapp.Event;
import com.witelokk.hrapp.data.repository.CompaniesRepository;
import com.witelokk.hrapp.ui.BaseViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddEditCompanyViewModel extends BaseViewModel {
    private final MutableLiveData<Event<Boolean>> isDone = new MutableLiveData<>();
    private final CompaniesRepository companiesRepository;

    @Inject
    AddEditCompanyViewModel(SharedPreferences sharedPreferences, CompaniesRepository companiesRepository) {
        super(sharedPreferences);
        this.companiesRepository = companiesRepository;
    }

    LiveData<Event<Boolean>> getIsDone() {
        return isDone;
    }

    void createCompany(String name, String inn, String kpp) {
        companiesRepository.addCompany(name, inn, kpp).observeForever(result -> {
            if (result.isSuccess()) {
                isDone.setValue(new Event<>(true));
            } else {
                setError(result.getError());
            }
        });
    }

    void editCompany(int companyId, String name, String inn, String kpp) {
        companiesRepository.editCompany(companyId, name, inn, kpp).observeForever(result -> {
            if (result.isSuccess()) {
                isDone.setValue(new Event<>(true));
            } else {
                setError(result.getError());
            }
        });
    }
}
