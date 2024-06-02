package com.witelokk.hrapp.ui.department;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.witelokk.hrapp.R;
import com.witelokk.hrapp.api.model.Department;
import com.witelokk.hrapp.databinding.DialogDeleteDepartmentBinding;
import com.witelokk.hrapp.databinding.FragmentDepartmentBinding;
import com.witelokk.hrapp.ui.BaseFragment;

import java.util.ArrayList;

public class DepartmentFragment extends BaseFragment<DepartmentViewModel> {
    FragmentDepartmentBinding binding;
    DepartmentFragmentArgs args;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = DepartmentFragmentArgs.fromBundle(getArguments());
        viewModel = new ViewModelProvider(requireActivity()).get(String.valueOf(args.getDepartment().getId()), DepartmentViewModel.class);
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

        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        ((MenuHost) requireActivity()).addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_department, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.menu_edit) {
                    Department department = viewModel.getDepartment().getValue();
                    if (department != null)
                        getNavController().navigate(DepartmentFragmentDirections.actionDepartmentFragmentToAddEditDepartmentFragment(department.getCompanyId(), viewModel.getDepartment().getValue()));
                } else if (menuItem.getItemId() == R.id.menu_delete) {
                    showDeleteDialog();
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        binding.toolbar.setTitle(args.getDepartment().getName());

        viewModel.setDepartmentId(args.getDepartment().getId());

        viewModel.getDepartment().observe(getViewLifecycleOwner(), department -> {
            binding.toolbar.setTitle(department.getName());
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        EmployeesAdapter adapter = new EmployeesAdapter(new ArrayList<>(), employee -> {
            getNavController().navigate(DepartmentFragmentDirections.actionDepartmentFragmentToEmployeeFragment(employee));
        });
        binding.recyclerView.setAdapter(adapter);

        viewModel.getEmployees().observe(getViewLifecycleOwner(), employees -> {
            adapter.setEmployees(employees);
            binding.textNoEmployees.setVisibility(employees.isEmpty()? View.VISIBLE: View.GONE);
        });

        binding.fabAddEmployee.setOnClickListener(v -> {
            DepartmentFragmentDirections.ActionDepartmentFragmentToAddEmployeeFragment action = DepartmentFragmentDirections.actionDepartmentFragmentToAddEmployeeFragment(args.getDepartment().getId());
            getNavController().navigate(action);
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE));

        viewModel.loadData();
    }

    void showDeleteDialog() {
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(requireContext());
        DialogDeleteDepartmentBinding dialogBinding = DialogDeleteDepartmentBinding.inflate(getLayoutInflater());
        dialogBuilder.setView(dialogBinding.getRoot());

        dialogBuilder.setTitle(R.string.delete_company);
        dialogBuilder.setPositiveButton(R.string.delete, (dialog, whichButton) -> {
            // delete company
        });
        dialogBuilder.setNegativeButton(R.string.cancel, (dialog, whichButton) -> {
        });

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}
