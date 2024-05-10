package com.witelokk.hrapp.ui.department;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.witelokk.hrapp.databinding.FragmentDepartmentBinding;
import com.witelokk.hrapp.ui.BaseFragment;

public class DepartmentFragment extends BaseFragment<DepartmentViewModel> {
    FragmentDepartmentBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(DepartmentViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDepartmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DepartmentFragmentArgs args = DepartmentFragmentArgs.fromBundle(getArguments());

        binding.toolbar.setTitle(args.getDepartmentName());

        int departmentId = args.getDepartmentId();
        viewModel.setDepartmentId(departmentId);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel.getEmployees().observe(getViewLifecycleOwner(), employees -> {
            EmployeesAdapter adapter = new EmployeesAdapter(employees, employeeId -> {
            });
            binding.recyclerView.setAdapter(adapter);
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading ->
                binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE));

        viewModel.loadEmployees();
    }
}
