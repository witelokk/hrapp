package com.witelokk.hrapp.ui.company;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.witelokk.hrapp.databinding.FragmentCompanyBinding;

public class CompanyFragment extends Fragment {
    FragmentCompanyBinding binding;
    CompanyViewModel viewModel;

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
        int companyId = getArguments().getInt("company_id");
        viewModel.setCompanyId(companyId);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        DepartmentsAdapter adapter = new DepartmentsAdapter(departmentId -> {});
        binding.recyclerView.setAdapter(adapter);

        viewModel.getDepartments().observe(requireActivity(), departments -> {
            adapter.departments.addAll(departments);
            adapter.notifyDataSetChanged();
        });

        viewModel.loadDepartments();
    }
}
