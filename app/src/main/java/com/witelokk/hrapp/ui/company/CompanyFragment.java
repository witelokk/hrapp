package com.witelokk.hrapp.ui.company;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.witelokk.hrapp.databinding.FragmentCompanyBinding;
import com.witelokk.hrapp.ui.BaseFragment;
import com.witelokk.hrapp.ui.home.CompaniesAdapter;

public class CompanyFragment extends BaseFragment<CompanyViewModel> {
    FragmentCompanyBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(CompanyViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCompanyBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int companyId = CompanyFragmentArgs.fromBundle(getArguments()).getCompanyId();
        viewModel.setCompanyId(companyId);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel.getDepartments().observe(requireActivity(), departments -> {
            DepartmentsAdapter adapter = new DepartmentsAdapter(departments, departmentId -> {
                CompanyFragmentDirections.ActionCompanyFragmentToDepartmentFragment action = CompanyFragmentDirections.actionCompanyFragmentToDepartmentFragment(departmentId);
                getNavController().navigate(action);
            });
            binding.recyclerView.setAdapter(adapter);
        });

        viewModel.loadDepartments();
    }
}
