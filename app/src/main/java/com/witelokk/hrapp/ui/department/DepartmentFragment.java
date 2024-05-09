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
        int departmentId = getArguments().getInt("department_id");
        viewModel.setDepartmentId(departmentId);

        binding.recyclerViewEmployees.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel.getEmployees().observe(requireActivity(), employees -> {
            EmployeesAdapter adapter = new EmployeesAdapter(employees, employeeId -> {
            });
            binding.recyclerViewEmployees.setAdapter(adapter);
        });

        viewModel.loadEmployees();
    }
}
