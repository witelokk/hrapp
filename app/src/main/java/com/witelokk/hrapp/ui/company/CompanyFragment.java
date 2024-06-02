package com.witelokk.hrapp.ui.company;

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
import com.witelokk.hrapp.api.model.Company;
import com.witelokk.hrapp.databinding.DialogDeleteCompanyBinding;
import com.witelokk.hrapp.databinding.FragmentCompanyBinding;
import com.witelokk.hrapp.ui.BaseFragment;

import java.util.ArrayList;

public class CompanyFragment extends BaseFragment<CompanyViewModel> {
    FragmentCompanyBinding binding;
    CompanyFragmentArgs args;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = CompanyFragmentArgs.fromBundle(getArguments());
        viewModel = new ViewModelProvider(requireActivity()).get(String.valueOf(args.getCompany().getId()), CompanyViewModel.class);
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

        binding.toolbar.setTitle(args.getCompany().getName());
        viewModel.setCompanyId(args.getCompany().getId());

        viewModel.getCompany().observe(getViewLifecycleOwner(), company -> {
            binding.toolbar.setTitle(company.getName());
            viewModel.setCompanyId(company.getId());
        });

        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        ((MenuHost) requireActivity()).addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_company, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.menu_edit) {
                    Company company = viewModel.getCompany().getValue();
                    CompanyFragmentDirections.ActionCompanyFragmentToAddCompanyFragment action = CompanyFragmentDirections.actionCompanyFragmentToAddCompanyFragment(company.getId(), company.getName(), company.getInn(), company.getKpp());
                    getNavController().navigate(action);
                } else if (menuItem.getItemId() == R.id.menu_delete) {
                    showDeleteDialog();
                } else if (menuItem.getItemId() == R.id.menu_reports) {
                    Company company = viewModel.getCompany().getValue();
                    CompanyFragmentDirections.ActionCompanyFragmentToReportsFragment action = CompanyFragmentDirections.actionCompanyFragmentToReportsFragment(company.getId());
                    getNavController().navigate(action);
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        DepartmentsAdapter adapter = new DepartmentsAdapter(new ArrayList<>(), department -> {
            CompanyFragmentDirections.ActionCompanyFragmentToDepartmentFragment action = CompanyFragmentDirections.actionCompanyFragmentToDepartmentFragment(department);
            getNavController().navigate(action);
        });
        binding.recyclerView.setAdapter(adapter);

        viewModel.getDepartments().observe(getViewLifecycleOwner(), departments -> {
            adapter.setDepartments(departments);
            binding.textNoDepartments.setVisibility(departments.isEmpty()? View.VISIBLE: View.GONE);
        });

        binding.fabAddDepartment.setOnClickListener(v -> getNavController().navigate(CompanyFragmentDirections.actionCompanyFragmentToAddEditDepartmentFragment(args.getCompany().getId())));

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE));

        viewModel.loadData();
    }

    void showDeleteDialog() {
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(requireContext());
        DialogDeleteCompanyBinding dialogBinding = DialogDeleteCompanyBinding.inflate(getLayoutInflater());
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
