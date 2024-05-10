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

        CompanyFragmentArgs args = CompanyFragmentArgs.fromBundle(getArguments());

        binding.toolbar.setTitle(args.getCompanyName());
        viewModel.setCompanyId(args.getCompanyId());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel.getDepartments().observe(getViewLifecycleOwner(), departments -> {
            if (departments.isEmpty()) {
                binding.textNoDepartments.setVisibility(View.VISIBLE);
                binding.recyclerView.setAdapter(null);
            } else {
                binding.textNoDepartments.setVisibility(View.GONE);
                DepartmentsAdapter adapter = new DepartmentsAdapter(departments, department -> {
                    CompanyFragmentDirections.ActionCompanyFragmentToDepartmentFragment action =
                            CompanyFragmentDirections.actionCompanyFragmentToDepartmentFragment(department.getCompanyId(), department.getName());
                    getNavController().navigate(action);
                });
                binding.recyclerView.setAdapter(adapter);
            }
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading ->
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE));

        viewModel.loadDepartments();
    }
}
