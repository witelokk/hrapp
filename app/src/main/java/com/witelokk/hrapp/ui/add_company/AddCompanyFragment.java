package com.witelokk.hrapp.ui.add_company;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;

import com.witelokk.hrapp.R;
import com.witelokk.hrapp.databinding.FragmentAddCompanyBinding;

public class AddCompanyFragment extends Fragment {
    FragmentAddCompanyBinding binding;
    AddCompanyViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddCompanyBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(AddCompanyViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavHost navHost = (NavHost) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);

        viewModel.getIsCompanyCreated().observe(requireActivity(), isCompanyCreated -> {
            if (isCompanyCreated) {
                navHost.getNavController().navigateUp();
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
