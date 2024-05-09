package com.witelokk.hrapp.ui.add_company;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.witelokk.hrapp.databinding.FragmentAddCompanyBinding;
import com.witelokk.hrapp.ui.BaseFragment;

public class AddCompanyFragment extends BaseFragment<AddCompanyViewModel> {
    FragmentAddCompanyBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(AddCompanyViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddCompanyBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getIsCompanyCreated().observe(requireActivity(), isCompanyCreated -> {
            if (isCompanyCreated) {
                getNavController().navigateUp();
            }
        });

        binding.buttonCreate.setOnClickListener(v -> {
            String name = binding.editTextName.getText().toString();
            String inn = binding.editTextInn.getText().toString();
            String kpp = binding.editTextKpp.getText().toString();

            viewModel.createCompany(name, inn, kpp);
        });
    }
}
