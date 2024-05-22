package com.witelokk.hrapp.ui.company;

import android.app.Dialog;
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
import com.witelokk.hrapp.databinding.DialogDeleteCompanyBinding;
import com.witelokk.hrapp.databinding.FragmentCompanyBinding;
import com.witelokk.hrapp.ui.BaseFragment;

public class CompanyFragment extends BaseFragment<CompanyViewModel> {
    FragmentCompanyBinding binding;
    CompanyFragmentArgs args;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = CompanyFragmentArgs.fromBundle(getArguments());
        viewModel = new ViewModelProvider(requireActivity()).get(String.valueOf(args.getCompanyId()), CompanyViewModel.class);
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

        binding.toolbar.setTitle(args.getCompanyName());
        viewModel.setCompanyId(args.getCompanyId());

        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        ((MenuHost) requireActivity()).addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_company, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.menu_edit) {

                } else if (menuItem.getItemId() == R.id.menu_delete) {
                    showDeleteDialog();
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel.getDepartments().observe(getViewLifecycleOwner(), departments -> {
            if (departments.isEmpty()) {
                binding.textNoDepartments.setVisibility(View.VISIBLE);
                binding.recyclerView.setAdapter(null);
            } else {
                binding.textNoDepartments.setVisibility(View.GONE);
                DepartmentsAdapter adapter = new DepartmentsAdapter(departments, department -> {
                    CompanyFragmentDirections.ActionCompanyFragmentToDepartmentFragment action = CompanyFragmentDirections.actionCompanyFragmentToDepartmentFragment(department.getCompanyId(), department.getName());
                    getNavController().navigate(action);
                });
                binding.recyclerView.setAdapter(adapter);
            }
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE));

        viewModel.loadDepartments();
    }

    void showDeleteDialog() {
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(requireContext());
        DialogDeleteCompanyBinding dialogBinding = DialogDeleteCompanyBinding.inflate(getLayoutInflater());
        dialogBuilder.setView(dialogBinding.getRoot());

        dialogBuilder.setTitle(R.string.delete_company);
        dialogBuilder.setPositiveButton(R.string.delete, (dialog, whichButton) -> {
            // delete company
        });
        dialogBuilder.setNegativeButton(R.string.cancel, (dialog, whichButton) -> {});

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}
